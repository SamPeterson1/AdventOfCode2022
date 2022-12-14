package puzzles.day14;

public class SandGrain {

	private int x, y;
	private SandGrid grid;
	
	public SandGrain(int x, int y, SandGrid grid) {
		this.x = x;
		this.y = y;
		this.grid = grid;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean wouldFallForever() {
		return (y == SandGrid.HEIGHT - 1);
	}
	
	public boolean fall() {
		if(grid.get(x, y + 1) == SandGrid.AIR) {
			updatePosition(x, y + 1);
			return true;
		} else if(grid.get(x - 1, y + 1) == SandGrid.AIR) {
			updatePosition(x - 1, y + 1);
			return true;
		} else if(grid.get(x + 1, y + 1) == SandGrid.AIR) { 
			updatePosition(x + 1, y + 1);
			return true;
		}
		
		return false;
	}
	
	private void updatePosition(int newX, int newY) {
		grid.set(this.x, this.y, SandGrid.AIR);
		grid.set(newX, newY, SandGrid.SAND);
		
		this.x = newX;
		this.y = newY;
	}

}
