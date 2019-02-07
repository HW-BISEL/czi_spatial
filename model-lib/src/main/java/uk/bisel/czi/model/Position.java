package uk.bisel.czi.model;

import uk.bisel.czi.data.NotADao;
import uk.bisel.czi.exceptions.BadPositionException;
import uk.bisel.czi.exceptions.BadStartPositionException;

public final class Position {

	public static boolean validatePosition(short position, Species species) {
		NotADao dao = new NotADao();
		if (position < 0 || position > dao.getICVPosition(species)) {
			throw new BadPositionException(position);
		}
		return true;
	}
	
	public static boolean validatePosition(short start, short end, Species species) {
		validatePosition(start, species);
		validatePosition(end, species);
		if(start <= end) return true;
		throw new BadStartPositionException(start, end);		
	}	
}
