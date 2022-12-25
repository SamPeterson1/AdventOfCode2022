package puzzles.day24;

import java.io.BufferedReader;
import java.io.IOException;

public class Player {	
	
	private Valley valley;
	private int startRow, startCol;
	private int tgtRow, tgtCol;

	public Player(BufferedReader in, int rows, int cols) throws IOException {
		this.valley = new Valley(in, rows, cols);
		
		this.startRow = 0;
		this.startCol = 1;
		this.tgtRow = valley.rows - 1;
		this.tgtCol = valley.cols - 2;
	}
	
	public int part1() {
		return getTimeToVisitFrom(startRow, startCol, tgtRow, tgtCol);
	}
	
	public int part2() {
		int time = 0;

		time += getTimeToVisitFrom(startRow, startCol, tgtRow, tgtCol);
		time += getTimeToVisitFrom(tgtRow, tgtCol, startRow, startCol);
		time += getTimeToVisitFrom(startRow, startCol, tgtRow, tgtCol);
		
		return time;
	}
	
	private int getTimeToVisitFrom(int startRow, int startCol, int tgtRow, int tgtCol) {
		Minute minute = new Minute(valley.rows, valley.cols, 0);
		minute.visit(startRow, startCol);
		
		while(!minute.isVisited(tgtRow, tgtCol)) {
			minute = nextMinute(minute);
		}
		
		return minute.getElapsedMinutes();
	}
	
	private Minute nextMinute(Minute minute) {
		int numVisited = minute.getNumVisited();
		valley.tick(false);
		
		Minute nextMinute = new Minute(valley.rows, valley.cols, minute.getElapsedMinutes() + 1);
		
		for(int i = 0; i < numVisited; i ++) {
			
			int row = minute.getVisitedRow(i);
			int col = minute.getVisitedCol(i);
						
			for(int[] direction : Valley.DIRECTIONS) {
				int nextRow = row + direction[0];
				int nextCol = col + direction[1];
				if(valley.get(nextRow, nextCol) == Valley.SAFE) {
					nextMinute.visit(nextRow, nextCol);
				}
			}
		}
		
		return nextMinute;
	}

}
