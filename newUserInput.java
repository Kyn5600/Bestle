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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class newUserInput extends textbasedai {
	@Override
    public void runner() throws Exception {
    	String inputText = "";
    	do {
    		inputText = userInput("Enter text to classify: ");
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
    public Instances getInstances(String data) throws Exception{
    	CSVLoader loader = new CSVLoader();
		loader.setSource(new File(data));
		Instances newdata = loader.getDataSet();
		return newdata;
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
   public void inputClassifier(String data,String inputText) throws Exception {
	   try {
		   classify(data,inputText);
	   } catch(IllegalArgumentException e) {
		   String newclass = userInput("Instance not found. Input classification: ");
		   addNewInstance(data,inputText,newclass);
	   }
   }
   public String userInput(String prompt) {
	   Scanner scanner = new Scanner(System.in);
	   System.out.print(prompt);
	   String newclass = scanner.nextLine();
	   return newclass;
   }
   public void classify(String data,String inputText) throws Exception {
	   Instances thisdata=getInstances(data);
       if (thisdata.classIndex() == -1) thisdata.setClassIndex(thisdata.numAttributes() - 1);
       Classifier classifier = setClassifier(thisdata);
       Instance newInstance = new DenseInstance(2);
       newInstance.setDataset(thisdata);
       newInstance.setValue(0, inputText);
       newInstance.setMissing(1);
       Attribute classAttr = thisdata.classAttribute();  
       double predictedClassValue = classifier.classifyInstance(newInstance);
       String predictedClassLabel = thisdata.classAttribute().value((int) predictedClassValue);
       System.out.println("Classified text as: " + predictedClassLabel);
   }
   public void addNewInstance(String data,String inputText, String newclass) throws IOException {
	   BufferedReader objReader = new BufferedReader(new FileReader(data));
	   ArrayList<String> tempstring = new ArrayList<String>();
	   String tempString; //same name different upper case. NR1 room 523 A. Fight me.
	   while ((tempString = objReader.readLine()) != null) {
		   tempstring.add(tempString);
	   }
	   FileWriter file = new FileWriter(data);
	   BufferedWriter bf = new BufferedWriter(file);
	   for(int i = 0; i <tempstring.size();i++) {
		   bf.write(tempstring.get(i));
		   bf.newLine();
	   }
	   bf.write(inputText + "," + newclass);
	   bf.close();
	   objReader.close();
   }
}

/* Editing workspace:
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
