package uk.bisel.czi.data;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.bisel.czi.model.Image2PositionMapping;

public class NotADaoTest {

	@Test
	public void getAllMappings() {
		NotADao obj = new NotADao();
		Image2PositionMapping[] allMappings = obj.getAllMappings();
		
	}

}
