package uk.bizel.czi.model;

import uk.bizel.czi.exceptions.BadPositionException;
import uk.bizel.czi.exceptions.BadStartPositionException;

public final class Position {

	public static boolean validatePosition(short position) {
		if (position < 0 || position > 150) {
			throw new BadPositionException(position);
		}
		return true;
	}
	
	public static boolean validatePosition(short start, short end) {
		if(start < end) return true;
		throw new BadStartPositionException(start, end);		
	}	
}
