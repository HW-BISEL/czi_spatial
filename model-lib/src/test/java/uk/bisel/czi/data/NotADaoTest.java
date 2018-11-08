package uk.bisel.czi.data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import uk.bisel.czi.exceptions.BadPositionException;
import uk.bisel.czi.exceptions.BadStartPositionException;
import uk.bisel.czi.exceptions.RegionNotFoundException;
import uk.bisel.czi.exceptions.NoSuchImageException;
import uk.bisel.czi.exceptions.PointNotFoundException;
import uk.bisel.czi.model.GutComponentName;
import uk.bisel.czi.model.Image2PositionMapping;

public class NotADaoTest {

	@Test
	public void getAllMappings() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] allMappings = obj.getAllImageMappings();
		
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
		
	
	
//
	
	@Test
	public void getPositionOfRegion() {
		NotADao obj = new NotADao();
		short[] allPositions = obj.getPositionOfRegion("anus");
		assertEquals("length should be 2", 2, allPositions.length);
		assertEquals("should be 0", 0, allPositions[0]);
		assertEquals("should be 4", 4, allPositions[1]);
	}
	
	@Test(expected = RegionNotFoundException.class)
	public void getPositionOfRegion_regionNotFound() {
		NotADao obj = new NotADao();
		obj.getPositionOfRegion("banana");
	}	
	
	
	@Test
	public void getImagesFromRegion() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] allImages = obj.getImagesFromRegion("anus");
		assertTrue(allImages.length > 1 );
	}
	
//
	
	@Test
	public void getPositionofPoint() {
		NotADao obj = new NotADao();
		assertEquals("should be 10", 10, obj.getPositionOfPoint("APR"));
	}
	
	@Test (expected = PointNotFoundException.class)
	public void getPositionofPoint_pointNotExists() {
		NotADao obj = new NotADao();
		assertEquals("should be 10", 10, obj.getPositionOfPoint("banana"));
	}	
	
//
	
	@Test 
	public void getImagesAtPoint() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] allImages = obj.getImagesAtPoint("APR");
		Image2PositionMapping[] allImages2 = obj.getImagesAtPosition((short) 10);
		assertEquals("should be same result", allImages2.length, allImages.length);
		for(int i = 0; i < allImages.length; i++) {
			assertEquals(i + " - should be same result", allImages2[i].getImageId(), allImages[i].getImageId());
		}
	}
	
//
	
	@Test
	public void getAllRegions() {
		NotADao obj = new NotADao();
		String[] allImages = obj.getAllRegions();
		assertEquals("7 regions", 7, allImages.length);					
	}
	
	
// 
	
	@Test
	public void getAllPoints() {
		NotADao obj = new NotADao();
		String[] allImages = obj.getAllPoints();
		assertEquals("2 points", 2, allImages.length);		
	}	
}
