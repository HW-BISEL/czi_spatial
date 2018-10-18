package uk.bizel.czi.model;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.bizel.czi.exceptions.BadPositionException;
import uk.bizel.czi.exceptions.WrongNumberOfArgumentsException;

public class GutComponentTest {

	@Test
	public void testConstructor() {
		GutComponent gc = new GutComponent(GutComponentName.ANUS, (short) 0, (short) 4);
		assertEquals("should be anus", GutComponentName.ANUS, gc.getName());
		assertEquals("should be 0", (short) 0, gc.getStartPosition());
		assertEquals("should be 4", (short) 4, gc.getEndPosition());
	}
	
	@Test
	public void testConstructor_apr() {
		GutComponent gc = new GutComponent(GutComponentName.APR, (short) 10);
		assertEquals("should be apr", GutComponentName.APR, gc.getName());
		assertEquals("should be 10", (short) 10, gc.getStartPosition());
		assertEquals("should be 10", (short) 10, gc.getEndPosition());
	}
	
	@Test
	public void testConstructor_icv() {
		GutComponent gc = new GutComponent(GutComponentName.ICV, (short) 150);
		assertEquals("should be icv", GutComponentName.ICV, gc.getName());
		assertEquals("should be 150", (short) 150, gc.getStartPosition());
		assertEquals("should be 150", (short) 150, gc.getEndPosition());
	}	
	
	
	@Test(expected = BadPositionException.class)
	public void testConstructor_badPosition_APR() {
		new GutComponent(GutComponentName.APR, (short) -151);
	}	

	@Test(expected = BadPositionException.class)
	public void testConstructor_badPosition_ICV() {
		new GutComponent(GutComponentName.ICV, (short) -151);
	}	
	
	@Test(expected = BadPositionException.class)
	public void testConstructor_2High_badPosition_APR() {
		new GutComponent(GutComponentName.APR, (short) 151);
	}	

	@Test(expected = BadPositionException.class)
	public void testConstructor_2High_badPosition_ICV() {
		new GutComponent(GutComponentName.ICV, (short) 151);
	}		
	
	@Test(expected = WrongNumberOfArgumentsException.class)
	public void testConstructor_wrongParams_apr() {
		new GutComponent(GutComponentName.APR, (short) 0, (short) 4);
	}
	
	@Test(expected = WrongNumberOfArgumentsException.class)
	public void testConstructor_wrongParams_anus() {
		new GutComponent(GutComponentName.ANUS, (short) 0);
	}	
	
	@Test
	public void compareToTest_noEndPos() {
		GutComponent apr = new GutComponent(GutComponentName.APR,(short) 10);
		GutComponent icv = new GutComponent(GutComponentName.ICV,(short) 150);
		
		assertEquals("apr should be less than icv", -1, apr.compareTo(icv));
		assertEquals("icv should be greater than apr", 1, icv.compareTo(apr));
		assertEquals("icv should be equal to icv", 0, icv.compareTo(icv));
	}
	
	@Test
	public void compareToTest_endPos() {
		GutComponent an = new GutComponent(GutComponentName.ANUS, (short) 0, (short) 4);
		GutComponent si = new GutComponent(GutComponentName.SIGMOID, (short) 4, (short) 10);
		
		assertEquals("an should be less than si", -1, an.compareTo(si));
		assertEquals("si should be greater than an", 1, si.compareTo(an));
		assertEquals("si should be equal to si", 0, si.compareTo(si));
	}
	
	@Test
	public void compareToTest_mixPos() {
		GutComponent apr = new GutComponent(GutComponentName.APR,(short) 10);
		GutComponent si = new GutComponent(GutComponentName.SIGMOID, (short) 4, (short) 10);
		
		assertEquals("apr should be greater than si", 1, apr.compareTo(si));
		assertEquals("si should be less than apr", -1, si.compareTo(apr));		
	}	
}
