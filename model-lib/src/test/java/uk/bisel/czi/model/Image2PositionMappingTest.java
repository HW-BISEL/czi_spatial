package uk.bisel.czi.model;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.bisel.czi.exceptions.BadPositionException;
import uk.bisel.czi.model.Image2PositionMapping;

public class Image2PositionMappingTest {

	@Test (expected = BadPositionException.class)
	public void testConstructor_fail_tooLow() {
		new Image2PositionMapping("1", (short) -1, Species.HUMAN);
	}

	@Test (expected = BadPositionException.class)
	public void testConstructor_fail_tooHigh() {
		new Image2PositionMapping("1", (short) 1501, Species.HUMAN);
	}
	
	@Test 
	public void testConstructor_pass() {
		Image2PositionMapping obj = new Image2PositionMapping("1", (short) 11, Species.HUMAN);
		assertEquals("imageId test", "1", obj.getImageId());
		assertEquals("position test", (short) 11, obj.getPosition());
	}
	
	@Test
	public void testCompare() {
		Image2PositionMapping one = new Image2PositionMapping("1", (short) 1 , Species.HUMAN);
		Image2PositionMapping two = new Image2PositionMapping("2", (short) 2, Species.HUMAN);
		Image2PositionMapping three = new Image2PositionMapping("3", (short) 3, Species.HUMAN);
		
		assertEquals("should be equal", true, three.equals(three));
		assertEquals("should be unequal", false, three.equals(two));
		
		assertEquals("should be less than", -1, one.compareTo(two));
		assertEquals("should be greater than", 1, three.compareTo(two));
		assertEquals("should be equal", 0, one.compareTo(one));		
		
	}
	
	@Test
	public void testEquals() {
		Image2PositionMapping obj = new Image2PositionMapping("1", (short) 4, Species.HUMAN);
		assertEquals("should be false", false, obj.equals(new Integer(4)));
		
		Image2PositionMapping obj2 = new Image2PositionMapping("1", (short) 2, Species.HUMAN);
		assertEquals("should be false", false, obj.equals(obj2));
	}
	
	@Test
	public void toStringTest() {
		Image2PositionMapping obj = new Image2PositionMapping("1", (short) 4, Species.HUMAN);
		String result = "1: 4";
		assertEquals("Simple test string", result, obj.toString());	
	}
}
