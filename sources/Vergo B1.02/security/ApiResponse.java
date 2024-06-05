package xyz.vergoclient.security;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
	
	// The response code
	@SerializedName(value = "status")
	public ResponseStatus status;
	
	// Used for some additional info
	@SerializedName(value = "statusText")
	public String statusText = "";
	
	// Response object used for stuff
	@SerializedName(value = "responseObject")
	public Object responseObject = null;
	
	public static enum ResponseStatus{
		OK,
		ERROR,
		FORBIDDEN;
	}
	
}
