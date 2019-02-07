package uk.bisel.czi.data;

import java.io.*;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import uk.bisel.czi.model.*;

public class LoadData {
	private EntityManager em;
	
    public static void main( String[] args ) throws IOException {
    	LoadData a = new LoadData();
    	a.insertNewModel();
    	a.randomGenerateImagePositions();
		System.exit(0);
    }


    private void randomGenerateImagePositions() {

    	int humanICV = 1000;
    	int mouseICV = 140;
    	int ratICV = 250;

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
    	for(int i = 0 ; i < 100; i++) {
			Image2PositionMapping temp = new Image2PositionMapping(Integer.valueOf(i).toString(), (short) Math.round(Math.random() * humanICV), Species.HUMAN);
			em.persist(temp);
		}
		for(int i = 101 ; i < 175; i++) {
			Image2PositionMapping temp = new Image2PositionMapping(Integer.valueOf(i).toString(),  (short) Math.round(Math.random() * mouseICV), Species.MOUSE);
			em.persist(temp);
		}
		for(int i = 180 ; i < 250; i++) {
			Image2PositionMapping temp = new Image2PositionMapping(Integer.valueOf(i).toString(),  (short) Math.round(Math.random() * ratICV), Species.RAT);
			em.persist(temp);
		}
		txn.commit();
		em.clear();
		em.close();
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
        	Image2PositionMapping temp = new Image2PositionMapping(st.nextToken(), (short) Integer.parseInt(st.nextToken()), Species.HUMAN);
        	em.persist(temp);        	
        }
        txn.commit();
        br.close();
        em.clear();
        em.close();
    }

    private void insertNewModel() {
    	insertNewModelHuman();
		insertNewModelMouse();
		insertNewModelRat();
	}

    private void insertNewModelHuman() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();	
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		
		GutSection anal = new GutSection((short) 0, (short) 40, GutComponentName.ANAL_CANAL, Species.HUMAN);
		GutSection rectum = new GutSection((short) 40, (short) 160, GutComponentName.RECTUM, Species.HUMAN);
		GutSection sigmoid = new GutSection((short) 160, (short) 560, GutComponentName.SIGMOID, Species.HUMAN);
		GutSection descending = new GutSection((short) 560, (short) 810, GutComponentName.DESCENDING, Species.HUMAN);
		GutSection transverse = new GutSection((short) 810, (short) 1310, GutComponentName.TRANSVERSE, Species.HUMAN);
		GutSection ascending = new GutSection((short) 1310, (short) 1460, GutComponentName.ASCENDING, Species.HUMAN);
		GutSection caecum = new GutSection((short) 1460, (short) 1500, GutComponentName.CAECUM, Species.HUMAN);
		em.persist(anal);
		em.persist(rectum);
		em.persist(sigmoid);
		em.persist(descending);
		em.persist(transverse);
		em.persist(ascending);
		em.persist(caecum);

		PointMapping anus = new PointMapping(GutComponentName.ANUS, (short) 0, Species.HUMAN);
		PointMapping apr = new PointMapping(GutComponentName.APR, (short) 100, Species.HUMAN);
		PointMapping sf = new PointMapping(GutComponentName.SPENLIC_FLEXURE, (short) 810, Species.HUMAN);
		PointMapping hf = new PointMapping(GutComponentName.HEPATIC_FLEXURE, (short) 1300,Species.HUMAN);
		PointMapping icv = new PointMapping(GutComponentName.ICV, (short) 1500, Species.HUMAN);
		em.persist(anus);
		em.persist(apr);
		em.persist(hf);
		em.persist(sf);
		em.persist(icv);
	
        txn.commit();
        em.clear();
        em.close();
    }

	private void insertNewModelMouse() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();

		GutSection anal = new GutSection((short) 0, (short) 2, GutComponentName.ANAL_CANAL, Species.MOUSE);
		GutSection rectum = new GutSection((short) 2, (short) 5, GutComponentName.RECTUM, Species.MOUSE);
		GutSection pmd = new GutSection((short) 5, (short) 100, GutComponentName.PROXIMAL_MID_DISTAL, Species.MOUSE);
		GutSection caecum = new GutSection((short) 100, (short) 140, GutComponentName.CAECUM, Species.MOUSE);
		em.persist(anal);
		em.persist(rectum);
		em.persist(pmd);
		em.persist(caecum);

		PointMapping anus = new PointMapping(GutComponentName.ANUS, (short) 0, Species.MOUSE);
		PointMapping icv = new PointMapping(GutComponentName.ICV, (short) 140, Species.MOUSE);
		em.persist(anus);
		em.persist(icv);

		txn.commit();
		em.clear();
		em.close();
	}

	private void insertNewModelRat() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();

		GutSection anal = new GutSection((short) 0, (short) 10, GutComponentName.ANAL_CANAL, Species.RAT);
		GutSection rectum = new GutSection((short) 10, (short) 80, GutComponentName.RECTUM, Species.RAT);
		GutSection pmd = new GutSection((short) 80, (short) 180, GutComponentName.PROXIMAL_MID_DISTAL, Species.RAT);
		GutSection caecum = new GutSection((short) 180, (short) 250, GutComponentName.CAECUM, Species.RAT);
		em.persist(anal);
		em.persist(rectum);
		em.persist(pmd);
		em.persist(caecum);

		PointMapping anus = new PointMapping(GutComponentName.ANUS, (short) 0, Species.RAT);
		PointMapping icv = new PointMapping(GutComponentName.ICV, (short) 250, Species.RAT);
		em.persist(anus);
		em.persist(icv);

		txn.commit();
		em.clear();
		em.close();
	}

}
