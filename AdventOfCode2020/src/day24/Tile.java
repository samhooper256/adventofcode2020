package day24;

import java.util.Objects;

/**
 * @author Sam Hooper
 *
 */
public class Tile {
	
	private boolean color;
	
	public Tile(boolean color) {
		super();
		this.color = color;
	}

	public boolean isWhite() {
		return color;
	}
	
	public boolean isBlack() {
		return !isWhite();
	}
	
	public void setColor(boolean color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		return color == other.color;
	}

	public void flip() {
		color = !color;
	}
	
}
