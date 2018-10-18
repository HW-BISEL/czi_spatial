package uk.bizel.czi.model;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.bizel.czi.exceptions.BadPositionException;
import uk.bizel.czi.exceptions.ComponentNotFoundException;
import uk.bizel.czi.exceptions.GutComponentAlreadyExists;

public class ColonTest {

	// should be mocked!
	
	@Test
	public void test() {
		Colon c = new Colon();
		assertEquals("length should be 0", 0 ,c.getAllComponents().length);
		
		GutComponent gc = new GutComponent(GutComponentName.ICV, (short) 150);
		c.addComponent(gc);
		assertEquals("length should be 1", 1 ,c.getAllComponents().length);
		
		GutComponent gc2 = new GutComponent(GutComponentName.APR, (short) 10);
		c.addComponent(gc2);
		assertEquals("length should be 2", 2 ,c.getAllComponents().length);
		
	}

	
	@Test(expected = GutComponentAlreadyExists.class)
	public void addComponentTwice_shouldFail() {
		Colon c = new Colon();
		GutComponent gc = new GutComponent(GutComponentName.ICV, (short) 150);
		c.addComponent(gc);
		c.addComponent(gc);
	}
	
	
	@Test 
	public void getComponent() {
		Colon c = new Colon();			
		GutComponent gc = new GutComponent(GutComponentName.ICV, (short) 150);
		c.addComponent(gc);				
		GutComponent gc2 = new GutComponent(GutComponentName.APR, (short) 10);
		c.addComponent(gc2);	
		
		assertEquals("should be ICV",  gc, c.getComponent(GutComponentName.ICV));
	}
	
	@Test (expected=ComponentNotFoundException.class)
	public void getComponentNotFound() {
		Colon c = new Colon();			
		c.getComponent(GutComponentName.ANUS);
	}	
	
	@Test(expected = BadPositionException.class) 
	public void getComponentGivenPosition_fail_positionTooLow() {
		Colon c = new Colon();	
		c.whichComponentGivenPosition((short) -1);
	}
	
	@Test(expected = BadPositionException.class) 
	public void getComponentGivenPosition_fail_positionTooHigh() {
		Colon c = new Colon();	
		c.whichComponentGivenPosition((short) -151);
	}

	@Test(expected = ComponentNotFoundException.class) 
	public void getComponentGivenPosition_fail_noComponent() {
		Colon c = new Colon();	
		c.whichComponentGivenPosition((short) 51);
	}	
	
	
	@Test(expected = ComponentNotFoundException.class) 
	public void getComponentGivenPosition_1() {
		Colon c = new Colon();	
		GutComponent gc = new GutComponent(GutComponentName.CAECUM, (short) 50, (short) 60);
		c.addComponent(gc);		
		c.whichComponentGivenPosition((short) 14).toLowerCase();
	}
	
	@Test(expected = ComponentNotFoundException.class) 
	public void getComponentGivenPosition_2() {
		Colon c = new Colon();	
		GutComponent gc = new GutComponent(GutComponentName.CAECUM, (short) 50, (short) 60);
		c.addComponent(gc);		
		c.whichComponentGivenPosition((short) 65).toLowerCase();
	}		
	
	@Test
	public void getComponentGivenPosition() {
		Colon c = new Colon();	
		GutComponent gc = new GutComponent(GutComponentName.CAECUM, (short) 145, (short) 150);
		c.addComponent(gc);		
		assertEquals("should be caecum", "caecum", c.whichComponentGivenPosition((short) 147).toLowerCase());
	}
	
}
