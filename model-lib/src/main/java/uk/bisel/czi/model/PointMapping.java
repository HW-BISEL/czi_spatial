package uk.bisel.czi.model;

import javax.persistence.*;

@Entity
public class PointMapping {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private GutComponentName name;
	private String description;
	private short position;
	@Enumerated(EnumType.STRING)
	private Species species;

	public PointMapping() {
		this.description = "";
	    this.position = 0;
	}

	public PointMapping(GutComponentName name, short position, Species species) {
		super();
		this.name = name;
		this.position = position;
		this.description = "";
		this.species = species;
	}
	
	public PointMapping(GutComponentName name, short position, Species species, String description) {
		super();
		this.name = name;
		this.position = position;
		this.species = species;
		this.description = description;
	}

	public String getName() {
		return name.toString();
	}

	public short getPosition() {
		return position;
	}

	public String getDescription() {
		return description;
	}

	public String getSpecies() { return species.toString();}
		
}
