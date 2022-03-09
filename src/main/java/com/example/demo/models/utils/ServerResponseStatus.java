package com.example.demo.models.utils;

import org.springframework.http.HttpStatus;

public class ServerResponseStatus {

	
	 public static final int OK = HttpStatus.OK.value();

	 public static final int CREATED = HttpStatus.CREATED.value();

	 public static final int UPDATED = HttpStatus.ACCEPTED.value();

	 public static final int DELETED = HttpStatus.ACCEPTED.value();

	 public static final int FAILED = HttpStatus.BAD_REQUEST.value();

	 public static final int NOT_MODIFIED = HttpStatus.NOT_MODIFIED.value();

	 public static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();

	 public static final int NO_CONTENT = HttpStatus.NO_CONTENT.value();
	    
	 public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
	    
	 public static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

	    private ServerResponseStatus() {
	    }
}
