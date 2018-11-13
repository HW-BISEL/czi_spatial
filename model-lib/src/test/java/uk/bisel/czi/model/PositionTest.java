package uk.bisel.czi.model;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.bisel.czi.exceptions.*;
import uk.bisel.czi.model.Position;

public class PositionTest {
	
	@Test
	public void testConstructor() {
		Position p = new Position();
	}

	@Test
	public void testValidatePosition() {
		assertEquals("should be valid", true, Position.validatePosition((short) 0));
		assertEquals("should be valid", true, Position.validatePosition((short) 45));
		assertEquals("should be valid", true, Position.validatePosition((short) 150));		
	}
	
	@Test(expected = BadPositionException.class) 
	public void tooLow_exception() {
		Position.validatePosition((short) -1);
	}
	
	@Test(expected = BadPositionException.class) 
	public void tooHigh_exception() {
		Position.validatePosition((short) 151);
	}	

	@Test
	public void startVsStopPositions() {
		assertEquals("should be valid", true, Position.validatePosition((short) 0, (short) 1));
		assertEquals("should be valid", true, Position.validatePosition((short) 10, (short) 150));
	}
	
	@Test(expected = BadStartPositionException.class)
	public void startVsStopPositions_exception() {
		Position.validatePosition((short) 1, (short) 0);		
	}	
		
	public void startVsStopPositions_sameValue_noexception() {
		assertEquals("same position now valid", true, Position.validatePosition((short) 1, (short) 1));		
	}	
}
