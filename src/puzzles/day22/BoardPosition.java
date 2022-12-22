package puzzles.day22;

public class BoardPosition {

	public static final int reverseDirection(int facing) {
		return (facing + 2) % 4;
	}
	
	public int row, col;
	public int facing;

	public int getPassword() {
		return 1000 * (row + 1) + 4 * (col + 1) + facing;
	}
	
	public void turn(int turn) {
		facing += turn;
		facing = Math.floorMod(facing, 4);
	}
	
	public int distanceTo(int row, int col) {
		return Math.abs(row - this.row) + Math.abs(col - this.col);
	}
	
	public boolean within(BoardRegion region) {
		return (row >= region.fromRow && row <= region.toRow 
				&& col >= region.fromCol && col <= region.toCol);
	}
	
}
