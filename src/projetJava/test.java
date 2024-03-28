package projetJava;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.*;
import weka.core.converters.CSVLoader;
import weka.core.stopwords.AbstractFileBasedStopwords;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;
import java.util.ArrayList;

public class test {

    public static void main(String[] args) throws Exception {
        try {
            // Load the saved RandomForest model
            weka.classifiers.trees.RandomForest rf = (weka.classifiers.trees.RandomForest) SerializationHelper.read("RandomForest_model.model");

            // Sample text to predict
            String text = "Développement d'une expertise fonctionnelle transverse sur l'ensemble des modules coeurs de l'application (trading, routing, rating)";

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

            Attribute firstAttribute = filteredData.attribute(0);
            Instance firstInstance = filteredData.instance(0);
            firstInstance.setValue(firstAttribute, text);

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

                double prediction = rf.classifyInstance(reducedDataset.firstInstance());

                // Get the predicted class label
                String predictedClass = vectorizedDataset.classAttribute().value((int) prediction);

                // Print the predicted class label
                System.out.println("Predicted Class: " + predictedClass);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
