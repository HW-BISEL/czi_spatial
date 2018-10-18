package uk.bisel.czi.exceptions;

public class WrongNumberOfArgumentsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongNumberOfArgumentsException(String message) {
		super(message);
	}
}
