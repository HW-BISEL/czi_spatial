package uk.bisel.czi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import uk.bisel.czi.exceptions.ComponentNotFoundException;
import uk.bisel.czi.exceptions.GutComponentAlreadyExists;

@Entity
public class Colon {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany()
	private List<GutComponent> allStructures;
	
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
