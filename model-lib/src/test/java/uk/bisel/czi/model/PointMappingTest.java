package uk.bisel.czi.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PointMappingTest {

	@Test
	public void test() {				
		PointMapping pm = new PointMapping(GutComponentName.ANUS, (short) 1, Species.HUMAN);
		assertEquals("check name", GutComponentName.ANUS.toString(), pm.getName());
		assertEquals("check position", (short) 1, pm.getPosition());
		
		pm = new PointMapping(GutComponentName.ANUS, (short) 2,  Species.HUMAN);
		assertEquals("check name", GutComponentName.ANUS.toString(), pm.getName());
		assertEquals("check position", (short) 2, pm.getPosition());
		
		pm = new PointMapping();
		assertEquals("check position", (short) 0, pm.getPosition());
		assertEquals("check description", "", pm.getDescription());

		pm = new PointMapping(GutComponentName.ANUS, (short) 1, Species.HUMAN, "a description");
		assertEquals("check position", (short) 1, pm.getPosition());
		assertEquals(Species.HUMAN, pm.getSpecies());
		assertEquals("check description", "a description", pm.getDescription());
	}

}
