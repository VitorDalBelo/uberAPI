package com.example.UberApi.erroHandle;

import java.util.ArrayList;

import org.springframework.validation.FieldError;

public class ErroDependenciaNaoEncontrada extends Exception{
	private ArrayList<FieldError> fieldErrors;
	
	public ErroDependenciaNaoEncontrada(FieldError f) {
		this.fieldErrors = new ArrayList<FieldError>();
		this.fieldErrors.add(f);
	}
	public void fieldAdd(FieldError f) {
		this.fieldErrors.add(f);
	}
	public ArrayList<FieldError> getFieldErrors() {
		return fieldErrors;
	}

}
