package uk.bisel.czi.data;

import org.junit.Before;
import org.junit.Test;
import uk.bisel.czi.exceptions.BadPositionException;
import uk.bisel.czi.exceptions.NoSuchImageException;
import uk.bisel.czi.exceptions.PointNotFoundException;
import uk.bisel.czi.model.GutComponentName;
import uk.bisel.czi.model.Species;

import static org.junit.Assert.*;

public class NotADaoTest {

    private NotADao dao;

    @Before
    public void setup() {
        dao = new NotADao();
    }

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

    @Test
    public void getAllImageMappings_pass() {
        assertEquals(244, dao.getAllImageMappings().length);
    }

    @Test
    public void getAllImageMappings_mouse_pass() {
        assertEquals(74, dao.getAllImageMappings(Species.MOUSE).length);
    }

    @Test
    public void getImagesFromRange_pass() {
        assertTrue(dao.getImagesFromRange((short) 10, (short) 50, Species.HUMAN).length > 0);
        assertTrue(dao.getImagesFromRange((short) 20, (short) 25, Species.RAT).length > 0);
    }

    @Test (expected = BadPositionException.class)
    public void getImagesFromRange_fail() {
        dao.getImagesFromRange((short) 260, (short) 300, Species.RAT);
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

    @Test (expected = NoSuchImageException.class)
    public void getPositionsFromImage_fail() {
        assertTrue(dao.getPositionsFromImage("h101").length > 1);
    }

    @Test
    public void getPositionsFromImage_pass() {
        assertTrue(dao.getPositionsFromImage("m6").length > 1);
    }

}