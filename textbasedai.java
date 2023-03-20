package weka.api;

import java.io.File;
import java.io.IOException;

import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.SerializationHelper;

public class textbasedai {//test
	
	public File filename = new File("C:/Users/werer/eclipse-workspace/TextBasedAI/src/weka/api/conversational_english.csv");
	public String modelFilename = "C:/Users/werer/eclipse-workspace/TextBasedAI/src/weka/api/BestleModel.model";
	
	protected Instances data;
	
	protected MultilayerPerceptron neuralNet = new MultilayerPerceptron();
	protected Instances testingData;
	
	public void runner() throws Exception {
		getCSV();
		printEval(testingData,neuralNet);
	}
	protected Instances getCSV() throws IOException {
		CSVLoader loader = new CSVLoader();
		loader.setSource(filename);
		data = loader.getDataSet();
		try {
			filterData(data);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public void filterData(Instances data) throws Exception {
		StringToWordVector filter = new StringToWordVector();
		filter.setLowerCaseTokens(true);
		filter.setTokenizer(new weka.core.tokenizers.WordTokenizer());
		filter.setInputFormat(data);
		Instances filteredData = Filter.useFilter(data, filter);
		RemovePercentage newFilter = new RemovePercentage();
		newFilter.setInputFormat(filteredData);
		newFilter.setPercentage(0);
		newFilter.setInvertSelection(false);
		Instances trainingData = Filter.useFilter(filteredData, newFilter);
		newFilter.setInvertSelection(true);
		testingData = Filter.useFilter(filteredData, newFilter);
		buildNeuralNet(trainingData);
	    SerializationHelper.write(modelFilename, neuralNet);
	}
	private void buildNeuralNet(Instances trainingData) throws Exception {
		neuralNet.setLearningRate(0.1);
		neuralNet.setMomentum(0.2);
		neuralNet.setTrainingTime(2300);
		trainingData.setClassIndex(trainingData.numAttributes() - 1);
		testingData.setClassIndex(testingData.numAttributes() - 1);
		neuralNet.buildClassifier(trainingData);
	}
	protected void printEval(Instances testingData,MultilayerPerceptron neuralNet) throws Exception {
		Evaluation eval = new Evaluation(testingData);
		eval.evaluateModel(neuralNet, testingData);
		System.out.println(eval.toSummaryString());
		System.out.println(eval.toMatrixString());
	}
}
