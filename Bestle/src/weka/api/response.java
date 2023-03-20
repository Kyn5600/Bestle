package weka.api;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
 
public class response extends textbasedai{
	private newUserInput classify = new newUserInput();
	public ArrayList<String> text = new ArrayList<String>();
	public ArrayList<String> label = new ArrayList<String>();
	public String predictedClassLabel = "";
	public String name;
	@Override
	public void runner() throws Exception {
		String namechoice = classify.userInput("Would you like to name the bot? Default is \"Bot\" (y/n): ");
		namechoice.toLowerCase();
		if(namechoice.equals("y")) {
			name = classify.userInput("Enter name: ");
		} else {
			name = "Bot";
			System.out.println( name + ": Alright. Let's continue.");
		}
		String inputText = "";
    	do {
    		inputText = classify.userInput("You: ");
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
    		String data = "C:/Users/zlego/OneDrive/Documents/weka/api/Bestle/src/weka/api/conversational_english.csv";
    		inputClassifier(data,inputText);
		} while(true);
	}
	public void inputClassifier(String data,String inputText) throws Exception {
		try {
			classify(data,inputText);
		} catch(IllegalArgumentException e) {
			inputText = classify.userInput("Data not found. Please input new text: ");
			inputClassifier(data,inputText);
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
	    double predictedClassValue = classifier.classifyInstance(newInstance);
	    predictedClassLabel = thisdata.classAttribute().value((int) predictedClassValue);
		//System.out.println("Classified text as: " + predictedClassLabel);       Replace with random reply of predicted value
	    getReply(predictedClassLabel,data);
	    predictedClassLabel = "";
	}
	public void getReply(String predictedClassLabel, String data) throws IOException { 
		setArrays(data);
		switch(predictedClassLabel) {
			case "feeling_question":
				System.out.println(getReplyString(predictedClassLabel = "feeling_response"));
				double random = Math.random(); //for testing
				if(random>0.5f) {
					System.out.println(getReplyString(predictedClassLabel = "feeling_question"));
				} break;
			case "feeling_response":
				System.out.println(getReplyString(predictedClassLabel = "feeling_response_response")); break;
			case "joke_request":
				System.out.println(getReplyString(predictedClassLabel = "joke")); break;
			case "regque":
				System.out.println(getReplyString(predictedClassLabel = "regresponse")); break;
			case "fav_book_que":
				System.out.println(getReplyString(predictedClassLabel = "fav_book_response")); break;
			case "fav_food_que":
				System.out.println(getReplyString(predictedClassLabel = "fav_food_response")); break;
			case "fav_movie_que":
				System.out.println(getReplyString(predictedClassLabel = "fav_movie_response")); break;
			default:
				System.out.println(getReplyString(predictedClassLabel)); break;
		}
	}
	public String getReplyString(String predictedClass) {
		ArrayList<Integer> arrayIndex = new ArrayList<Integer>();
		for(int i = 0; i < text.size();i++) {
			if(label.get(i).equals(predictedClass)) {
				arrayIndex.add(i);
			}
		}
		return name + ": " + text.get(arrayIndex.get((int)(Math.random() * arrayIndex.size())));
	}
	public void setArrays(String data) throws IOException {
		BufferedReader objReader = new BufferedReader(new FileReader(data));
		String tempString; //same name different upper case. NR1 room 523 A. Fight me.
		while ((tempString = objReader.readLine()) != null) {
			String[] tempArray = tempString.split(",");
			text.add(tempArray[0]);
			label.add(tempArray[1]);
		}
		objReader.close();
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