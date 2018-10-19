package uk.bisel.czi.data;

import java.util.List;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import uk.bisel.czi.exceptions.NoSuchImageException;
import uk.bisel.czi.model.Image2PositionMapping;

public class NotADao {

	private EntityManager em = null;
	
	public NotADao () {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();				
	}
	
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
	
	public Image2PositionMapping[] getAllMappings() {
		Query query = em.createQuery("from Image2PositionMapping");
		List<Image2PositionMapping> allMappings = query.getResultList();		
		Image2PositionMapping[] result = new Image2PositionMapping[allMappings.size()];
		return allMappings.toArray(result);						
	}
	
	public Image2PositionMapping[] getImagesAtPosition(short position) {
		short upperBoundary = (short) (((position + 5) <=  150) ? position + 5 :  150);
		short lowerBoundary = (short) (((position -  5) >=  0) ? position -  5 :  0);
		return getImagesFromRange(lowerBoundary, upperBoundary);
	}
	
	public Image2PositionMapping[] getImagesFromRange(short start, short end) {				
		Query query = em.createQuery("from Image2PositionMapping i where i.position >= " + start + " and i.position <= " + end);
		List<Image2PositionMapping> allMappings = query.getResultList();		
				
		if(allMappings.isEmpty()) return new Image2PositionMapping[1];
		
		TreeSet<Image2PositionMapping> sortedMappings = new TreeSet<>();
		sortedMappings.addAll(allMappings);
		Image2PositionMapping[] result = new Image2PositionMapping[sortedMappings.size()];
		return sortedMappings.toArray(result);
	}	
	
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}
	
	
	public static void main(String[] args) {
		NotADao obj = new NotADao();
		Image2PositionMapping[] allMappings = obj.getAllMappings();
		System.out.println(allMappings.length);
		for(Image2PositionMapping mapping : allMappings) {
			System.out.println(mapping.toJson());
		}
		
		System.out.println("*****************");
		
		short[] allPositions = obj.getPositionsFromImage("12");
		for(short position: allPositions) {
			System.out.println(position);
		}
		
		System.out.println("*****************");
		
		
	}
	
}
