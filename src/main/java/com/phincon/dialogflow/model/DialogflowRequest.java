package com.phincon.dialogflow.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "session",
    "message"
})
public class DialogflowRequest {

	@JsonProperty("session")
	private String session;
	
	@JsonProperty("message")
	private String message;

	@JsonProperty("session")
	public String getSession() {
		return session;
	}

	@JsonProperty("session")
	public void setSession(String session) {
		this.session = session;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
