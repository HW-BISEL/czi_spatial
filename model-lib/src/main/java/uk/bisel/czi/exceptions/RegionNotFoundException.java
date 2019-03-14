package uk.bisel.czi.exceptions;

import uk.bisel.czi.model.Species;

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
	
	public RegionNotFoundException(Species species, short position) {		
		super("Cannot map " + species + "."+position+" to the Abstract model.");
	}	
}
