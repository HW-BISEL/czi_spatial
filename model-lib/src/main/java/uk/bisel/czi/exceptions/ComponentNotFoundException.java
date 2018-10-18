package uk.bisel.czi.exceptions;

public class ComponentNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ComponentNotFoundException(String name) {		
		super("Component (called "+name+") does not exist");
	}
	
	public ComponentNotFoundException(short position) {		
		super("No component at position: "+String.valueOf(position));
	}	
}
