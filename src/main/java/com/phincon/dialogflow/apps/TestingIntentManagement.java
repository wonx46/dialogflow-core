package com.phincon.dialogflow.apps;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.phincon.dialogflow.management.IntentManagement;

public class TestingIntentManagement {

	//private static final String CSV_FILE_PATH = "D:\\EclipseWorkSpace\\CSVOperations\\results.csv";
	private static final String CSV_FILE_PATH = "D:\\test.csv";
	
	/*public static void main(String [] args) {
		System.out.println("Read Data Line by Line With Header \n"); 
		readDataLineByLine(CSV_FILE_PATH);
        System.out.println("_______________________________________________");
	}
	
	public static void readDataLineByLine(String file) { 
        try { 
  
            // Create an object of filereader class 
            // with CSV file as a parameter. 
            FileReader filereader = new FileReader(file); 
  
            // create csvReader object passing 
            // filereader as parameter 
            CSVReader csvReader = new CSVReader(filereader); 
            String[] nextRecord; 
  
            // we are going to read data line by line
            
            String older = null;
            String newest = null;
            List<String> listMessageResponse = new ArrayList<>();
            while ((nextRecord = csvReader.readNext()) != null) { 
            	
            	newest = nextRecord[0];
            	if(older != null) {
            		if(!newest.equals(older)){
            			IntentManagement.updateIntentForMessageResponse(older, listMessageResponse);
                	}
            	}
            	listMessageResponse.add(nextRecord[1]);
            	older =  newest;
            }
            if(listMessageResponse.size() != 0) {
            	IntentManagement.updateIntentForMessageResponse(older, listMessageResponse);	
            	listMessageResponse = new ArrayList<>();
            }
            
            csvReader.close();
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
	
	public static void updateIntentForMessageResponse(String file) { 
        try { 
  
            // Create an object of filereader class 
            // with CSV file as a parameter. 
            FileReader filereader = new FileReader(file); 
  
            // create csvReader object passing 
            // filereader as parameter 
            CSVReader csvReader = new CSVReader(filereader); 
            String[] nextRecord; 
  
            // we are going to read data line by line
            
            String older = null;
            String newest = null;
            List<String> trainingPhrasesParts = new ArrayList<>();
            while ((nextRecord = csvReader.readNext()) != null) { 
            	
            	newest = nextRecord[0];
            	if(older != null) {
            		if(!newest.equals(older)){
            			IntentManagement.updateIntentForTrainingData(older, trainingPhrasesParts);	
                	}
            	}
            	trainingPhrasesParts.add(nextRecord[1]);
            	older =  newest;
            }
            if(trainingPhrasesParts.size() != 0) {
            	IntentManagement.updateIntentForTrainingData(older, trainingPhrasesParts);	
        		trainingPhrasesParts = new ArrayList<>();
            }
            
            csvReader.close();
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }*/
}
