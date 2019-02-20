package uk.bisel.czi.exceptions;

import uk.bisel.czi.model.Species;

public class NoImageFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoImageFoundException(Species species, short position1, short position2) {
		super("There is no image mapped to " + species + " in range "+position1 + " -> "+position2);
	}

}
