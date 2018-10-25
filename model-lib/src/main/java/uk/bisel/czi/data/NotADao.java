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
	
	public NotADao () {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();				
	}
	
	
	//
	
	public short[] getPositionsFromImage(String imageId) {
		Query query = em.createQuery("from Image2PositionMapping i where imageId = "+imageId+" order by i.position");
		List<Image2PositionMapping> allMappings = query.getResultList();		
		
		if(allMappings.isEmpty()) throw new NoSuchImageException(imageId);
		
		short[] result = new short[allMappings.size()];
		for(int i = 0; i < allMappings.size(); i++) {
			result[i] = allMappings.get(i).getPosition();
		}
		
		return result;
	}
	
	public Image2PositionMapping[] getAllImageMappings() {
		Query query = em.createQuery("from Image2PositionMapping");
		List<Image2PositionMapping> allMappings = query.getResultList();		
		Image2PositionMapping[] result = new Image2PositionMapping[allMappings.size()];
		return allMappings.toArray(result);						
	}
	
	public Image2PositionMapping[] getImagesAtPosition(short position) {
		Position.validatePosition(position);
		short upperBoundary = (short) (((position + 5) <=  150) ? position + 5 :  150);
		short lowerBoundary = (short) (((position -  5) >=  0) ? position -  5 :  0);
		return getImagesFromRange(lowerBoundary, upperBoundary);
	}
	
	public Image2PositionMapping[] getImagesFromRange(short start, short end) {				
		Position.validatePosition(start, end);
		Query query = em.createQuery("from Image2PositionMapping i where i.position >= " + start + " and i.position <= " + end);
		List<Image2PositionMapping> allMappings = query.getResultList();		
				
		if(allMappings.isEmpty()) return new Image2PositionMapping[1];
		
		TreeSet<Image2PositionMapping> sortedMappings = new TreeSet<>();
		sortedMappings.addAll(allMappings);
		Image2PositionMapping[] result = new Image2PositionMapping[sortedMappings.size()];
		return sortedMappings.toArray(result);
	}	
	
	@Deprecated
	public short[] getPositionOfComponent(String name) {		
		Query query = em.createQuery("from GutComponent c where name LIKE '"+name.toUpperCase().trim()+"'");
		List<GutComponent> allComponents = query.getResultList();		
		if (allComponents.isEmpty()) throw new RegionNotFoundException(name);
		if(allComponents.size() > 1) throw new RuntimeException("Unique component not identified");
		short[] positions = new short[2];
		positions[0] = allComponents.get(0).getStartPosition();
		positions[1] = allComponents.get(0).getEndPosition();
		return positions;			
	}
	
	@Deprecated
	public Image2PositionMapping[] getImagesFromComponent(String name) {
		short[] position = getPositionOfComponent(name);
		return getImagesFromRange(position[0], position[1]);
	}
	
	public short[] getPositionOfRegion(String name) {		
		Query query = em.createQuery("from RegionMapping where name LIKE '"+name.toLowerCase().trim()+"'");
		List<RegionMapping> allComponents = query.getResultList();		
		if (allComponents.isEmpty()) throw new RegionNotFoundException(name);
		if(allComponents.size() > 1) throw new RuntimeException("Unique component not identified");
		short[] positions = new short[2];
		positions[0] = allComponents.get(0).getStartPosition();
		positions[1] = allComponents.get(0).getEndPosition();
		return positions;			
	}	
	
	public Image2PositionMapping[] getImagesFromRegion(String name) {
		short[] position = getPositionOfRegion(name);
		return getImagesFromRange(position[0], position[1]);
	}	
	
	public short getPositionOfPoint(String name) {
		Query query = em.createQuery("from PointMapping where name LIKE '"+name.toLowerCase().trim()+"'");
		List<PointMapping> allComponents = query.getResultList();		
		if (allComponents.isEmpty()) throw new PointNotFoundException(name);
		if(allComponents.size() > 1) throw new RuntimeException("Unique component not identified");
		return allComponents.get(0).getPosition();				
	}
	
	public Image2PositionMapping[] getImagesAtPoint(String name) {
		return getImagesAtPosition(getPositionOfPoint(name));		
	}	
		
	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}
	
}
