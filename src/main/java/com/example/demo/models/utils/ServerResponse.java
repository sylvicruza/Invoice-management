package com.example.demo.models.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerResponse {

	private boolean success;
	private String message;
	private Object data;
	
	@JsonIgnore
	private int status;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public ServerResponse() {
	}

	public ServerResponse(boolean success, String message, Object data, int status) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.status = status;
	}

	public void setResponse(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}



	public static HttpStatus getStatus(int status) {
        return HttpStatus.valueOf(status);
    }




	public static ServerResponse exceptionMessage(Exception e){
		e.printStackTrace();
		return new ServerResponse(false,
				"Something went wrong",
				"",
				ServerResponseStatus.INTERNAL_SERVER_ERROR);
	}

	public static ServerResponse badRequest(String message){
		return new ServerResponse(false,
				message,
				"",
				ServerResponseStatus.FAILED);
	}

	public static ServerResponse notFound(String message){
		return new ServerResponse(false,
				message,
				"",
				ServerResponseStatus.NOT_FOUND);
	}

	public static boolean isValidInput(String input){
		return input != null && !input.isEmpty();
	}
    
}
