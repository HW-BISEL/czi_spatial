package uk.bisel.czi.data;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import uk.bisel.czi.model.GutComponentName;
import uk.bisel.czi.model.GutSection;
import uk.bisel.czi.model.Image2PositionMapping;
import uk.bisel.czi.model.Model2AbstractMapping;
import uk.bisel.czi.model.PointMapping;
import uk.bisel.czi.model.Species;

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
			Image2PositionMapping temp = new Image2PositionMapping("h"+Math.round(Math.random() * 100), (short) Math.round(Math.random() * humanICV), Species.HUMAN, false);
			em.persist(temp);
		}
		for(int i = 101 ; i < 175; i++) {
			Image2PositionMapping temp = new Image2PositionMapping("m"+Math.round(Math.random() * 75),  (short) Math.round(Math.random() * mouseICV), Species.MOUSE, false);
			em.persist(temp);
		}
		for(int i = 180 ; i < 250; i++) {
			Image2PositionMapping temp = new Image2PositionMapping("r"+Math.round(Math.random() * 40),  (short) Math.round(Math.random() * ratICV), Species.RAT, false);
			em.persist(temp);
		}
		txn.commit();
		em.clear();
		em.close();
	}

    private void insertNewModel() {
    	insertNewModelHuman();
		insertNewModelMouse();
		insertNewModelRat();
		insertNewModelAbstract();
		insertModel2ModelMappings();
	}
    
    private void insertModel2ModelMappings() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();	
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		
		Model2AbstractMapping mouseAnus = new Model2AbstractMapping(Species.MOUSE, GutComponentName.ANUS, (short) 0, (short) 0);
		Model2AbstractMapping mouseAC = new Model2AbstractMapping(Species.MOUSE, GutComponentName.ANAL_CANAL, (short) 0, (short) 2, (short) 0, (short) 100);
		Model2AbstractMapping mouseR = new Model2AbstractMapping(Species.MOUSE, GutComponentName.RECTUM, (short) 2, (short) 5, (short) 100, (short) 200);
		Model2AbstractMapping mouseMD = new Model2AbstractMapping(Species.MOUSE, GutComponentName.MID_DISTAL, (short) 5, (short) 70, (short) 200, (short) 700);
		Model2AbstractMapping mouseP = new Model2AbstractMapping(Species.MOUSE, GutComponentName.PROXIMAL, (short) 70, (short) 100, (short) 700, (short) 900);
		Model2AbstractMapping mouseC = new Model2AbstractMapping(Species.MOUSE, GutComponentName.CAECUM, (short) 100, (short) 140, (short) 900, (short) 1000);
		Model2AbstractMapping mouseICV = new Model2AbstractMapping(Species.MOUSE, GutComponentName.ICV, (short) 140, (short) 1000);
		
		em.persist(mouseAnus);
		em.persist(mouseAC);
		em.persist(mouseR);
		em.persist(mouseMD);
		em.persist(mouseP);
		em.persist(mouseC);
		em.persist(mouseICV);
    	
		Model2AbstractMapping ratAnus = new Model2AbstractMapping(Species.RAT, GutComponentName.ANUS, (short) 0, (short) 0);
		Model2AbstractMapping ratAC = new Model2AbstractMapping(Species.RAT, GutComponentName.ANAL_CANAL, (short) 0, (short) 10, (short) 0, (short) 100);
		Model2AbstractMapping ratR = new Model2AbstractMapping(Species.RAT, GutComponentName.RECTUM, (short) 10, (short) 80, (short) 100, (short) 200);
		Model2AbstractMapping ratPMD = new Model2AbstractMapping(Species.RAT, GutComponentName.PROXIMAL_MID_DISTAL, (short) 80, (short) 180, (short) 200, (short) 900);
		Model2AbstractMapping ratC = new Model2AbstractMapping(Species.RAT, GutComponentName.CAECUM, (short) 180, (short) 250, (short) 900, (short) 1000);		
		Model2AbstractMapping ratICV = new Model2AbstractMapping(Species.RAT, GutComponentName.ICV, (short) 250, (short) 1000);
		
		em.persist(ratAnus);
		em.persist(ratAC);
		em.persist(ratR);
		em.persist(ratPMD);
		em.persist(ratC);
		em.persist(ratICV);		
		
		Model2AbstractMapping humanAnus = new Model2AbstractMapping(Species.HUMAN, GutComponentName.ANUS, (short) 0, (short) 0);
		Model2AbstractMapping humanAC = new Model2AbstractMapping(Species.HUMAN, GutComponentName.ANAL_CANAL, (short) 0, (short) 40, (short) 0, (short) 100);		
		Model2AbstractMapping humanR = new Model2AbstractMapping(Species.HUMAN, GutComponentName.RECTUM, (short) 40, (short) 160, (short) 100, (short) 200);
		Model2AbstractMapping humanS = new Model2AbstractMapping(Species.HUMAN, GutComponentName.SIGMOID, (short) 160, (short) 560, (short) 200, (short) 300);
		Model2AbstractMapping humanD = new Model2AbstractMapping(Species.HUMAN, GutComponentName.DESCENDING, (short) 560, (short) 810, (short) 300, (short) 500);
		Model2AbstractMapping humanT = new Model2AbstractMapping(Species.HUMAN, GutComponentName.TRANSVERSE, (short) 810, (short) 1310, (short) 500, (short) 700);
		Model2AbstractMapping humanAs = new Model2AbstractMapping(Species.HUMAN, GutComponentName.ASCENDING, (short) 1310, (short) 1470, (short) 300, (short) 500);
		Model2AbstractMapping humanC = new Model2AbstractMapping(Species.HUMAN, GutComponentName.CAECUM, (short) 1470, (short) 1500, (short) 900, (short) 1000);		
		Model2AbstractMapping humanICV = new Model2AbstractMapping(Species.HUMAN, GutComponentName.ICV, (short) 1500, (short) 1000);
		
		em.persist(humanAnus);
		em.persist(humanAC);
		em.persist(humanR);
		em.persist(humanS);
		em.persist(humanD);
		em.persist(humanT);
		em.persist(humanAs);
		em.persist(humanC);
		em.persist(humanICV);
		
		
        txn.commit();
        em.clear();
        em.close();
    }
    
    private void insertNewModelAbstract() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();	
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		
		GutSection anal = new GutSection((short) 0, (short) 100, GutComponentName.ANAL_CANAL, Species.ABSTRACT);
		GutSection rectum = new GutSection((short) 100, (short) 200, GutComponentName.RECTUM, Species.ABSTRACT);
		GutSection sigmoid = new GutSection((short) 200, (short) 300, GutComponentName.SIGMOID, Species.ABSTRACT);
		GutSection descending = new GutSection((short) 300, (short) 500, GutComponentName.DESCENDING, Species.ABSTRACT);
		GutSection transverse = new GutSection((short) 500, (short) 700, GutComponentName.TRANSVERSE, Species.ABSTRACT);
		GutSection ascending = new GutSection((short) 700, (short) 900, GutComponentName.ASCENDING, Species.ABSTRACT);
		GutSection caecum = new GutSection((short) 900, (short) 1000, GutComponentName.CAECUM, Species.ABSTRACT);
		em.persist(anal);
		em.persist(rectum);
		em.persist(sigmoid);
		em.persist(descending);
		em.persist(transverse);
		em.persist(ascending);
		em.persist(caecum);

		PointMapping anus = new PointMapping(GutComponentName.ANUS, (short) 0, Species.ABSTRACT);
		PointMapping sf = new PointMapping(GutComponentName.SPENLIC_FLEXURE, (short) 500, Species.ABSTRACT);
		PointMapping hf = new PointMapping(GutComponentName.HEPATIC_FLEXURE, (short) 700,Species.ABSTRACT);
		PointMapping icv = new PointMapping(GutComponentName.ICV, (short) 1000, Species.ABSTRACT);
		em.persist(anus);		
		em.persist(hf);
		em.persist(sf);
		em.persist(icv);
	
        txn.commit();
        em.clear();
        em.close();    	    	
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
		GutSection caecum = new GutSection((short) 1470, (short) 1500, GutComponentName.CAECUM, Species.HUMAN);
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
		GutSection md = new GutSection((short) 5, (short) 70, GutComponentName.MID_DISTAL, Species.MOUSE);
		GutSection prox = new GutSection((short) 70, (short) 100, GutComponentName.PROXIMAL, Species.MOUSE);
		GutSection caecum = new GutSection((short) 100, (short) 140, GutComponentName.CAECUM, Species.MOUSE);
		em.persist(anal);
		em.persist(rectum);
		em.persist(md);
		em.persist(prox);
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
