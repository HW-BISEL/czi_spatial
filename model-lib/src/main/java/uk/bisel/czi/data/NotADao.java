package uk.bisel.czi.data;

import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import uk.bisel.czi.exceptions.DatabaseException;
import uk.bisel.czi.exceptions.NoImageFoundException;
import uk.bisel.czi.exceptions.NoSuchGutSection;
import uk.bisel.czi.exceptions.NoSuchImageException;
import uk.bisel.czi.exceptions.PointNotFoundException;
import uk.bisel.czi.exceptions.RegionNotFoundException;
import uk.bisel.czi.model.GutComponentName;
import uk.bisel.czi.model.GutSection;
import uk.bisel.czi.model.Image2PositionMapping;
import uk.bisel.czi.model.Model2AbstractMapping;
import uk.bisel.czi.model.PointMapping;
import uk.bisel.czi.model.Position;
import uk.bisel.czi.model.Species;


public class NotADao {

    private EntityManager em = null;
	
    private Logger logger = Logger.getLogger(NotADao.class.getName());

    public NotADao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
        em = emf.createEntityManager();
    }

    /**
     * Returns point in species 2 which maps to position1 from species1
     * 
     * @param species1
     * @param position1
     * @param species2
     * @return 
     */
    public short mapping(Species species1, short position1, Species species2) {
    	GutSection[] allSections = this.getRegionFromPosition(species1, position1);
    	
    	if(species1 == species2) return position1;
    	
    	// special rules because the mouse & rat dont map well to the abstract
    	if(species1 == Species.HUMAN && species2 == Species.MOUSE) {
    		if(allSections[0].getName() == GutComponentName.SIGMOID || allSections[0].getName() == GutComponentName.DESCENDING || allSections[0].getName() == GutComponentName.TRANSVERSE) {    			
    			float species1PD = calculateProportionalDistance((short) 160, (short) 1310, position1);
    			return convertProportionalDistanceToActualDistance((short) 5, (short) 70, species1PD);
    		}
    	} else if(species2 == Species.HUMAN && species1 == Species.MOUSE) {    		
    		if(allSections[0].getName() == GutComponentName.MID_DISTAL) {
    			float species1PD = calculateProportionalDistance(species1, position1);
    			return convertProportionalDistanceToActualDistance((short) 160, (short) 1310, species1PD); 
    		}
    	} else if(species1 == Species.HUMAN && species2 == Species.RAT) {
    		if(allSections[0].getName() == GutComponentName.SIGMOID || allSections[0].getName() == GutComponentName.DESCENDING || allSections[0].getName() == GutComponentName.TRANSVERSE || allSections[0].getName() == GutComponentName.ASCENDING) {
    			float species1PD = calculateProportionalDistance((short) 160, (short) 1470, position1);
    			return convertProportionalDistanceToActualDistance((short) 80, (short) 180, species1PD);
    		}
    	} else if(species2 == Species.HUMAN && species1 == Species.RAT) {
    		if(allSections[0].getName() == GutComponentName.PROXIMAL_MID_DISTAL) {
    			float species1PD = calculateProportionalDistance(species1, position1); 
    			return convertProportionalDistanceToActualDistance((short) 160, (short) 1470, species1PD); 
    		}
    	}
    	
    	float species1PD = calculateProportionalDistance(species1, position1);      	
    	GutComponentName name2 = getSpecies2SectionNameFromSpecies1Position(species1, position1, species2);    	
    	GutSection section2 = getSection(species2, name2);
    	return convertProportionalDistanceToActualDistance(section2.getStartPosition(), section2.getEndPosition(), species1PD);    	    	
    }
    
    public short wholeColonMapping(Species species1, short position1, Species species2) {
    	float species1PD = calculateProportionalDistanceWholeColon(species1, position1);     	
    	return convertProportionalDistanceToActualDistance(getMinPosition(species2), getMaxPosition(species2), species1PD);
    }    
    
	public short getMinPosition(Species species) {
		return 0;
	}    
    
	public short getMaxPosition(Species species) {
		String queryString = "FROM GutSection WHERE species LIKE '" + species + "' ORDER BY endPosition DESC";
		logger.info(queryString);
		Query query = em.createQuery(queryString);
		query.setMaxResults(1);
    	List<GutSection> allSections = (List<GutSection>) query.getResultList();
    	if(allSections.isEmpty()) {
    		throw new DatabaseException("Cannot find any sections for the species "+species);
    	}
    	return allSections.get(0).getEndPosition();
	}        		
	
	protected float calculateProportionalDistanceWholeColon(Species species, short position) {	
		short minPos = getMinPosition(species);
		short maxPos = getMaxPosition(species);		
		short distance = (short) (maxPos - minPos);			
		short difference = (short) (position - minPos);		
		if(difference == (short) 0) return 0;
		return ((float) difference / distance);
	} 	
	
	
    /**
     * Calculates proportional distance in region of position. For example, if the section goes from 10 to 20 and the position
     * is 15, then 0.5 will be returned.
     * 
     * @param species
     * @param position
     * @return
     */
	protected float calculateProportionalDistance(Species species, short position) {		
		GutSection[] allSections = this.getRegionFromPosition(species, position);		
		short distance = (short) (allSections[0].getEndPosition() - allSections[0].getStartPosition());				
		short difference = (short) (position - allSections[0].getStartPosition());		
		if(difference == (short) 0) return 0;
		return ((float) difference / distance);
	}    
	
	protected float calculateProportionalDistance(short start, short end, short position) {				
		short distance = (short) (end - start);				
		short difference = (short) (position - start);		
		if(difference == (short) 0) return 0;
		return ((float) difference / distance);
	} 	
    
	/**
	 * Converts a proportional distance in a section to a real distance along a specified range. For example if start = 10 and end = 20 and 
	 * pD is 0.5 then 15 will be returned.
	 *  
	 * @param start
	 * @param end
	 * @param pD
	 * @return
	 */
	protected short convertProportionalDistanceToActualDistance(short start, short end, float pD) {
		if(start < 0 || end < 0 || pD < 0) throw new IllegalArgumentException("Must be positive values");
		if(start > end) {
			short temp = start;
			start = end;
			end = temp;
		}	
		return (short) (Math.round((end - start) * pD) + start);
	}	
	
	/**
	 * Returns the GutSection object for a given section in a given species. 
	 * Includes the Abstract model
	 * 
	 * @param species
	 * @param name
	 * @return
	 */
	public GutSection getSection(Species species, GutComponentName name) {
		String queryString = "FROM GutSection WHERE species LIKE '" + species + "' AND name LIKE '"+name+"'";
		logger.info(queryString);
		Query query = em.createQuery(queryString);
    	List<GutSection> allSections = (List<GutSection>) query.getResultList();
    	if(allSections.isEmpty()) {
    		throw new DatabaseException("No "+name+" from species " + species.toString()+" in database.");
    	}
    	return allSections.get(0);
	}
    
    /**
     * Returns the section of gut which includes a given position when told the species. 
     * 
     * @param species
     * @param position
     * @return
     */
    public GutSection[] getRegionFromPosition(Species species, short position) {
    	String queryString = "FROM GutSection WHERE species LIKE '" + species + "' AND startPosition <= " + position +" AND endPosition >= "+ position;
    	logger.info(queryString);
    	Query query = em.createQuery(queryString);
    	List<GutSection> allSections = query.getResultList();
    	if(allSections.isEmpty()) {
    		throw new RegionNotFoundException(species, position);
    	}
    	
    	GutSection[] validSections = new GutSection[allSections.size()];
    	validSections = allSections.toArray(validSections);
    	return validSections;
    }
    
    /**
     * Converts a given species-position pair into a section of the gut from the species AND then maps that section into a section from the second species (ie species2).
     * The trip goes through the Abstract model; ie, species1 section -> abstract section - > species2 section.
     * 
     * If a boundary point is given, the name returned will be the proximal-most section
     * 
     * @param species1
     * @param position
     * @param species2
     * @return
     */
    public GutComponentName getSpecies2SectionNameFromSpecies1Position(Species species1, Short position, Species species2) {
    	String queryString = "FROM Model2AbstractMapping WHERE species1 LIKE '" + species1 + "' AND species1StartPosition <= " + position + " AND species1StopPosition >= "+ position;
    	logger.info(queryString);
    	Query query = em.createQuery(queryString);
     	List<Model2AbstractMapping> allMaps = query.getResultList();
    	if(allMaps.isEmpty()) {
    		throw new DatabaseException("No mapping from "+species1+" position " + position + " to the abstract model");
    	}      	
    	
    	short abstractStart = allMaps.get(0).getAbstractStartPosition();
    	short abstractStop = allMaps.get(0).getAbstractStopPosition();
    	    	
    	float species1PD = calculateProportionalDistance(species1, position);    	
    	short abstractPoint = convertProportionalDistanceToActualDistance(abstractStart, abstractStop, species1PD);
    	queryString = "FROM Model2AbstractMapping WHERE species1 LIKE '"+species2+"' AND abstractStartPosition <= " + abstractPoint + " AND abstractStopPosition >= "+ abstractPoint +" ORDER BY abstractStopPosition DESC";
    	logger.info(queryString);
    	query = em.createQuery(queryString);
     	allMaps = query.getResultList();
    	if(allMaps.isEmpty()) {
    		throw new DatabaseException("No mapping from abstract position " + abstractPoint + " to "+species2);
    	}      	  
    	return allMaps.get(0).getSpecies1SectionName();
    }
    
    
    // does this make sense?
