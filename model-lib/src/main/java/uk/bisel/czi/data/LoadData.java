package uk.bisel.czi.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import uk.bisel.czi.model.Image2PositionMapping;
import uk.bisel.czi.model.PointMapping;
import uk.bisel.czi.model.RegionMapping;

public class LoadData {
	private EntityManager em;
	
    public static void main( String[] args ) throws IOException {
    	LoadData a = new LoadData();
    	a.readCSV();    	
    	a.insertNewModel();    	    	
    }
    
    private void readCSV() throws IOException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();	
		EntityTransaction txn = em.getTransaction();
    	
        System.out.println("Importing dummy data");
                   
        BufferedReader br = new BufferedReader(new FileReader(new File("MOCK_DATA.csv")));
        String line = "";
        br.readLine(); // throw away header
        txn.begin();
        while((line = br.readLine()) != null) {
        	StringTokenizer st = new StringTokenizer(line, ",");
        	Image2PositionMapping temp = new Image2PositionMapping(st.nextToken(), (short) Integer.parseInt(st.nextToken()));
        	em.persist(temp);        	
        }
        txn.commit();
        br.close();
        em.clear();
        em.close();
    }
    
    private void insertNewModel() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();	
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		
		RegionMapping anal = new RegionMapping((short) 0, (short) 4, "anal");
		RegionMapping rectum = new RegionMapping((short) 4, (short) 16, "rectum");
		RegionMapping sigmoid = new RegionMapping((short) 16, (short) 56, "sigmoid");
		RegionMapping descending = new RegionMapping((short) 56, (short) 81, "descending");
		RegionMapping transverse = new RegionMapping((short) 81, (short) 131, "transverse");
		RegionMapping ascending = new RegionMapping((short) 131, (short) 146, "ascending");
		RegionMapping caecum = new RegionMapping((short) 146, (short) 150, "caecum");
		
		em.persist(anal);
		em.persist(rectum);
		em.persist(sigmoid);
		em.persist(descending);
		em.persist(transverse);
		em.persist(ascending);
		em.persist(caecum);
		
		PointMapping apr = new PointMapping("apr", (short) 10);
		PointMapping icv = new PointMapping("icv", (short) 150);
		em.persist(apr);
		em.persist(icv);
	
        txn.commit();
        em.clear();
        em.close();
        
    }

}
