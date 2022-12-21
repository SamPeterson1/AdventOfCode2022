package puzzles.day18;

import java.util.EnumMap;

public class LavaCell {
	
	private boolean[] neighbors;
	private int numNeighbors;
	
	public LavaCell() {
		this.neighbors = new boolean[6];
	}
	
	public int getSurfaceArea() {
		return 6 - numNeighbors;
	}

	public void addNeighbor(LavaCell neighbor, Side side) {
		if(!neighbors[side.id]) {
			neighbors[side.id] = true;
			numNeighbors ++;
		}
		
		if(neighbor == null) return;
		
		Side opposite = Side.getOppositeSide(side);
		if(!neighbor.neighbors[opposite.id]) {
			neighbor.neighbors[opposite.id] = true;
			neighbor.numNeighbors ++;
		}
	}
	
}
