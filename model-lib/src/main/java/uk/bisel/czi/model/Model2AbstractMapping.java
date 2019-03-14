package uk.bisel.czi.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Model2AbstractMapping {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	@Enumerated(EnumType.STRING)
	private Species species1;
	@Enumerated(EnumType.STRING)
	private GutComponentName species1SectionName;
	private short species1StartPosition;
	private short species1StopPosition;
		
	@Enumerated(EnumType.STRING)
	private Species abstractSpecies; // why bother
	private short abstractStartPosition;
	private short abstractStopPosition;
	
    public Model2AbstractMapping() {
		this.species1StartPosition = (short) - 1;
		this.species1StopPosition = (short) - 1;
		this.species1 = null;
		this.abstractSpecies = null;
		this.abstractStartPosition = (short) -1;
		this.abstractStopPosition = (short) -1;
		this.description = "";
	}	
    
    public Model2AbstractMapping(Species species1, GutComponentName name, short species1Start, short species1Stop, short abstractStart, short abstractStop) {
    	this.species1 = species1;
    	this.species1SectionName = name;
    	this.species1StartPosition = species1Start;
		this.species1StopPosition = species1Stop;
		
		this.abstractSpecies = Species.ABSTRACT;
		this.abstractStartPosition = abstractStart;
		this.abstractStopPosition = abstractStop;
		this.description = "";
	}    
    
    public Model2AbstractMapping(Species species1, GutComponentName name, short species1Position, short abstractPosition) {
    	this.species1 = species1;
    	this.species1SectionName = name;
		this.species1StartPosition = species1Position;
		this.species1StopPosition = (short) -1;
		
		this.abstractSpecies = Species.ABSTRACT;
		this.abstractStartPosition = abstractPosition;
		this.abstractStopPosition = (short) -1;
		this.description = "";
	}	    
    
    public Model2AbstractMapping(Species species1, GutComponentName name, short species1Start, short species1Stop, short abstractStart, short abstractStop, String description) {
    	this.species1 = species1;
    	this.species1SectionName = name;
		this.species1StartPosition = species1Start;
		this.species1StopPosition = species1Stop;
		
		this.abstractSpecies = Species.ABSTRACT;
		this.abstractStartPosition = abstractStart;
		this.abstractStopPosition = abstractStop;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Species getSpecies1() {
		return species1;
	}

	public void setSpecies1(Species species1) {
		this.species1 = species1;
	}

	public GutComponentName getSpecies1SectionName() {
		return species1SectionName;
	}

	public void setSpecies1SectionName(GutComponentName name) {
		this.species1SectionName = name;
	}

	public short getSpecies1StartPosition() {
		return species1StartPosition;
	}

	public void setSpecies1StartPosition(short species1StartPosition) {
		this.species1StartPosition = species1StartPosition;
	}

	public short getSpecies1StopPosition() {
		return species1StopPosition;
	}

	public void setSpecies1StopPosition(short species1StopPosition) {
		this.species1StopPosition = species1StopPosition;
	}

	public Species getAbstractSpecies() {
		return abstractSpecies;
	}

	public void setAbstractSpecies(Species abstractSpecies) {
		this.abstractSpecies = abstractSpecies;
	}

	public short getAbstractStartPosition() {
		return abstractStartPosition;
	}

	public void setAbstractStartPosition(short abstractStartPosition) {
		this.abstractStartPosition = abstractStartPosition;
	}

	public short getAbstractStopPosition() {
		return abstractStopPosition;
	}

	public void setAbstractStopPosition(short abstractStopPosition) {
		this.abstractStopPosition = abstractStopPosition;
	}	    	

}
