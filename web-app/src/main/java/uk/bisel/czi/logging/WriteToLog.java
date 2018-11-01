package uk.bisel.czi.logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;


public class WriteToLog {
	private static EntityManager em = null;
	
	public WriteToLog() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate");
		em = emf.createEntityManager();			
	}
	
	public void write(String ipAddress, String query) {		
		LogToDBMS log = new LogToDBMS((int) (System.currentTimeMillis() / 1000L), ipAddress, query);
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		em.persist(log);
		System.err.println("IN LOG:" + log.toString());
		txn.commit();
		em.close();
	}
	
}
