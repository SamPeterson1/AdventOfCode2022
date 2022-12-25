package puzzles.day24;

public class Minute {
	
	private boolean[][] isVisited;
		
	private int[] visitedCoords;
	private int numVisited;
	
	private int rows;
	private int cols;
	
	private int elapsedMinutes;
	
	public Minute(int rows, int cols, int elapsedMinutes) {
		this.rows = rows;
		this.cols = cols;
		this.elapsedMinutes = elapsedMinutes;
		this.visitedCoords = new int[2*rows*cols];
		this.isVisited = new boolean[rows][cols];
	}
	
	public int getElapsedMinutes() {
		return this.elapsedMinutes;
	}
	
	public int getVisitedRow(int coordIndex) {
		return this.visitedCoords[2 * coordIndex];
	}
	
	public int getVisitedCol(int coordIndex) {
		return this.visitedCoords[2 * coordIndex + 1];
	}
	
	public int getNumVisited() {
		return this.numVisited;
	}
	
	public boolean isVisited(int row, int col) {
		return isVisited[row][col];
	}
	
	public void visit(int row, int col) {
		if(row < 0 || row >= rows || col < 0 || col >= cols) return;
		
		if(!isVisited[row][col]) {
			isVisited[row][col] = true;
			visitedCoords[2 * numVisited] = row;
			visitedCoords[2 * numVisited + 1] = col;
			numVisited ++;
		}
	}
	
	public void print() {
		for(int row = 0; row < rows; row ++) {
			for(int col = 0; col < cols; col ++) {
				System.out.print(isVisited[row][col] ? '#' : '.');
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
}
