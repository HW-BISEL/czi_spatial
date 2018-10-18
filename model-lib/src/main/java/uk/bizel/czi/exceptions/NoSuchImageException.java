package uk.bizel.czi.exceptions;

public class NoSuchImageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public NoSuchImageException(String imageId) {
		super("There is no image matching id: "+imageId);
	}
		
}
