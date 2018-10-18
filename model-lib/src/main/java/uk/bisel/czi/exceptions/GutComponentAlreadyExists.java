package uk.bisel.czi.exceptions;

public class GutComponentAlreadyExists extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GutComponentAlreadyExists(String name) {		
		super("Component (called "+name+") already declared");
	}

}
