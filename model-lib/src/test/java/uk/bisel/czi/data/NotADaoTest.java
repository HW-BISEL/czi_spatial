package uk.bisel.czi.data;

import org.junit.Before;
import org.junit.Test;
import uk.bisel.czi.exceptions.BadPositionException;
import uk.bisel.czi.exceptions.DatabaseException;
import uk.bisel.czi.exceptions.NoSuchImageException;
import uk.bisel.czi.exceptions.PointNotFoundException;
import uk.bisel.czi.exceptions.RegionNotFoundException;
import uk.bisel.czi.model.GutComponentName;
import uk.bisel.czi.model.Image2PositionMapping;
import uk.bisel.czi.model.Species;

import static org.junit.Assert.*;

public class NotADaoTest {

    private NotADao dao;

    @Before
    public void setup() {
        dao = new NotADao();
    }
   
    
    @Test
    public void getRegionFromPosition_pass() {
    	assertEquals( GutComponentName.ANAL_CANAL ,dao.getRegionFromPosition(Species.ABSTRACT, (short) 5)[0].getName());
    	assertEquals( GutComponentName.RECTUM ,dao.getRegionFromPosition(Species.ABSTRACT, (short) 150)[0].getName());
    	assertEquals( GutComponentName.CAECUM ,dao.getRegionFromPosition(Species.HUMAN, (short) 1500)[0].getName());
    }
    
    @Test (expected = RegionNotFoundException.class)
    public void getRegionFromPosition_fail_tooLow() {
    	dao.getRegionFromPosition(Species.ABSTRACT, (short) -1);    	
    }    
    
    @Test (expected = RegionNotFoundException.class)
    public void getRegionFromPosition_fail_tooHigh() {
    	dao.getRegionFromPosition(Species.ABSTRACT, (short) 1300);    	
    }      
    //
    @Test
    public void getICVPosition_pass() {
        assertEquals(1500, dao.getICVPosition(Species.HUMAN));
        assertEquals(140, dao.getICVPosition(Species.MOUSE));
        assertEquals(250, dao.getICVPosition(Species.RAT));
    }

    @Test
    public void getAllPoints_pass() {
        assertEquals(5,dao.getAllPoints(Species.HUMAN).length);
        assertEquals(2,dao.getAllPoints(Species.RAT).length);
        assertEquals(2,dao.getAllPoints(Species.MOUSE).length);
    }
    //
    @Test
    public void getPositionOfPoint_pass() {
        assertEquals(0, dao.getPositionOfPoint(GutComponentName.ANUS.toString(), Species.HUMAN));
        assertEquals(0, dao.getPositionOfPoint(GutComponentName.ANUS.toString(), Species.MOUSE));
        assertEquals(0, dao.getPositionOfPoint(GutComponentName.ANUS.toString(), Species.RAT));
    }

    @Test (expected = PointNotFoundException.class)
    public void getPositionOfPoint_fail() {
        assertEquals(0, dao.getPositionOfPoint(GutComponentName.HEPATIC_FLEXURE.toString(), Species.RAT));
    }
    //
    @Test
    public void getAllImageMappings_pass() {
        assertEquals(244, dao.getAllImageMappings().length);
    }

    @Test
    public void getAllImageMappings_mouse_pass() {
        assertEquals(74, dao.getAllImageMappings(Species.MOUSE).length);
    }
    //
    @Test
    public void getImagesFromRange_pass() {
        assertTrue(dao.getImagesFromRange((short) 10, (short) 50, Species.HUMAN).length > 0);
        assertTrue(dao.getImagesFromRange((short) 20, (short) 80, Species.RAT).length > 0);
    }
 
    @Test (expected = BadPositionException.class)
    public void getImagesFromRange_fail() {
        dao.getImagesFromRange((short) 260, (short) 300, Species.RAT);
    }
    //
    @Test
    public void getImagesAtPosition_pass_icv() {
    	assertTrue(dao.getImagesAtPosition((short) 136, Species.MOUSE).length > 0); 
    }    
    
    @Test
    public void getImagesAtPosition_pass_anus() {
    	assertTrue(dao.getImagesAtPosition((short) 3, Species.MOUSE).length > 0); 
    }      
    
    @Test (expected = BadPositionException.class)
    public void getImagesAtPosition_fail_tooLow() {
        dao.getImagesAtPosition((short) -1, Species.MOUSE);
    }


