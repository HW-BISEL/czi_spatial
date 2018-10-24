package uk.bisel.czi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import uk.bisel.czi.exceptions.WrongNumberOfArgumentsException;

@Entity
@Deprecated
public class GutComponent implements Comparable<GutComponent> {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private String name;
	private short startPosition;
	private short endPosition;
	
	public GutComponent() {} ;

	
	public GutComponent(GutComponentName name, short position) {
		if(name.equals(GutComponentName.APR) || name.equals(GutComponentName.ICV)) {
			if(Position.validatePosition(position)) {
				this.name = name.toString();
				this.startPosition = position;
				this.endPosition = position;
			}
		} else {
			throw new WrongNumberOfArgumentsException(name + " should provide a start and stop position");
		}
	}
	
	public GutComponent(GutComponentName name, short startPosition, short endPosition) {
		if(name.equals(GutComponentName.APR) || name.equals(GutComponentName.ICV)) {
			throw new WrongNumberOfArgumentsException(name + " should only provide 1 position (and use GutComponent(name, position)");			
		}
		Position.validatePosition(startPosition);
		Position.validatePosition(endPosition);
		Position.validatePosition(startPosition, endPosition);
		this.name = name.toString();
		this.startPosition = startPosition;
		this.endPosition = endPosition;		
	}	
	
	public String getName() {
		return name;
	}

	public short getStartPosition() {
		return startPosition;
	}

	public short getEndPosition() {
		return endPosition;
	}

	@Override
	public int compareTo(GutComponent o) {
		if(this.startPosition < o.startPosition) return -1;
		if(this.startPosition > o.startPosition) return 1;
		return 0;
	}

}
