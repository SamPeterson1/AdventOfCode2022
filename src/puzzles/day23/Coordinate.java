package puzzles.day23;

import java.util.Objects;

public class Coordinate {
	public int x, y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		return x == other.x && y == other.y;
	}
	
	public void max(Coordinate other) {
		this.x = Math.max(x, other.x);
		this.y = Math.max(y, other.y);
	}
	
	public void min(Coordinate other) {
		this.x = Math.min(x, other.x);
		this.y = Math.min(y, other.y);
	}
	
	public void set(Coordinate other) {
		this.x = other.x;
		this.y = other.y;
	}

	public Coordinate N() { return new Coordinate(x, y - 1); }
	public Coordinate NE() { return new Coordinate(x + 1, y - 1); }
	public Coordinate E() { return new Coordinate(x + 1, y); }
	public Coordinate SE() { return new Coordinate(x + 1, y + 1); }
	public Coordinate S() { return new Coordinate(x, y + 1); }
	public Coordinate SW() { return new Coordinate(x - 1, y + 1); }
	public Coordinate W() { return new Coordinate(x - 1, y); }
	public Coordinate NW() { return new Coordinate(x - 1, y - 1); }
}