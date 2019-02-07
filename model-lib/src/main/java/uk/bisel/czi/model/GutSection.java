package uk.bisel.czi.model;

import javax.persistence.*;

@Entity
public class GutSection {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private short startPosition;
	private short endPosition;
	@Enumerated(EnumType.STRING)
	private GutComponentName name;
	private String description;
	@Enumerated(EnumType.STRING)
	private Species species;

	public GutSection() {
		this.startPosition = 0;
		this.endPosition = 0;
		this.description = "";
	}
	
	public GutSection(short startPosition, short endPosition, GutComponentName name, Species species) {
		super();
		//Position.validatePosition(startPosition, endPosition, species);
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.name = name;
		this.species = species;
		this.description = "";
	}

	public GutSection(short startPosition, short endPosition, GutComponentName name, Species species, String description) {
		super();
		//Position.validatePosition(startPosition, endPosition, species);
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.name = name;
		this.species = species;
		this.description = description;
	}

	public float getStartPosition() {
		return startPosition;
	}

	public float getEndPosition() {
		return endPosition;
	}

	public GutComponentName getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getSpecies() { return species.toString(); }
}
