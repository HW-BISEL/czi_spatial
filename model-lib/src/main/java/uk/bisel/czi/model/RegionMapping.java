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
		this.startPosition = 0;
		this.endPosition = 0;
		this.name = "";
		this.description = "";
	}
	
	public RegionMapping(short startPosition, short endPosition, String name) {
		super();
		Position.validatePosition(startPosition, endPosition);
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.name = name;
		this.description = "";
	}

	public RegionMapping(short startPosition, short endPosition, String name, String description) {
		super();
		Position.validatePosition(startPosition, endPosition);		
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
}