//    public GutSection getAbstractSectionFromSection(GutSection section1) {
//    	Query query = em.createQuery("from Model2AbstractMapping where species1 LIKE '" + section1.getSpecies() + "' AND species1Name like '"+section1.getName()+"'");
//    	List<Model2AbstractMapping> allSections = query.getResultList();
//    	if(allSections.isEmpty()) {
//    		throw new DatabaseException("No mapping from "+section1.getSpecies()+"."+section1.getName().toString()+" to the abstract model");
//    	}   
//    	query = em.createQuery("from GutSection where species LIKE '" + Species.ABSTRACT + "' AND name like '"+allSections.get(0).getSpecies1Name()+"'");
//    	List<GutSection> abstractSection = query.getResultList();
//    	if(abstractSection.isEmpty()) {
//    		throw new DatabaseException("Cannot find the abstract section "+allSections.get(0).getSpecies1Name()+" in the database.");    		
//    	} 
//    	return abstractSection.get(0);
//    }
    
    
//    public Model2AbstractMapping[] getMappings2Abstract(Species species, GutComponentName name) {
//    	Query query = em.createQuery("from Model2AbstractMapping where species1 LIKE '" + species + "' AND species1Name LIKE '"+name+"'");
//    	List<Model2AbstractMapping> allMappings = query.getResultList();
//    	if(allMappings.isEmpty()) {
//    		throw new DatabaseException("No mappings from species " + species.toString()+"."+name+" to ABSTRACT in database.");
//    	}
//    	Model2AbstractMapping[] allMappingsArray = new Model2AbstractMapping[allMappings.size()];
//    	allMappingsArray = allMappings.toArray(allMappingsArray);
//    	return allMappingsArray;
//    }
    

    
    /**
     * Returns the position (in the model) of an image given the imageID.
     * As each image is mapped to a particular model, the position will be in the model which the image is mapped to. 
     * 
     * @param imageId
     * @return
     */
    public Image2PositionMapping[] getPositionsFromImage(String imageId) {
    	String queryString = "from Image2PositionMapping i where imageId = '" + imageId + "' order by i.position";
    	logger.info(queryString);
        Query query = em.createQuery(queryString);
        List<Image2PositionMapping> allMappings;
        allMappings = query.getResultList();
        if(allMappings.isEmpty()) {        	
            throw new NoSuchImageException(imageId);
        }

        if (allMappings == null || allMappings.isEmpty()) { throw new NoSuchImageException(imageId); }

        return returnSortedArrayOfImageMappings(allMappings);
    }

    /**
     * Returns all the images in a range of points (start -> end) for a given model.
     * 
     * @param start
     * @param end
     * @param species
     * @return
     */
    public Image2PositionMapping[] getImagesFromRange(short start, short end, Species species) {
        Position.validatePosition(start, end, species);
        String queryString = "FROM Image2PositionMapping i WHERE species LIKE '" + species + "' AND i.position >= " + start + " AND i.position <= " + end;
        logger.info(queryString);
        Query query = em.createQuery(queryString);
        List<Image2PositionMapping> allMappings = query.getResultList();

        if (allMappings.isEmpty()) { throw new NoImageFoundException(species, start, end); }

        return returnSortedArrayOfImageMappings(allMappings);
    }

    protected Image2PositionMapping[] returnSortedArrayOfImageMappings(List<Image2PositionMapping> allMappings) {
        TreeSet<Image2PositionMapping> sortedMappings = new TreeSet<>();
        sortedMappings.addAll(allMappings);
        Image2PositionMapping[] result = new Image2PositionMapping[sortedMappings.size()];
        return sortedMappings.toArray(result);
    }


    /**
     * Returns the images mapped to a given species-position pair
     *
     * @param position
     * @param species
     * @return
     */
	public Image2PositionMapping[] getImagesAtPosition(short position, Species species) {
		Position.validatePosition(position, species);
		short upperBoundary = (short) (((position + 5) <=  getICVPosition(species)) ? position + 5 :  getICVPosition(species));
		short lowerBoundary = (short) (((position -  5) >=  0) ? position -  5 :  0);
		return getImagesFromRange(lowerBoundary, upperBoundary, species);
	}





    /**
     * Gets all image mappings
     *
     * @return
     */
    public Image2PositionMapping[] getAllImageMappings() {
    	String queryString = "from Image2PositionMapping";
    	logger.info(queryString);
        Query query = em.createQuery(queryString);
        return runGetAlImageMappings(query);
    }

    /**
     * Gets all images mappings for a given species
     * 
     * @param species
     * @return
     */
    public Image2PositionMapping[] getAllImageMappings(Species species) {
    	String queryString = "from Image2PositionMapping WHERE species LIKE '"+species.toString()+"'";
    	logger.info(queryString);
        Query query = em.createQuery(queryString);
        return runGetAlImageMappings(query);
    }

    private Image2PositionMapping[] runGetAlImageMappings(Query query) {
        List<Image2PositionMapping> allMappings = query.getResultList();
        Image2PositionMapping[] result = new Image2PositionMapping[allMappings.size()];
        return allMappings.toArray(result);
    }

	/**
	 * Returns position of a known point, eg, ICV
     *
	 * @param name
	 * @param species
	 * @return
	 */
	public short getPositionOfPoint(String name, Species species) {
		String queryString = "from PointMapping where name = '"+name.toUpperCase().trim()+"' AND species = '"+species+"'";
		logger.info(queryString);
		Query query = em.createQuery(queryString);
		List<PointMapping> allComponents = query.getResultList();		
		if (allComponents.isEmpty()) throw new PointNotFoundException(name);
		if(allComponents.size() > 1) throw new RuntimeException("Unique component not identified");
		return allComponents.get(0).getPosition();				
	}

    /**
     * Returns position of ICV. Assumes ICV is maximum position of model
     *
     * @param species The name of the model
     * @return position of ICV in that species
     * @see Species
     */
    public short getICVPosition(Species species) {
        return getPositionOfPoint(GutComponentName.ICV.toString(), species);
    }


    /**
     * Returns all known landmarks in a species
     *
     * @param species
     * @return
     */
	public String[] getAllPoints(Species species) {
		String queryString = "from PointMapping where species LIKE '"+species+"'";
		logger.info(queryString);
		Query query = em.createQuery(queryString);
		List<PointMapping> allComponents = query.getResultList();	
		if (allComponents.isEmpty()) throw new DatabaseException("No points in database");
		String[] names = new String[allComponents.size()];
		for (int i = 0; i < allComponents.size(); i++) {
			names[i] = allComponents.get(i).getName();
		}
		return names;
	}

	/**
	 * Returns all images mapped to a given species-section pair.
	 * 
	 * @param species 
	 * @param name The name of the section
	 * @return All images mapped to that section
	 */
	public Image2PositionMapping[] getImagesFromRegion(Species species, String name) {		
		if(name.equalsIgnoreCase("cecum")) name = "caecum";
		GutSection section = null;
		try {						
			section = getSection(species, GutComponentName.valueOf(name.trim().toUpperCase().replaceAll(" ", "_")));
		}
		catch (IllegalArgumentException e) {
			logger.info("Cannot convert "+name+" into a GutComponentName");
			logger.severe(e.getMessage());
			throw new NoSuchGutSection(name);
		}		
		return getImagesFromRange(section.getStartPosition(), section.getEndPosition(), species);		
	}


 		
	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}
	
}
