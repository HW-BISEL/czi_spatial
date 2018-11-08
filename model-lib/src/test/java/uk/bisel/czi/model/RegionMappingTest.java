package uk.bisel.czi.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegionMappingTest {

	@Test
	public void test() {
		RegionMapping rm = new RegionMapping();
		assertEquals("check name", "", rm.getName());
		assertEquals("check description", "", rm.getDescription());
		assertEquals("check start", (short) 0, rm.getStartPosition());
		assertEquals("check start", (short) 0, rm.getEndPosition());
		
		rm = new RegionMapping((short) 1, (short) 10, "test region");
		assertEquals("check name", "test region", rm.getName());
		assertEquals("check description", "", rm.getDescription());
		assertEquals("check start", (short) 1, rm.getStartPosition());
		assertEquals("check start", (short) 10, rm.getEndPosition());

		rm = new RegionMapping((short) 1, (short) 10, "test region3", "description");
		assertEquals("check name", "test region3", rm.getName());
		assertEquals("check description", "description", rm.getDescription());
		assertEquals("check start", (short) 1, rm.getStartPosition());
		assertEquals("check start", (short) 10, rm.getEndPosition());
	}

}
