package puzzles.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Board {

	public static final int WIDTH = 150;
	public static final int HEIGHT = 200;
	
	public static final int CUBE_SIZE = 50;
	
	public static final int EMPTY = 0;
	public static final int OPEN = 1;
	public static final int WALL = 2;
	
	public static final int CW = 1;
	public static final int CCW = -1;
	public static final int NO_TURN = 0;
	
	public static final int RIGHT = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int UP = 3;
	
	public static final int[][] DIRECTIONS = new int[][] {
		{0, 1},
		{1, 0},
		{0, -1},
		{-1, 0}
	};

	public class Instruction {
		public final int distance;
		public final int turn;
		
		public Instruction(int distance, int turn) {
			this.distance = distance;
			this.turn = turn;
		}
	}
	
	public int[][] board;
	public ArrayList<Instruction> instructions;
	
	
	
	public Board(BufferedReader in) throws IOException {
		parseBoard(in);
		
		in.readLine();
		
		parseInstructions(in);
	}
	
	private void parseBoard(BufferedReader in) throws IOException {
		this.board = new int[HEIGHT][WIDTH];
		
		for(int row = 0; row < HEIGHT; row ++) {
			char[] line = in.readLine().toCharArray();
			for(int col = 0; col < line.length; col ++) {
				board[row][col] = parseBoardChar(line[col]);
			}
		}
	}
	
	private void parseInstructions(BufferedReader in) throws IOException {
		this.instructions = new ArrayList<Instruction>();
		char[] line = in.readLine().toCharArray();
		StringBuilder currentNum = new StringBuilder();
		
		for(char c : line) {
			if(c == 'R' || c == 'L') { 
				int distance = Integer.parseInt(currentNum.toString());
				int turn = parseTurnChar(c);
				
				instructions.add(new Instruction(distance, turn));
				currentNum = new StringBuilder();
			} else {
				currentNum.append(c);
			}
		}
		
		int distance = Integer.parseInt(currentNum.toString());
		instructions.add(new Instruction(distance, NO_TURN));
	}

	private int parseTurnChar(char c) {
		if(c == 'R') return CW;
		else if(c == 'L') return CCW;
		
		return NO_TURN;
	}
	
	private int parseBoardChar(char c) {
		if(c == ' ') return EMPTY;
		else if(c == '.') return OPEN;
		else if(c == '#') return WALL;
		
		return -1;
	}
	
	public int findStartingCol() {
		for(int col = 0; col < WIDTH; col ++) {
			if(board[0][col] == OPEN) return col;
		}
		
		return -1;
	}
	
	public boolean inBounds(int row, int col) {
		if(row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) return false;
		return board[row][col] != EMPTY;
	}
	

}
