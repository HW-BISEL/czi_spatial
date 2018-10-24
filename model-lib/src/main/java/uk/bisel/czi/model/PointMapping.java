package uk.bisel.czi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PointMapping {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private short position;

	public PointMapping() {
	}

	public PointMapping(String name, short position) {
		super();
		this.name = name;
		this.position = position;
	}
	
	public PointMapping(String name, short position, String description) {
		super();
		this.name = name;
		this.position = position;
		this.description = description;
	}	
	public String getName() {
		return name;
	}

	public short getPosition() {
		return position;
	}

	public String getDescription() {
		return description;
	}
		
}
