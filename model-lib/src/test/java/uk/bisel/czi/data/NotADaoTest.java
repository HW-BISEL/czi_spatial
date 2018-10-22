package uk.bisel.czi.data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import uk.bisel.czi.exceptions.BadPositionException;
import uk.bisel.czi.exceptions.BadStartPositionException;
import uk.bisel.czi.exceptions.ComponentNotFoundException;
import uk.bisel.czi.exceptions.NoSuchImageException;
import uk.bisel.czi.model.GutComponentName;
import uk.bisel.czi.model.Image2PositionMapping;

public class NotADaoTest {

	@Test
	public void getAllMappings() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] allMappings = obj.getAllMappings();
		
		assertEquals("number of mappings", 100, allMappings.length);
		
	}

	
	@Test 
	public void getPositionsFromImage() {
		NotADao obj = new NotADao();
		short[] allPositions = obj.getPositionsFromImage("12");
		assertEquals("number of positions", 3, allPositions.length);		
	}
	
	@Test (expected=NoSuchImageException.class)
	public void getPositionsFromImage_noImage_low() {
		NotADao obj = new NotADao();
		short[] allPositions = obj.getPositionsFromImage("0");		
	}	
	
	@Test (expected=NoSuchImageException.class)
	public void getPositionsFromImage_noImage_high() {
		NotADao obj = new NotADao();
		short[] allPositions = obj.getPositionsFromImage("150");		
	}		
	
	
	@Test
	public void getImagesFromRange() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromRange((short) 6, (short) 14);
		assertEquals("number of results", 4, results.length);	
	}
	
	@Test
	public void getImagesFromRange_testLower() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromRange((short) 4, (short) 15);
		assertEquals("number of results", 6, results.length);	
	}	
	
	@Test
	public void getImagesFromRange_testHigher() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromRange((short) 77, (short) 147);
		assertEquals("number of results", 34, results.length);	
	}		
	
//	@Test
//	public void getImagesFromRange_noResult() {
//		NotADao obj = new NotADao();
//		Image2PositionMapping[] results = obj.getImagesFromRange((short) 0, (short) 0);		
//		assertEquals("empty result", null, results);	
//	}	
	
	@Test(expected = BadPositionException.class)
	public void getImagesFromRange_invalidRange_tooHigh() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromRange((short) 0, (short) 151);		
	}
	
	@Test(expected = BadPositionException.class)
	public void getImagesFromRange_invalidRange_tooLow() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromRange((short) -10, (short) 151);		
	}	

	@Test(expected = BadStartPositionException.class)
	public void getImagesFromRange_invalidRange_wrongOrder() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromRange((short) 10, (short) 1);		
	}
	
//	
	
	@Test
	public void getImagesAtPosition() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesAtPosition((short) 12);
		assertEquals("number of results", 5, results.length);	
	}
	
	@Test
	public void getImagesAtPosition_low() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesAtPosition((short) 4);
		assertEquals("number of results", 5, results.length);	
	}	
	
	@Test
	public void getImagesAtPosition_high() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesAtPosition((short) 146);
		assertEquals("number of results", 6, results.length);	
	}
	
	@Test (expected=BadPositionException.class)
	public void getImagesAtPosition_positionTooLow() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesAtPosition((short) -1);		
	}

	@Test (expected=BadPositionException.class)
	public void getImagesAtPosition_positionTooHigh() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesAtPosition((short) 151);		
	}
	
//	
	
	@Test
	public void getImagesFromComponent_anus() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromComponent(GutComponentName.ANUS.toString());
		assertEquals("number of results", 2, results.length);
	}
	
	@Test
	public void getImagesFromComponent_apr() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromComponent(GutComponentName.APR.toString());
		assertEquals("number of results", 1, results.length);
	}	
	
	@Test (expected=ComponentNotFoundException.class)
	public void getImagesFromComponent_fail() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] results = obj.getImagesFromComponent("nbb");		
	}		
	
//	
	
	@Test
	public void getPositionOfComponent() {
		NotADao obj = new NotADao();
		short[] results = obj.getPositionOfComponent("ANUS");
		assertEquals("number of results", 2, results.length);
		assertEquals("start pos is 0", 0, results[0]);
		assertEquals("end pos is 4", 4, results[1]);
	}
	
	@Test(expected=ComponentNotFoundException.class)
	public void getPositionOfComponent_unknown() {
		NotADao obj = new NotADao();
		short[] results = obj.getPositionOfComponent("unknon");		
	}	
}
