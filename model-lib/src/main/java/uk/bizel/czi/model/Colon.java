package uk.bizel.czi.model;

import java.util.ArrayList;

import uk.bizel.czi.exceptions.ComponentNotFoundException;
import uk.bizel.czi.exceptions.GutComponentAlreadyExists;

public class Colon {
	
	private ArrayList<GutComponent> allStructures;
	
	public Colon() {
		allStructures = new ArrayList<>();
	}

	public void addComponent(GutComponent component) {
		if(allStructures.contains(component)) throw new GutComponentAlreadyExists(component.getName().toString());
		allStructures.add(component);
	}
	
	public GutComponent[] getAllComponents() {
		GutComponent[] temp = new GutComponent[allStructures.size()];
		return allStructures.toArray(temp);
	}
	
	public GutComponent getComponent(GutComponentName name) {
		for(GutComponent temp : allStructures) {
			if(temp.getName().equals(name)) return temp;
		}
		throw new ComponentNotFoundException(name.toString());		
	}
	
	public String whichComponentGivenPosition(short position) {
		Position.validatePosition(position);
		for(GutComponent temp : allStructures) {
			if(temp.getStartPosition() <= position && position <= temp.getEndPosition()) return temp.getName().toString();
		}
		throw new ComponentNotFoundException(position);		
	}

}
