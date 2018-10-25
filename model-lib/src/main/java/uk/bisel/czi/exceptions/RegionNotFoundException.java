package uk.bisel.czi.exceptions;

public class RegionNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RegionNotFoundException(String name) {		
		super("Region (called "+name+") does not exist");
	}
	
	public RegionNotFoundException(short position) {		
		super("No region at position: "+String.valueOf(position));
	}	
}
