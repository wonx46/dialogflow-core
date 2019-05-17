package com.phincon.dialogflow.rest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVReader;
import com.phincon.dialogflow.management.IntentManagement;

@RestController
@RequestMapping("phincon/dialogflow/intent")
public class IntentController {
	
	@Autowired
	IntentManagement intentManagement;

	@RequestMapping(value = "/detect", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> detectIntentText(@RequestParam(value = "textValue") String textValue) throws Exception {
		
		String sessionId = UUID.randomUUID().toString();
	    Object out = intentManagement.detectIntent(textValue, sessionId);
	    return new ResponseEntity<Object>(out, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteIntent(@RequestParam(value = "textValue") String textValue) throws Exception {
		
		Object out = intentManagement.deleteIntent(textValue);
	    return new ResponseEntity<Object>(out, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createIntent(@RequestParam(value = "textValue") String textValue) throws Exception {
		return null;
	}
	
	@RequestMapping(value = "/upload/training_phase", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadTrainingPhase(@RequestParam(value = "filePath") String filePath) {
		
		try {
			FileReader filereader = new FileReader(filePath);
			
			CSVReader csvReader = new CSVReader(filereader); 
            String[] nextRecord;
            
            String oldIntentName = null;
            String newIntentName = null;
            List<String> listTrainingData = new ArrayList<>();
            
            Object result = null;
            while ((nextRecord = csvReader.readNext()) != null) { 
            	
            	newIntentName = nextRecord[0];
            	if(oldIntentName != null) {
            		if(!newIntentName.equals(oldIntentName)){
            			result = intentManagement.updateIntentForTrainingData(oldIntentName, listTrainingData);
                	}
            	}
            	listTrainingData.add(nextRecord[1]);
            	oldIntentName =  newIntentName;
            }
            if(listTrainingData.size() != 0) {
            	result = intentManagement.updateIntentForTrainingData(oldIntentName, listTrainingData);	
            	listTrainingData = new ArrayList<>();
            }
            
            csvReader.close();
            return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/upload/message_response", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadMessageResponse(@RequestParam(value = "filePath") String filePath) {
		try {
			FileReader filereader = new FileReader(filePath);
			
			CSVReader csvReader = new CSVReader(filereader); 
            String[] nextRecord;
            
            String oldIntentName = null;
            String newIntentName = null;
            List<String> listMessageResponse = new ArrayList<>();
            
            Object result = null;
            while ((nextRecord = csvReader.readNext()) != null) { 
            	
            	newIntentName = nextRecord[0];
            	if(oldIntentName != null) {
            		if(!newIntentName.equals(oldIntentName)){
            			result = intentManagement.updateIntentForMessageResponse(oldIntentName, listMessageResponse);
                	}
            	}
            	listMessageResponse.add(nextRecord[1]);
            	oldIntentName =  newIntentName;
            }
            if(listMessageResponse.size() != 0) {
            	result = intentManagement.updateIntentForMessageResponse(oldIntentName, listMessageResponse);	
            	listMessageResponse = new ArrayList<>();
            }
            
            csvReader.close();
            return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}
