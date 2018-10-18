package uk.bizel.czi.exceptions;

public class BadStartPositionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadStartPositionException(short startPosition, short endPosition) {
		super("startPosition ("+startPosition+") must be less than endPosition ("+endPosition+")");
	}	
}
