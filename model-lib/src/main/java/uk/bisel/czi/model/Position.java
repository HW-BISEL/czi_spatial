package uk.bisel.czi.model;

import uk.bisel.czi.data.NotADao;
import uk.bisel.czi.exceptions.BadPositionException;
import uk.bisel.czi.exceptions.BadStartPositionException;
import uk.bisel.czi.exceptions.PointNotFoundException;

public final class Position {

	public static boolean validatePosition(short position, Species species) {
		NotADao dao = new NotADao();
		int icvPos = 10000;
		try {
			icvPos = dao.getICVPosition(species);
		} catch(PointNotFoundException pnfe) {
			return false;
		} catch (RuntimeException re) {
			return false;
		}

		if (position < 0 || position > icvPos) {
			throw new BadPositionException("Min value is 0. Max is "+icvPos+"; you specified "+ position);
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
