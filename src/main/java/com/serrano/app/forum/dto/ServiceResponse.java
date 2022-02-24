package com.serrano.app.forum.dto;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String message;
	@JsonInclude(value = Include.NON_NULL)
	private Object data;
	private HttpStatus status;
	private Instant timestamp;
	
	public ServiceResponse(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
		this.timestamp = Instant.now();
	}
	
	public ServiceResponse(String message, HttpStatus status, Object data) {
		this.message = message;
		this.data = data;
		this.status = status;
		this.timestamp = Instant.now();
	}
}
