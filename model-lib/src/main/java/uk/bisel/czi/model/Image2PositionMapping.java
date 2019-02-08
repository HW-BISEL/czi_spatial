package uk.bisel.czi.model;

import javax.persistence.*;

@Entity
public class Image2PositionMapping implements Comparable<Image2PositionMapping> {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String imageId;
	private short position;
	@Enumerated(EnumType.STRING)
	private Species species;
	
	public Image2PositionMapping() {}
	
	public Image2PositionMapping(String imageId, short position, Species species) {
		Position.validatePosition(position, species);
		this.position = position;
		this.imageId = imageId;
		this.species = species;
	}
	
	public String getImageId() {
		return imageId;
	}

	public short getPosition() {
		return position;
	}

	public Species getSpecies() { return species;}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass().equals(this.getClass())) {
			Image2PositionMapping temp = (Image2PositionMapping) obj;
			if (temp.getSpecies().equals(this.getSpecies()) && temp.getImageId().equals(this.imageId) && temp.getPosition() == this.position) return true;
		}
		return false;
	}

	@Override
	public int compareTo(Image2PositionMapping obj) {
		if(obj.getSpecies().toString().equalsIgnoreCase(this.getSpecies().toString())) {
			if (obj.position == this.position) return 0;
			if (obj.position > this.position) return -1;
			return 1;
		}
		return this.getSpecies().compareTo(obj.getSpecies());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(imageId+": "+position);		
		return sb.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public void setPosition(short position) {
		this.position = position;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}
}
