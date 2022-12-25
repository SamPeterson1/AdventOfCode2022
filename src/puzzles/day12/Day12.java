package puzzles.day12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 12)
public class Day12 extends Puzzle {
	
	private static final int ROWS = 41;
	private static final int COLS = 114;

	private int[][] heights = new int[ROWS][COLS];
	private int[][] distances = new int[ROWS][COLS];
	
	private int startRow, startCol = 0;
	private int endRow, endCol = 0;
	
	private void readHeights(BufferedReader in) throws IOException {
		for(int r = 0; r < ROWS; r ++) {
			char[] chars = in.readLine().toCharArray();
			for(int c = 0; c < COLS; c ++) {
				if(chars[c] == 'S') {
					startRow = r;
					startCol = c;
				} else if(chars[c] == 'E') {
					endRow = r;
					endCol = c;
				}
				heights[r][c] = getHeight(chars[c]);
				distances[r][c] = Integer.MAX_VALUE;
			}
		}
	}
	
	private int getHeight(char c) {
		if(c == 'S') c = 'a';
		if(c == 'E') c = 'z';
		
		int ascii = (int) c;
		 return ascii - 97;
	}
	
	private boolean inBounds(int r, int c) {
		return (r >= 0 && r < ROWS && c >= 0 && c < COLS);
	}

	private boolean canClimb(int r1, int c1, int r2, int c2) {
		if(!inBounds(r1, c1) || !inBounds(r2, c2)) return false;
		return (heights[r2][c2] <= (heights[r1][c1] + 1));
	}
	
	private void setDistance(int r, int c, int distance) {
		if(!inBounds(r, c)) return;
		if(distance >= distances[r][c]) return;
		
		distances[r][c] = distance;
		
		if(canClimb(r + 1, c, r, c)) setDistance(r + 1, c, distance + 1);
		if(canClimb(r - 1, c, r, c)) setDistance(r - 1, c, distance + 1);
		if(canClimb(r, c + 1, r, c)) setDistance(r, c + 1, distance + 1);
		if(canClimb(r, c - 1, r, c)) setDistance(r, c - 1, distance + 1);
	}
	
	@Override
	public void part1(BufferedReader in, BufferedWriter out) throws IOException {
		readHeights(in);
		setDistance(endRow, endCol, 0);
		
		out.write(Integer.toString(distances[startRow][startCol]));
	}

	@Override
	public void part2(BufferedReader in, BufferedWriter out) throws IOException {
		readHeights(in);
		setDistance(endRow, endCol, 0);
		
		int minDistance = Integer.MAX_VALUE;
		for(int r = 0; r < ROWS; r ++) {
			for(int c = 0; c < COLS; c ++) {
				if(heights[r][c] == 0 && distances[r][c] < minDistance) {
					minDistance = distances[r][c];
				}
			}
		}
		
		out.write(Integer.toString(minDistance));
	}
	
}
