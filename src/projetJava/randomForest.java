package projetJava;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.evaluation.Evaluation;
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
import java.util.ArrayList;
import java.util.Random;

public class randomForest {


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
        // load the dataset
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("dataset2.csv"));
        Instances dataset = loader.getDataSet();

        if (dataset.classIndex() == -1) {
            dataset.setClassIndex(dataset.numAttributes() - 1);
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
        filter.setPeriodicPruning(2);

        Instances vectorizedDataset = Filter.useFilter(filteredData, filter);


        // instancesToCSV(vectorizedDataset, "vector.csv");

        // imputing NaN values


        Attribute targetAttr = vectorizedDataset.attribute(0);  // Index 1 corresponds to the second attribute

        // Create a list to store indices of instances to be deleted
        ArrayList<Integer> indicesToDelete = new ArrayList<>();

        // Check if the target attribute is nominal
        if (targetAttr.isNominal()) {
            // Iterate through each instance to check for missing values for the target attribute
            for (int i = 0; i < vectorizedDataset.numInstances(); i++) {
                Instance inst = vectorizedDataset.instance(i);

                // Check if the current instance has a missing value for the target attribute
                if (inst.isMissing(targetAttr)) {
                    indicesToDelete.add(i);  // Add index to the list for deletion
                }
            }

            // Delete instances with missing values for the target attribute
            for (int i = indicesToDelete.size() - 1; i >= 0; i--) {
                vectorizedDataset.delete(indicesToDelete.get(i));
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

            int numAttributes = reducedDataset.numAttributes();


            // split the data into train and test splits
            Randomize randomize = new Randomize();

            randomize.setSeed(42);
            randomize.setInputFormat(reducedDataset);
            Instances randomizedData = Filter.useFilter(reducedDataset, randomize);

            int trainSize = (int) Math.round(randomizedData.numInstances() * 0.7);
            int testSize = randomizedData.numInstances() - trainSize;

            Instances trainingData = new Instances(randomizedData, 0, trainSize);
            Instances testData = new Instances(randomizedData, trainSize, testSize);


            int classIndex = vectorizedDataset.classIndex();

            // Determine class distribution
            int[] classCounts = vectorizedDataset.attributeStats(classIndex).nominalCounts;
            int minClassCount = Integer.MAX_VALUE;
            for (int count : classCounts) {
                if (count < minClassCount) {
                    minClassCount = count;
                }
            }

            // oversampling using SMOTE
            SMOTE smote = new SMOTE();
            smote.setInputFormat(trainingData);
            smote.setNearestNeighbors(10);
            smote.setPercentage(1000);
            Instances balancedTrainingData = Filter.useFilter(trainingData, smote);

            // train a random forest classifier

            weka.classifiers.trees.RandomForest rf = new weka.classifiers.trees.RandomForest();
            String[] options = new String[10];
            options[0] = "-I"; // Number of trees
            options[1] = "100"; // Set to 100
            options[2] = "-depth"; // Maximum depth of the trees
            options[3] = "200"; // Set maximum depth to 100
            options[4] = "-M"; // Set minimum Number of Instances for Split
            options[5] = "2";
            options[6] = "-P"; // Set percentage of Attributes to Consider
            options[7] = "70";
            options[8] = "-S"; // Set random seed
            options[9] = "42";
            rf.setOptions(options);

            rf.buildClassifier(balancedTrainingData);


            String modelFilePath = "RandomForest_model.model";

            // Save the trained random forest classifier to a file
            SerializationHelper.write(modelFilePath, rf);

            System.out.println("Random forest classifier model saved to: " + modelFilePath);


            // evaluate the classifier using cross validation
            Evaluation eval = new Evaluation(balancedTrainingData);
            Random randomSeed = new Random(42);
            int folds = 5;
            eval.crossValidateModel(rf, balancedTrainingData, folds, randomSeed);
            System.out.println(eval.toSummaryString("Cross-Validated Evaluation Metrics: \n", false));

            // evaluate the classifier on test data

            Evaluation eval1 = new Evaluation(balancedTrainingData);
            eval1.evaluateModel(rf, testData);
            System.out.println(eval1.toSummaryString());

            Evaluation eval2 = new Evaluation(balancedTrainingData);
            eval2.evaluateModel(rf, testData);
            System.out.println("Confusion Matrix: \n");
            System.out.println(eval2.toMatrixString());

        }
    }
}
