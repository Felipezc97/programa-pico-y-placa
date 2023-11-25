package com.felipe.samples.picoplaca.exception;

public class PicoPlacaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PicoPlacaException(String errorMessage) {
		super(errorMessage);
	}
	
}
