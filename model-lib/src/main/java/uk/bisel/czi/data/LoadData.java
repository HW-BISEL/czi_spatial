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

public class LoadData {
	private EntityManager em;
	
    public static void main( String[] args ) throws IOException {
    	LoadData a = new LoadData();
    	a.readCSV();
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
    }
}
