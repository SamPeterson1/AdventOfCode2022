package puzzles.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Board {

	private static final int WIDTH = 150;
	private static final int HEIGHT = 200;
	
	private static final int EMPTY = 0;
	private static final int OPEN = 1;
	private static final int WALL = 2;
	
	private static final int CW = 1;
	private static final int CCW = -1;
	private static final int NO_TURN = 0;
	
	/*
	private static final int RIGHT = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int UP = 3;
	*/
	
	private static final int[][] DIRECTIONS = new int[][] {
		{0, 1},
		{1, 0},
		{0, -1},
		{-1, 0}
	};
	
	private class Instruction {
		public final int distance;
		public final int turn;
		
		public Instruction(int distance, int turn) {
			this.distance = distance;
			this.turn = turn;
		}
	}
	
	private int[][] board;
	private ArrayList<Instruction> instructions;
	
	private int row, col;
	private int facing;
	
	public Board(BufferedReader in) throws IOException {
		parseBoard(in);
		
		in.readLine();
		
		parseInstructions(in);
		
		this.col = findStartingCol();
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
	
	private int findStartingCol() {
		for(int col = 0; col < WIDTH; col ++) {
			if(board[0][col] == OPEN) return col;
		}
		
		return -1;
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
	
	private boolean inBounds(int row, int col) {
		if(row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) return false;
		return board[row][col] != EMPTY;
	}
	
	private void move(int moveDistance) {
		int[] direction = DIRECTIONS[facing];
		
		//System.out.println("Start: " + row + " " + col);
		
		int facingEdgeRow = row, facingEdgeCol = col;
		
		while(inBounds(facingEdgeRow, facingEdgeCol)) {
			facingEdgeRow += direction[0];
			facingEdgeCol += direction[1];
		}
		
		facingEdgeRow -= direction[0];
		facingEdgeCol -= direction[1];
		
		int oppositeEdgeRow = row, oppositeEdgeCol = col;
		
		while(inBounds(oppositeEdgeRow, oppositeEdgeCol)) {
			oppositeEdgeRow -= direction[0];
			oppositeEdgeCol -= direction[1];
		}
		
		oppositeEdgeRow += direction[0];
		oppositeEdgeCol += direction[1];
		
		//System.out.println("Facing edge: " + facingEdgeRow + " " + facingEdgeCol);
		//System.out.println("Opposite edge: " + oppositeEdgeRow + " " + oppositeEdgeCol);
		
		for(int i = 0; i < moveDistance; i ++) {
			int newRow, newCol;
			if(row == facingEdgeRow && col == facingEdgeCol) {
				newRow = oppositeEdgeRow;
				newCol = oppositeEdgeCol;
			} else {
				newRow = row + direction[0];
				newCol = col + direction[1];
			}
			
			if(board[newRow][newCol] == WALL) {
				break;
			} else {
				row = newRow;
				col = newCol;
			}
		}
		
		System.out.println(row + " " + col + " " + facing);
	}
	
	private void turn(int turn) {
		facing += turn;
		facing = Math.floorMod(facing, 4);
	}
	
	private void runPath() {
		for(Instruction instruction : instructions) {
			move(instruction.distance);
			turn(instruction.turn);
		}
	}

	public int getPassword() {
		runPath();
		
		return 1000 * (row + 1) + 4 * (col + 1) + facing;
	}
	
}
