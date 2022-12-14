package puzzles.day14;

import java.io.BufferedReader;
import java.io.IOException;

public class SandGrid {
	
	public static final int AIR = 0;
	public static final int WALL = 1;
	public static final int SAND = 2;
	public static final int SAND_SOURCE = 3;
	
	public static final int WIDTH = 10000;
	public static final int HEIGHT = 600;
	
	int[][] grid;
	int highestWall = 0;
	
	public SandGrid(BufferedReader in) throws IOException {
		readGrid(in);
	}
	
	public void addFloor() {
		for(int x = 0; x < WIDTH; x ++) {
			grid[highestWall + 2][x] = WALL;
		}
	}
	
	public void print(int x1, int y1, int x2, int y2) {
		for(int y = y1; y <= y2; y ++) {
			for(int x = x1; x <= x2; x ++) {
				System.out.print(getChar(x, y));
			}
			System.out.println();
		}
	}
	
	public int get(int x, int y) {
		if(x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) return WALL;
		return grid[y][x];
	}
	
	public void set(int x, int y, int val) {
		if(val == WALL && y > highestWall) highestWall = y;
		grid[y][x] = val;
	}
	
	private void readPath(String path) {
		String[] coordinatesStr = path.split(" -> ");
		
		int x1 = -1, y1 = -1;
		int x2 = -1, y2 = -1;
		
		for(int i = 0; i < coordinatesStr.length; i ++) {
			String[] xyCoordinatesStr = coordinatesStr[i].split(",");
			x2 = Integer.parseInt(xyCoordinatesStr[0]);
			y2 = Integer.parseInt(xyCoordinatesStr[1]);
			if(x1 != -1) {
				fill(x1, y1, x2, y2);
			}
			
			x1 = x2;
			y1 = y2;
		}
	}
	
	private void fill(int x1, int y1, int x2, int y2) {
		int minX = Math.min(x1, x2);
		int maxX = Math.max(x1, x2);
		int minY = Math.min(y1, y2);
		int maxY = Math.max(y1, y2);
		
		for(int x = minX; x <= maxX; x ++) {
			for(int y = minY; y <= maxY; y ++) {
				set(x, y, WALL);
			}
		}
	}
	
	private char getChar(int x, int y) {
		int gridVal = grid[y][x];
		if(gridVal == AIR) return '.';
		else if(gridVal == WALL) return '#';
		else return 'o';
	}
	
	private void readGrid(BufferedReader in) throws IOException {
		grid = new int[HEIGHT][WIDTH];
		set(500, 0, SAND_SOURCE);
		
		String line;
		while((line = in.readLine()) != null) {
			readPath(line);
		}
	}

}
