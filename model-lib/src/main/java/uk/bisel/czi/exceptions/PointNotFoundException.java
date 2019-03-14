package uk.bisel.czi.exceptions;

public class PointNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PointNotFoundException(String name) {		
		super("Point (called "+name+") does not exist");
	}
	
	public PointNotFoundException(float position) {
		super("No point at position: "+String.valueOf(position));
	}	
}
