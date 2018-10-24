package uk.bisel.czi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RegionMapping {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private short startPosition;
	private short endPosition;
	private String name;
	private String description;

	public RegionMapping() {

	}
	
	public RegionMapping(short startPosition, short endPosition, String name) {
		super();
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.name = name;
	}

	public RegionMapping(short startPosition, short endPosition, String name, String description) {
		super();
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.name = name;
		this.description = description;
	}

	public short getStartPosition() {
		return startPosition;
	}

	public short getEndPosition() {
		return endPosition;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
