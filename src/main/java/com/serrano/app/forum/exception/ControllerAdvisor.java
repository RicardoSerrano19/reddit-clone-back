package com.serrano.app.forum.exception;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{

	@ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        APIException exception = new APIException(
        	ex.getMessage(),
        	ex.getErrors(),
            status,
            Instant.now());
        return new ResponseEntity<>(exception,status);
    }
	
	@ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Object> handleTokenNotFoundException(TokenNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        APIException exception = new APIException(
        	ex.getMessage(),
        	ex.getErrors(),
            status,
            Instant.now());
        return new ResponseEntity<>(exception,status);
    }
	
	@ExceptionHandler(CustomApiException.class)
    public ResponseEntity<Object> handleCustomApiException(CustomApiException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        APIException exception = new APIException(
        	ex.getMessage(),
        	ex.getErrors(),
            status,
            Instant.now());
        return new ResponseEntity<>(exception,status);
    }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String message = "Argument(s) not valid";
		List<String> errors = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(e -> e.getDefaultMessage())
	            .collect(Collectors.toList());
	            
		
	        APIException exception = new APIException(
	            message,
	            errors,
	            status,
	            Instant.now());

	        return new ResponseEntity<>(exception, status);
	}


}
