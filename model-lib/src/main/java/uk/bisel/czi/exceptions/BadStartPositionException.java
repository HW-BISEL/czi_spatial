package uk.bisel.czi.exceptions;

public class BadStartPositionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadStartPositionException(float startPosition, float endPosition) {
		super("startPosition ("+startPosition+") must be less than endPosition ("+endPosition+")");
	}	
}
