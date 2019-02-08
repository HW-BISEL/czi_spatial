package uk.bisel.czi.exceptions;

public class BadPositionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public BadPositionException(String message) {
		super("Position specified is outwith the limits. "+message);
	}	
}
