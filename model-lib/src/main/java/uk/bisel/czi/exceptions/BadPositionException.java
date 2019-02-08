package uk.bisel.czi.exceptions;

public class BadPositionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public BadPositionException(float position) {
		super("Position specified ("+position+") is outwith the limits.");
	}	
}
