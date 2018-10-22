package uk.bisel.czi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image2PositionMapping implements Comparable<Image2PositionMapping> {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String imageId;
	private short position;
	
	public Image2PositionMapping() {}
	
	public Image2PositionMapping(String imageId, short position) {
		Position.validatePosition(position);
		this.position = position;
		this.imageId = imageId;
	}
	
	public String getImageId() {
		return imageId;
	}

	public short getPosition() {
		return position;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass().equals(this.getClass())) {
			Image2PositionMapping temp = (Image2PositionMapping) obj;
			if (temp.getImageId().equals(this.imageId) && temp.getPosition() == this.position) return true;
		}
		return false;
	}

	@Override
	public int compareTo(Image2PositionMapping obj) {		
		if(obj.position == this.position) return 0;
		if(obj.position > this.position) return -1;
		return 1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(imageId+": "+position);		
		return sb.toString();
	}
}
