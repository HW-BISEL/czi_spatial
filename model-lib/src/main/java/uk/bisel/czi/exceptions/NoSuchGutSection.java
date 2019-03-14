package uk.bisel.czi.exceptions;

public class NoSuchGutSection extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoSuchGutSection(String name) {		
		super("Component (called "+name+") does not exist");
	}	
}
