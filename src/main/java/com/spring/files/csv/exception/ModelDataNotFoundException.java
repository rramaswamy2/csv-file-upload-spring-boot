package com.spring.files.csv.exception;

public class ModelDataNotFoundException extends RuntimeException {

	
	public ModelDataNotFoundException(Long id) {
		
		super("Model data with id "+ id +" not found in DB");
	}
}
