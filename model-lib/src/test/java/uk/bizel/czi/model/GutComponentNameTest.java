package uk.bizel.czi.model;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.bisel.czi.model.GutComponentName;

public class GutComponentNameTest {

	@Test
	public void simple_pass() {
		GutComponentName name = GutComponentName.ANUS;
		assertEquals("should both be anus", name, GutComponentName.ANUS);
	}

}
