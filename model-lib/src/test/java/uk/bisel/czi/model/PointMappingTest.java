package uk.bisel.czi.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PointMappingTest {

	@Test
	public void test() {				
		PointMapping pm = new PointMapping("test1", (short) 1);
		assertEquals("check name", "test1", pm.getName());
		assertEquals("check position", (short) 1, pm.getPosition());
		
		pm = new PointMapping("test2", (short) 2, "point 2");
		assertEquals("check name", "test2", pm.getName());
		assertEquals("check position", (short) 2, pm.getPosition());
		assertEquals("check description", "point 2", pm.getDescription());
		
		pm = new PointMapping();
		assertEquals("check name", "", pm.getName());
		assertEquals("check position", (short) 0, pm.getPosition());
		assertEquals("check description", "", pm.getDescription());		
	}

}
