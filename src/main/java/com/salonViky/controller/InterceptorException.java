package com.salonViky.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InterceptorException {
	//@ExceptionHandler({ArithmeticException.class, NullPointerException.class,Exception.class})
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
		Map<String, Object> response = new HashMap<>();
		response.put("ok", false);
		response.put("Error", e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
		 @ExceptionHandler(MethodArgumentNotValidException.class)
		    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		        Map<String, String> errors = new HashMap<>();
		        ex.getBindingResult().getAllErrors().forEach(error -> {
		            String fieldName = ((org.springframework.validation.FieldError) error).getField();
		            String errorMessage = error.getDefaultMessage();
		            errors.put(fieldName, errorMessage);
		        });
		        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		    }
		    
		    
		    
			// Maneja excepciones estándar como NullPointerException
			@ExceptionHandler(NullPointerException.class)
			public ResponseEntity<Map<String, Object>> handleNullPointerException(NullPointerException e) {
				Map<String, Object> response = new HashMap<>();
				response.put("ok", false);
				response.put("error", e.getMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			// Maneja excepciones como IllegalArgumentException
			@ExceptionHandler(IllegalArgumentException.class)
			public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
				Map<String, Object> response = new HashMap<>();
				response.put("ok", false);
				response.put("error", e.getMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			// Maneja RuntimeException
			@ExceptionHandler(RuntimeException.class)
			public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException e) {
				Map<String, Object> response = new HashMap<>();
				response.put("ok", false);
				response.put("error", e.getMessage());
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}   
		
}
