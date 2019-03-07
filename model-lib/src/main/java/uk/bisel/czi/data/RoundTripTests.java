package uk.bisel.czi.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import uk.bisel.czi.model.Species;

public class RoundTripTests {
	private NotADao dao = new NotADao();

	public RoundTripTests() {

	}

	public static void main(String[] args) {
		RoundTripTests rtt = new RoundTripTests();
		rtt.mouseToHuman();
		rtt.humanToMouse();
		rtt.mouseToRat();
		rtt.ratToMouse();
		rtt.humanToRat();
		rtt.ratToHuman();
	}

	private void mouseToHuman() {
		try (PrintWriter out = new PrintWriter(new File("M2H.txt"))) {
			for (short i = 0; i < 141; i++) {
				short human = dao.mapping(Species.MOUSE, i, Species.HUMAN);
				short mouse2 = dao.mapping(Species.HUMAN, human, Species.MOUSE);
				if(i != mouse2) {
					out.println("mouse: " + i + " human: " + human + " mouse2: " + mouse2);
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void humanToMouse() {
		try (PrintWriter out = new PrintWriter(new File("H2M.txt"))) {
			for (short i = 0; i < 1501; i++) {
				short mouse = dao.mapping(Species.HUMAN, i, Species.MOUSE);
				short human2 = dao.mapping(Species.MOUSE, mouse, Species.HUMAN);
				if(i != human2) {
					out.println("human: " + i + " mouse: " + mouse + " human2: " + human2);
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void mouseToRat() {
		try (PrintWriter out = new PrintWriter(new File("M2R.txt"))) {
			for (short i = 0; i < 141; i++) {			
				short rat = dao.mapping(Species.MOUSE, i, Species.RAT);
				short mouse2 = dao.mapping(Species.RAT, rat, Species.MOUSE);
				if(i != mouse2) {
					out.println("mouse: " + i + " rat: " + rat + " mouse2: " + mouse2);
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void ratToMouse() {
		try (PrintWriter out = new PrintWriter(new File("R2M.txt"))) {
			for (short i = 0; i < 251; i++) {
				short mouse = dao.mapping(Species.RAT, i, Species.MOUSE);
				short rat2 = dao.mapping(Species.MOUSE, mouse, Species.RAT);
				if(i != rat2) {
					out.println("rat: " + i + " mouse: " + mouse + " rat2: " + rat2);
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	private void humanToRat() {
		try (PrintWriter out = new PrintWriter(new File("H2R.txt"))) {
			for (short i = 0; i < 1501; i++) {
				short rat = dao.mapping(Species.HUMAN, i, Species.RAT);
				short human2 = dao.mapping(Species.RAT, rat, Species.HUMAN);
				if(i != human2) {
					out.println("human: " + i + " rat: " + rat + " human2: " + human2);
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	private void ratToHuman() {
		try (PrintWriter out = new PrintWriter(new File("R2H.txt"))) {
			for (short i = 0; i < 251; i++) {				
				short human = dao.mapping(Species.RAT, i, Species.HUMAN);
				short rat2 = dao.mapping(Species.HUMAN, human, Species.RAT);
				if(i != rat2) {
					out.println("rat: " + i + " human: " + human + " rat2: " + rat2);
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}
