package uk.bisel.czi.data;

import java.util.List;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import uk.bisel.czi.model.*;
import uk.bisel.czi.exceptions.*;


public class NotADao {

    private EntityManager em = null;

    public NotADao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
        em = emf.createEntityManager();
    }


    //

    public Image2PositionMapping[] getPositionsFromImage(String imageId) {
        Query query = em.createQuery("from Image2PositionMapping i where imageId = '" + imageId + "' order by i.position");
        List<Image2PositionMapping> allMappings;
        try {
            allMappings = query.getResultList();
        } catch (Exception e) {
            throw new NoSuchImageException(imageId);
        }

        if (allMappings == null || allMappings.isEmpty()) { throw new NoSuchImageException(imageId); }

        return returnSortedArrayOfImageMappings(allMappings);
    }

    /**
     * @param start
     * @param end
     * @param species
     * @return
     */
    public Image2PositionMapping[] getImagesFromRange(short start, short end, Species species) {
        Position.validatePosition(start, end, species);
        Query query = em.createQuery("FROM Image2PositionMapping i WHERE species LIKE '" + species + "' AND i.position >= " + start + " AND i.position <= " + end);
        List<Image2PositionMapping> allMappings = query.getResultList();

        if (allMappings.isEmpty()) { return new Image2PositionMapping[1]; }

        return returnSortedArrayOfImageMappings(allMappings);
    }

    public Image2PositionMapping[] returnSortedArrayOfImageMappings(List<Image2PositionMapping> allMappings) {
        TreeSet<Image2PositionMapping> sortedMappings = new TreeSet<>();
        sortedMappings.addAll(allMappings);
        Image2PositionMapping[] result = new Image2PositionMapping[sortedMappings.size()];
        return sortedMappings.toArray(result);
    }


    /**
     *
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
        Query query = em.createQuery("from Image2PositionMapping");
        return runGetAlImageMappings(query);
    }

    public Image2PositionMapping[] getAllImageMappings(Species species) {
        Query query = em.createQuery("from Image2PositionMapping WHERE species LIKE '"+species.toString()+"'");
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
		Query query = em.createQuery("from PointMapping where name LIKE '"+name.toLowerCase().trim()+"' AND species LIKE '"+species+"'");
		List<PointMapping> allComponents = query.getResultList();		
		if (allComponents.isEmpty()) throw new PointNotFoundException(name);
		if(allComponents.size() > 1) throw new RuntimeException("Unique component not identified");
		return allComponents.get(0).getPosition();				
	}

    /**
     * Assumes ICV is maximum position of model
     *
     * @param species The name of the model
     * @return position of ICV in that species
     * @see Species
     */
    public int getICVPosition(Species species) {
        return getPositionOfPoint(GutComponentName.ICV.toString(), species);
    }


    /**
     * All known landmarks in a species
     *
     * @param species
     * @return
     */
	public String[] getAllPoints(Species species) {
		Query query = em.createQuery("from PointMapping where species LIKE '"+species+"'");
		List<PointMapping> allComponents = query.getResultList();	
		if (allComponents.isEmpty()) throw new DatabaseException("No points in database");
		String[] names = new String[allComponents.size()];
		for (int i = 0; i < allComponents.size(); i++) {
			names[i] = allComponents.get(i).getName();
		}
		return names;
	}



 		
	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}
	
}
