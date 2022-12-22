package puzzles.day22;

public class BoardRegion {

	public static final BoardRegion horizontal(int row, int from, int to) {
		return new BoardRegion(row, from, row, to);
	}
	
	public static final BoardRegion vertical(int col, int from, int to) {
		return new BoardRegion(from, col, to, col);
	}
	
	public final int fromRow, fromCol;
	public final int toRow, toCol;
	
	public BoardRegion(int fromRow, int fromCol, int toRow, int toCol) {
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
	}
	
	public boolean isHorizontal() {
		return (fromRow == toRow);
	}
	
	public boolean isVertical() {
		return (fromCol == toCol);
	}
	
}
