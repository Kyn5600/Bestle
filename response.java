package weka.api;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class response extends textbasedai{
	private 
	newUserInput classify = new newUserInput();
	@Override
	public void runner() throws Exception {
		String inputText = "";
    	do {
    		inputText = classify.userInput("Enter text to classify: ");
    		inputText = inputText.replaceAll("[^a-zA-Z0-9\\s]","");
    		inputText = inputText.toLowerCase();
    		if(inputText.equals("eval")) {
    			getCSV();
    			printEval(testingData,neuralNet);
    			inputText = "";
    			continue;
    		} else if(inputText.equals("exit")) {
    			System.out.println();
    			break;
    		}
    		String data = "C:/Users/werer/eclipse-workspace/TextBasedAI/src/weka/api/conversational_english.csv";
    		inputClassifier(data,inputText);
		} while(true);
	}
	public void inputClassifier(String data,String inputText) throws Exception {
		try {
			classify(data,inputText);
		} catch(IllegalArgumentException e) {
			inputText = classify.userInput("Data not found. Please input new text: ");
		}
	}
	public void classify(String data,String inputText) throws Exception {
		Instances thisdata = classify.getInstances(data);
	    if (thisdata.classIndex() == -1) thisdata.setClassIndex(thisdata.numAttributes() - 1);
	    Classifier classifier = setClassifier(thisdata);
	    Instance newInstance = new DenseInstance(2);
	    newInstance.setDataset(thisdata);
	    newInstance.setValue(0, inputText);
	    newInstance.setMissing(1);
	    Attribute classAttr = thisdata.classAttribute();  
	    double predictedClassValue = classifier.classifyInstance(newInstance);
	    String predictedClassLabel = thisdata.classAttribute().value((int) predictedClassValue);
		//System.out.println("Classified text as: " + predictedClassLabel);       Replace with random reply of predicted value
	}
	public Classifier setClassifier(Instances data) throws Exception {
		Classifier classifier = new MultilayerPerceptron();
		String[] options = new String[6];
		options[0] = "-L"; // learning rate
		options[1] = "0.1";
		options[2] = "-M"; // momentum
		options[3] = "0.2";
		options[4] = "-N"; // epochs
		options[5] = "500";
		((MultilayerPerceptron)classifier).setOptions(options);
		classifier.buildClassifier(data);
		return classifier;
	}
}