package weka.api;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
 
import java.io.File;

public class CSVToArff {
   public void runner() throws Exception {
    CSVLoader loader = new CSVLoader();
    loader.setSource(new File("C:/Users/werer/eclipse-workspace/TextBasedAI/src/weka/api/conversational_english.csv"));
    Instances data = loader.getDataSet();

    ArffSaver saver = new ArffSaver();
    saver.setInstances(data);
    saver.setFile(new File("C:/Users/werer/eclipse-workspace/TextBasedAI/src/weka/api/conversational_english_data.arff"));
    saver.writeBatch();
  }
}