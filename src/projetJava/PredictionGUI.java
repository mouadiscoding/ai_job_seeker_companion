package projetJava;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.stopwords.AbstractFileBasedStopwords;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;


public class PredictionGUI extends JFrame {
	private JFrame frame;
	private JComboBox<String> modelComboBox;
	private JTextField inputTextField;
	private JButton predictButton;
	private JLabel predictionLabel;
	private JPanel contentPane;
	int xx, xy;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PredictionGUI frame = new PredictionGUI();
					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PredictionGUI() {

		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 476);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 346, 490);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Job Finder");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setForeground(new Color(0x739bcb));
		lblNewLabel.setBounds(134, 305, 100, 27);
		panel.add(lblNewLabel);

		JLabel home = new JLabel("<html><u>Home</u></html>");
		home.setHorizontalAlignment(SwingConstants.CENTER);
		home.setFont(new Font("Tahoma", Font.PLAIN, 14));
		home.setForeground(new Color(0x739bcb));
		home.setBounds(100, 380, 160, 27);
		panel.add(home);


		JLabel logOut = new JLabel("<html><u>Log Out</u></html>");
		logOut.setHorizontalAlignment(SwingConstants.CENTER);
		logOut.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logOut.setForeground(new Color(0x739bcb));
		logOut.setBounds(100, 410, 160, 27);
		panel.add(logOut);

		JLabel label = new JLabel("");

		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				SwingUtilities.invokeLater(() -> {
					Home home1 = new Home();
					home1.setUndecorated(true);
					home1.setLocationRelativeTo(null);
					home1.setVisible(true);
				});
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				home.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				home.setForeground(new Color(0x739bcb));
			}
		});

		logOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				SwingUtilities.invokeLater(() -> {
					frame1 frame1Instance = new frame1();
					frame1Instance.setUndecorated(true);
					frame1Instance.setLocationRelativeTo(null);
					frame1Instance.setVisible(true);
				});
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				logOut.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logOut.setForeground(new Color(0x739bcb));
			}
		});

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});

		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
				int y = arg0.getYOnScreen();
				PredictionGUI.this.setLocation(x - xx, y - xy);
			}
		});
		label.setBounds(0, 0, 346, 490);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(frame1.class.getResource("./bg3.jpg")));
		panel.add(label);

		JLabel lblWeGotYou = new JLabel("....Find Your Matching Job....");
		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeGotYou.setForeground(new Color(0x706b63));
		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWeGotYou.setBounds(85, 343, 200, 27);
		panel.add(lblWeGotYou);


		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.DARK_GRAY);
		panel2.setBounds(0, 0, 346, 490);
		contentPane.add(panel2);
		panel2.setLayout(null);

		modelComboBox = new JComboBox<>(new String[]{"RandomForest", "MultilayerPerceptron", "SVM", "NaiveBayes", "DecisionTree", "LogisticRegression"});
		modelComboBox.setBounds(395, 120, 283, 36);
		contentPane.add(modelComboBox);

		inputTextField = new JTextField();
		inputTextField.setColumns(10);
		inputTextField.setBounds(395, 190, 283, 80);
		contentPane.add(inputTextField);

		predictButton = new JButton("Predict");
		predictButton.setForeground(Color.WHITE);
		predictButton.setBackground(new Color(0x739bcb));
		predictButton.setBounds(395, 300, 283, 36);
		predictButton.setBorder(null);
		predictButton.setFocusPainted(false);
		predictButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					performPrediction();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		contentPane.add(predictButton);

		predictionLabel = new JLabel("Prediction: ");
		predictionLabel.setForeground(Color.BLACK);
		predictionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		predictionLabel.setBounds(395, 350, 283, 20);
		contentPane.add(predictionLabel);

		contentPane.setVisible(true);


		JLabel lbl_close = new JLabel("X");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(new Color(241, 57, 83));
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds(691, 0, 37, 27);
		contentPane.add(lbl_close);
	}

	private void performPrediction() throws Exception {
		String text = inputTextField.getText().trim();
		if (text.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Please enter text to predict.");
			return;
		}


		String selectedModel = (String) modelComboBox.getSelectedItem();

		// Read the selected model based on the user's choice
		Object model = weka.core.SerializationHelper.read(selectedModel.toLowerCase() + "_model.model");


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

			double prediction;
			if (selectedModel.equals("RandomForest")) {
				prediction = ((RandomForest) model).classifyInstance(reducedDataset.firstInstance());
			} else if (selectedModel.equals("MultilayerPerceptron")) {
				prediction = ((MultilayerPerceptron) model).classifyInstance(reducedDataset.firstInstance());
			} else if (selectedModel.equals("SVM")) {
				prediction = ((SMO) model).classifyInstance(reducedDataset.firstInstance());

			} else if (selectedModel.equals("NaiveBayes")) {
				prediction = ((Classifier) model).classifyInstance(reducedDataset.firstInstance());
			} else {
				throw new IllegalArgumentException("Invalid model selection.");
			}

			String predictedClass = vectorizedDataset.classAttribute().value((int) prediction);
			predictionLabel.setText("Prediction: " + predictedClass);
		}

	}
	private void showHome () {
		SwingUtilities.invokeLater(() -> {
			Home home = new Home();
			home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			home.setUndecorated(true);
			home.setLocationRelativeTo(null);
			home.setVisible(true);
		});
	}
}
