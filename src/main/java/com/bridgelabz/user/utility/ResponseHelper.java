package com.bridgelabz.user.utility;

import com.bridgelabz.user.models.Response;

public class ResponseHelper {
	
	public Response responseStatus(int statusCode, String message) {
		
		Response response = new Response();
		response.setCode(statusCode);
		response.setMessage(message);
		
		return response;
	}
}