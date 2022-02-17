package com.serrano.app.forum.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIException {
	 private final String message;
	 private final List<String> errors;
	 private final HttpStatus httpStatus;
	 private final Instant timestamp;
	 
	 public APIException(String message, List<String> errors, HttpStatus status) {
		 this.message = message;
		 this.errors = errors;
		 this.httpStatus = status;
		 this.timestamp = Instant.now();
	 }
}
