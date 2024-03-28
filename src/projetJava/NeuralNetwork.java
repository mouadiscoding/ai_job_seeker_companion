package projetJava;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;
import weka.core.stopwords.AbstractFileBasedStopwords;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.Randomize;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class NeuralNetwork {

    public static int calculateMode(Instances data, Attribute attr) {
        HashMap<String, Integer> valueCounts = new HashMap<>();

        // Count occurrences of each nominal value
        for (int i = 0; i < data.numInstances(); i++) {
            Instance inst = data.instance(i);
            if (!inst.isMissing(attr)) {
                String value = inst.stringValue(attr);
                valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            }
        }

        // Find the most frequent value (mode)
        int modeValue = -1;
        int maxCount = 0;
        for (String value : valueCounts.keySet()) {
            int count = valueCounts.get(value);
            if (count > maxCount) {
                maxCount = count;
                modeValue = attr.indexOfValue(value);
            }
        }

        return modeValue;
    }

    public static void instancesToCSV(Instances data, String filePath) throws IOException, IOException {
        FileWriter writer = new FileWriter(filePath);

        // Write header
        for (int i = 0; i < data.numAttributes(); i++) {
            writer.append(data.attribute(i).name());
            if (i < data.numAttributes() - 1) {
                writer.append(",");
            }
        }
        writer.append("\n");

        // Write data
        for (int j = 0; j < data.numInstances(); j++) {
            for (int i = 0; i < data.numAttributes(); i++) {
                writer.append(String.valueOf(data.instance(j).value(i)));
                if (i < data.numAttributes() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
        }

        writer.flush();
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("dataset2.csv"));
        Instances dataset = loader.getDataSet();

        if(dataset.classIndex() == -1){
            dataset.setClassIndex(dataset.numAttributes()-1);
        }



        NominalToString nominalToStringFilter = new NominalToString();

        // Set the attribute index to convert to string
        nominalToStringFilter.setAttributeIndexes("1"); // Convert only the first attribute (index 1)

        // Apply the filter to the dataset
        nominalToStringFilter.setInputFormat(dataset);

        Instances filteredData = Filter.useFilter(dataset, nominalToStringFilter);


        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(filteredData); // specify the dataset
        filter.setWordsToKeep(2000); // features to keep per class
        filter.setDoNotOperateOnPerClassBasis(false);
        filter.setOutputWordCounts(true); // display the number of occurrences of a word/feature instead of just the truth value
        filter.setTFTransform(true); // set the term frequency
        filter.setIDFTransform(true); // set the inverse document frequency

        AbstractFileBasedStopwords stopwords = new AbstractFileBasedStopwords() {
            @Override
            public String stopwordsTipText() {
                return null;
            }

            @Override
            public String globalInfo() {
                return null;
            }

            @Override
            protected boolean is(String s) {
                return false;
            }
        };

        stopwords.setStopwords(new File("stopwords-fr.txt"));

        filter.setStopwordsHandler(stopwords);

        WordTokenizer wordTokenizer = new WordTokenizer();
        filter.setTokenizer(wordTokenizer);

        filter.setMinTermFreq(3);
        //filter.setPeriodicPruning(2);

        Instances vectorizedDataset = Filter.useFilter(filteredData, filter);


        // instancesToCSV(vectorizedDataset, "vector.csv");

        // imputing NaN values


        Attribute targetAttr = vectorizedDataset.attribute(0);  // Index 1 corresponds to the second attribute

        // Check if the target attribute is nominal
        if (targetAttr.isNominal()) {
            // Calculate mode for the target attribute
            int modeValue = calculateMode(vectorizedDataset, targetAttr);

            // Iterate through each instance to replace missing values for the target attribute
            for (int i = 0; i < vectorizedDataset.numInstances(); i++) {
                Instance inst = vectorizedDataset.instance(i);

                // Check if the current instance has a missing value for the target attribute
                if (inst.isMissing(targetAttr)) {
                    inst.setValue(targetAttr, modeValue);
                }
            }
        }




        // performing attribute selection

        InfoGainAttributeEval evaluator = new InfoGainAttributeEval();
        Ranker search = new Ranker();
        search.setGenerateRanking(true);
        search.setThreshold(0.005);
        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setEvaluator(evaluator);
        attributeSelection.setSearch(search);
        attributeSelection.setSeed(42);
        attributeSelection.SelectAttributes(vectorizedDataset);
        Instances reducedDataset = attributeSelection.reduceDimensionality(vectorizedDataset);

        // split the data into train and test splits
        Randomize randomize = new Randomize();
        randomize.setSeed(42);
        randomize.setInputFormat(reducedDataset);
        Instances randomizedData = Filter.useFilter(reducedDataset, randomize);

        int trainSize = (int) Math.round(randomizedData.numInstances()*0.7);
        int testSize = randomizedData.numInstances() - trainSize;

        System.out.println(trainSize);
        System.out.println(testSize);

        Instances trainingData = new Instances(randomizedData, 0, trainSize);
        Instances testData = new Instances(randomizedData, trainSize, testSize);

        // oversampling using SMOTE

        SMOTE smote = new SMOTE();
        smote.setInputFormat(trainingData);
        smote.setNearestNeighbors(10);
        smote.setPercentage(1000);
        Instances balancedTrainingData = Filter.useFilter(trainingData, smote);



        // build and train a neural network
        MultilayerPerceptron mlp = new MultilayerPerceptron();
        String[] options = new String[14];

        options[0] = "-L"; // Learning Rate
        options[1] = "0.3"; // Adjust based on experimentation; smaller values can be beneficial for convergence.
        options[2] = "-M"; // Momentum
        options[3] = "0.1";
        options[4] = "-H"; // Hidden Layers
        options[5] = "40,10";
        options[6] = "-N"; // Set the number of epochs (training iterations)
        options[7] = "200";
        options[8] = "-V"; // validation set size
        options[9] = "20";
        options[10] = "-S"; // set random seed
        options[11] = "1";
        options[12] = "-E";
        options[13] = "10";

        mlp.setOptions(options);

        mlp.buildClassifier(balancedTrainingData);

        String modelFilePath = "MultilayerPerceptron_model.model";

        // Save the trained MLP classifier to a file
        SerializationHelper.write(modelFilePath, mlp);

        System.out.println("Neural Network model saved to: " + modelFilePath);

        // evaluate the classifier using cross validation
        Evaluation eval = new Evaluation(trainingData);
        Random randomSeed = new Random(42);
        int folds = 5;
        eval.crossValidateModel(mlp, trainingData, folds, randomSeed);
        System.out.println(eval.toSummaryString("Cross-Validated Evaluation Metrics: \n", false));

        // evaluate the classifier on test data

        Evaluation eval1 = new Evaluation(trainingData);
        eval1.evaluateModel(mlp, testData);
        System.out.println(eval1.toSummaryString());

        Evaluation eval2 = new Evaluation(trainingData);
        eval2.evaluateModel(mlp, testData);
        System.out.println("Confusion Matrix: \n");
        System.out.println(eval2.toMatrixString());
    }
}