    @Test (expected = BadPositionException.class)
    public void getImagesAtPosition_fail_tooHigh() {
        dao.getImagesAtPosition((short) 141, Species.MOUSE);
    }

    @Test
    public void getImagesAtPosition_pass() {
        assertTrue(dao.getImagesAtPosition((short) 82, Species.RAT).length > 0 );
    }
    //
    @Test (expected = NoSuchImageException.class)
    public void getPositionsFromImage_fail() {
        dao.getPositionsFromImage("a9");
    }

    @Test
    public void getPositionsFromImage_pass() {	
        assertTrue(dao.getPositionsFromImage("r8").length > 1);
    }
    //
    @Test
    public void convertProportionalDistanceToActualDistance_pass() {
    	assertEquals((short) 5, dao.convertProportionalDistanceToActualDistance((short) 0, (short) 10, 0.5F));
    	assertEquals((short) 7, dao.convertProportionalDistanceToActualDistance((short) 0, (short) 10, 0.75F));    	
    }
    
    @Test
    public void convertProportionalDistanceToActualDistance_pass_arguementsWrongWayRound() {
    	assertEquals((short) 5, dao.convertProportionalDistanceToActualDistance((short) 10, (short) 0, 0.5F));    	  
    }    
    
    @Test (expected = IllegalArgumentException.class)
    public void convertProportionalDistanceToActualDistance_fail_tooLow() {
    	assertEquals((short) 5, dao.convertProportionalDistanceToActualDistance((short) -1, (short) 10, 0.5F)); 
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void convertProportionalDistanceToActualDistance_fail_tooLow2() {
    	assertEquals((short) 5, dao.convertProportionalDistanceToActualDistance((short) 0, (short) -10, 0.5F)); 
    }    

    @Test (expected = IllegalArgumentException.class)
    public void convertProportionalDistanceToActualDistance_fail_tooLow3() {
    	assertEquals((short) 5, dao.convertProportionalDistanceToActualDistance((short) 0, (short) 10, -0.5F)); 
    }  
    //
    @Test
    public void calculateProportionalDistance_pass()  {    	
    	assertEquals(0.5F, dao.calculateProportionalDistance(Species.ABSTRACT, (short) 600), 0.001);
    	assertEquals(0.5F, dao.calculateProportionalDistance(Species.ABSTRACT, (short) 150), 0.001);
    	assertEquals(0.0F, dao.calculateProportionalDistance(Species.ABSTRACT, (short) 0), 0.001);
    }
    //
    @Test
    public void getSection_pass() {
    	assertEquals(100, dao.getSection(Species.ABSTRACT, GutComponentName.RECTUM).getStartPosition());
    	assertEquals(100, dao.getSection(Species.MOUSE, GutComponentName.CAECUM).getStartPosition());
    }
    
    @Test (expected = DatabaseException.class)
    public void getSection_fail() {
    	dao.getSection(Species.MOUSE, GutComponentName.SIGMOID).getStartPosition();    	
    }    
    //
    @Test
    public void getSpecies2SectionNameFromSpecies1Position_pass() {
    	assertEquals(GutComponentName.CAECUM, dao.getSpecies2SectionNameFromSpecies1Position(Species.MOUSE, (short) 120, Species.HUMAN));
    	assertEquals(GutComponentName.RECTUM, dao.getSpecies2SectionNameFromSpecies1Position(Species.RAT, (short) 70, Species.HUMAN));
    }
    
    @Test
    public void getSpecies2SectionNameFromSpecies1Position_pass_boundary() {
    	assertEquals(GutComponentName.CAECUM, dao.getSpecies2SectionNameFromSpecies1Position(Species.MOUSE, (short) 100, Species.HUMAN)); 
    }    
    
    @Test (expected = DatabaseException.class)
    public void getSpecies2SectionNameFromSpecies1Position_fail() {
    	dao.getSpecies2SectionNameFromSpecies1Position(Species.MOUSE, (short) 141, Species.HUMAN);    	
    }    
    //
    @Test
    public void mapping_pass() {
    	assertEquals((short) 1485, dao.mapping(Species.MOUSE, (short) 120, Species.HUMAN));
    	assertEquals((short) 142, dao.mapping(Species.RAT, (short) 70, Species.HUMAN));
    }
    //
}