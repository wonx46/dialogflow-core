package com.phincon.dialogflow.management;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.IntentView;
import com.google.cloud.dialogflow.v2.IntentsClient;
import com.google.cloud.dialogflow.v2.ProjectAgentName;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.cloud.dialogflow.v2.UpdateIntentRequest;
import com.google.cloud.dialogflow.v2.Intent.Message;
import com.google.cloud.dialogflow.v2.Intent.TrainingPhrase;
import com.google.cloud.dialogflow.v2.Intent.Message.Text;
import com.google.cloud.dialogflow.v2.Intent.TrainingPhrase.Part;
import com.google.cloud.dialogflow.v2.TextInput.Builder;

@Service
public class IntentManagement {

	@Value("${phincon.dialogflow.project.id}")
	private String projectId;
	
	@Value("${phincon.dialogflow.language.code}")
	private String languageCode;
	
	
	
	public Intent selectIntent(String displayName) throws Exception{
		try{
			IntentsClient intentsClient = IntentsClient.create();
			
			//ProjectAgentName parent = ProjectAgentName.of(projectId);
			ProjectAgentName parent = ProjectAgentName.of(projectId);
			
			
			Intent intentDialogflow = null;
			for(Intent intent : intentsClient.listIntents(parent).iterateAll()) {
				if (intent.getDisplayName().equals(displayName)) {
					intentDialogflow = intent;
					break;
					//listIntent.add(splitName[splitName.length - 1]);
		        }
			}
			return intentDialogflow;
		}catch(Exception e) {
			
		}
		return null;
	}
	
	public void createIntent(String intentName, List<String> listTrainingData, List<String> listMessageResponse) {
		try {
			IntentsClient intentsClient = IntentsClient.create();
			
			// Set the project agent name using the projectID (my-project-id)
			ProjectAgentName parent = ProjectAgentName.of(projectId);
		    
			// Build the intent
			Intent intent = Intent.newBuilder()
		          .setDisplayName(intentName)
		          .addMessages(messageResponse(listMessageResponse))
		          .addAllTrainingPhrases(listTrainingData(listTrainingData))
		          .build();
			
			// Performs the create intent request
			Intent response = intentsClient.createIntent(parent, intent);
		    System.out.format("Intent created: %s\n", response);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Object detectIntent(String displayName, String sessionId) {
		try{
			SessionsClient sessionsClient = SessionsClient.create();
			// Set the session name using the sessionId (UUID) and projectID (my-project-id)
		    SessionName session = SessionName.of(projectId, sessionId);
		    System.out.println("Session Path: " + session.toString());
		    
		    // Set the text (hello) and language code (en-US) for the query
	        Builder textInput = TextInput.newBuilder().setText(displayName).setLanguageCode(languageCode);

	        // Build the query with the TextInput
	        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

	        // Performs the detect intent request
	        DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

	        // Display the query result
	        QueryResult queryResult = response.getQueryResult();
		    
	        return queryResult.getFulfillmentText();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public Object updateIntentForTrainingData(String intentName, List<String> listTrainingData) {
		try {
			
			IntentsClient intentsClient = IntentsClient.create();
			
			Intent intent = selectIntent(intentName);
			String result = null;
			if(intent != null) {
				intent = Intent.newBuilder(intent)
		                .addAllTrainingPhrases(listTrainingData(listTrainingData))
		                .build();
				
				//Build the update request
	            UpdateIntentRequest request = UpdateIntentRequest.newBuilder()
	                    .setIntent(intent)
	                    .setIntentView(IntentView.INTENT_VIEW_FULL)
	                    .setLanguageCode("en")
	                    .build();
	            Intent response= intentsClient.updateIntent(request);
	            System.out.format("Intent updated: %s\n", response);
	            
	            if(response != null) {
	            	result = "success";
	            }
			}
			return result;
			
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public Object updateIntentForMessageResponse(String intentName, List<String> listMessageResponse) {
		try {
			
			IntentsClient intentsClient = IntentsClient.create();
			
			Intent intent = selectIntent(intentName);
			
			intent = Intent.newBuilder(intent)
	                .addMessages(messageResponse(listMessageResponse))
					.build();
			
			//Build the update request
            UpdateIntentRequest request = UpdateIntentRequest.newBuilder()
                    .setIntent(intent)
                    .setIntentView(IntentView.INTENT_VIEW_FULL)
                    .setLanguageCode(languageCode)
                    .build();
            Intent response= intentsClient.updateIntent(request);
            String result = null;
            if(response != null) {
            	result = "success";
            }
			return result;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	public static List<TrainingPhrase> listTrainingData(List<String> listTrainingData){
		// Build the trainingPhrases from the trainingPhrasesParts
		List<TrainingPhrase> trainingPhrases = new ArrayList<>();
		for (String trainingPhrase : listTrainingData) {
			trainingPhrases.add(
            TrainingPhrase.newBuilder().addParts(
                    Part.newBuilder().setText(trainingPhrase).build())
                .build());
		}
		return trainingPhrases;
	}
	
	public Message messageResponse(List<String> listMessageResponse) {
		// Build the message texts for the agent's response
	    Message message = Message.newBuilder()
	    	.setText(
	    			Text.newBuilder()
	    			.addAllText(listMessageResponse).build()
	    	).build();
	    
	    return message;
	}
	
	public Object deleteIntent(String intentName) throws Exception {
		// Instantiates a client
		try{
			IntentsClient intentsClient = IntentsClient.create();
			Intent intent = selectIntent(intentName);
			String result = null;
			if(intent != null) {
				// Performs the delete intent request
				intentsClient.deleteIntent(intent.getName());
				result = "success";
			}
			return result;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public Object createIntent(String intentName) throws Exception {
		// Instantiates a client
		try{
			IntentsClient intentsClient = IntentsClient.create();
			Intent intent = selectIntent(intentName);
			String result = null;
			if(intent != null) {
				// Performs the delete intent request
				intentsClient.deleteIntent(intent.getName());
				result = "success";
			}
			return result;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
