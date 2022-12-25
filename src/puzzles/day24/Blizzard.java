package puzzles.day24;

public class Blizzard {

	private Valley valley;
	
	public int row, col;
	public int[] direction;
	
	public Blizzard(Valley valley, int row, int col, int direction[]) {
		this.valley = valley;
		this.row = row;
		this.col = col;
		this.direction = direction;
	}
	
	public int wrap(int pos, int wrapLength) {
		if(pos == wrapLength - 1) return 1;
		else if(pos == 0) return wrapLength - 2;
		
		return pos;
	}
	
	public void tick(boolean reverse) {	
		int sign = reverse ? -1 : 1;
		valley.removeBlizzard(this.row, this.col);
		
		this.row = wrap(this.row + sign * direction[0], valley.rows);
		this.col = wrap(this.col + sign * direction[1], valley.cols);
				
		valley.addBlizzard(this.row, this.col);
	}
	
}
