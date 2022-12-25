package puzzles.day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Valley {

	public static final int WALL = -1;
	public static final int SAFE = 0;
	public static final int BLIZZARD = 1;
	
	public static final int[][] DIRECTIONS = new int[][] {
		{0, 1},  //right
		{1, 0},  //down
		{0, -1}, //left
		{-1, 0},  //up
		{0, 0}   //wait
	};
	
	public final int rows;
	public final int cols;
	
	private int[][] grid;
	
	private Blizzard[] blizzards;
	
	public Valley(BufferedReader in, int rows, int cols) throws IOException {
		this.rows = rows;
		this.cols = cols;
		
		this.grid = new int[rows][cols];
		
		List<Blizzard> blizzardList = new ArrayList<Blizzard>();
		String line;
		
		int row = 0;
		
		while((line = in.readLine()) != null) {
			char[] lineChars = line.toCharArray();
			
			for(int col = 0; col < cols; col ++) {
				char c = lineChars[col];
				this.grid[row][col] = parseChar(c);
				
				if(this.grid[row][col] == BLIZZARD) {
					blizzardList.add(new Blizzard(this, row, col, parseDirection(c)));
				}
			}
			
			row ++;
		}
		
		this.blizzards = new Blizzard[blizzardList.size()];
		for(int i = 0; i < blizzards.length; i ++) {
			this.blizzards[i] = blizzardList.get(i);
		}
	}
	
	public void print(int playerRow, int playerCol) {
		for(int row = 0; row < rows; row ++) {
			System.out.println();
			for(int col = 0; col < cols; col ++) {
				int val = grid[row][col];
				if(row == playerRow && col == playerCol) System.out.print('X');
				else if(val == SAFE) System.out.print('.');
				else if(val == WALL) System.out.print('#');
				else System.out.print(grid[row][col]);
			}
		}
		
		System.out.println();
	}
	
	public void tick(boolean reverse) {
		for(Blizzard blizzard : blizzards)
			blizzard.tick(reverse);
	}
	
	private int[] parseDirection(char direction) {
		if(direction == '>') return DIRECTIONS[0];
		else if(direction == 'v') return DIRECTIONS[1];
		else if(direction == '<') return DIRECTIONS[2];
		else if(direction == '^') return DIRECTIONS[3];
		
		return null;
	}

	private int parseChar(char c) {
		if(c == '.') return SAFE;
		else if(c == '#') return WALL;
		else return BLIZZARD;
	}
	
	public int get(int row, int col) {
		if(row < 0 || row >= rows || col < 0 || col >= cols) return WALL;
		return this.grid[row][col];
	}
	
	public void addBlizzard(int row, int col) {
		this.grid[row][col] ++;
	}
	
	public void removeBlizzard(int row, int col) {
		this.grid[row][col] --;
	}
	
}
