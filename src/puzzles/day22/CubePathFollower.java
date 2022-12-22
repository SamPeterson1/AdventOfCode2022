package puzzles.day22;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import puzzles.day22.Board.Instruction;

public class CubePathFollower implements PathFollower {
	
	private class CubeFace {
		public final CubeEdge[] edges;
		public final BoardRegion region;
		public final int id;
		
		public CubeFace(int id, BoardRegion region, CubeEdge ...edges) {
			this.id = id;
			this.region = region;
			this.edges = edges;
		}
	}
	
	private static final int[][] PANELS = {
		{0 * Board.CUBE_SIZE, 1 * Board.CUBE_SIZE}, {0 * Board.CUBE_SIZE, 2 * Board.CUBE_SIZE}, 
		{1 * Board.CUBE_SIZE, 1 * Board.CUBE_SIZE}, {2 * Board.CUBE_SIZE, 0 * Board.CUBE_SIZE}, 
		{2 * Board.CUBE_SIZE, 1 * Board.CUBE_SIZE}, {3 * Board.CUBE_SIZE, 0 * Board.CUBE_SIZE}
	};
	
	private Board board;
	private BoardPosition pos;
	
	private CubeFace[] faces;
	private CubeEdge[] edges;
	
	public CubePathFollower(Board board) {
		this.board = board;
		this.pos = new BoardPosition();
		this.pos.col = board.findStartingCol();
		
		this.edges = new CubeEdge[12];
		this.faces = new CubeFace[6];

		edges[0] = getEdge(0, 0, 1, Board.RIGHT, Board.LEFT, false);
		edges[1] = getEdge(1, 0, 2, Board.DOWN, Board.UP, false);
		edges[2] = getEdge(2, 0, 3, Board.LEFT, Board.LEFT, true);
		edges[3] = getEdge(3, 0, 5, Board.UP, Board.LEFT, false);
		edges[4] = getEdge(4, 5, 1, Board.DOWN, Board.UP, false);
		edges[5] = getEdge(5, 1, 2, Board.DOWN, Board.RIGHT, false);
		edges[6] = getEdge(6, 2, 3, Board.LEFT, Board.UP, false);
		edges[7] = getEdge(7, 3, 5, Board.DOWN, Board.UP, false);
		edges[8] = getEdge(8, 4, 1, Board.RIGHT, Board.RIGHT, true);
		edges[9] = getEdge(9, 4, 2, Board.UP, Board.DOWN, false);
		edges[10] = getEdge(10, 4, 3, Board.LEFT, Board.RIGHT, false);
		edges[11] = getEdge(11, 4, 5, Board.DOWN, Board.RIGHT, false);
		
		addFace(0, 0, 1, 2, 3);
		addFace(1, 0, 4, 5, 8);
		addFace(2, 1, 5, 6, 9);
		addFace(3, 2, 6, 7, 10);
		addFace(4, 8, 9, 10, 11);
		addFace(5, 3, 4, 7, 11);
	}
	
	private BoardRegion getEdgeRegion(int panelRow, int panelCol, int direction) {
		int sz = Board.CUBE_SIZE - 1;
		
		if(direction == Board.LEFT) {
			return BoardRegion.vertical(panelCol, panelRow, panelRow + sz);
		} else if(direction == Board.RIGHT) {
			return BoardRegion.vertical(panelCol + sz, panelRow, panelRow + sz);
		} else if(direction == Board.UP) {
			return BoardRegion.horizontal(panelRow, panelCol, panelCol + sz);
		} else if(direction == Board.DOWN) {
			return BoardRegion.horizontal(panelRow + sz, panelCol, panelCol + sz);
		}
		
		return null;
	}
	
	private CubeEdge getEdge(int id, int fromPanel, int toPanel, int fromDirection, int toDirection, boolean invertIndices) {
		BoardRegion fromRegion = getEdgeRegion(PANELS[fromPanel][0], PANELS[fromPanel][1], fromDirection);
		BoardRegion toRegion = getEdgeRegion(PANELS[toPanel][0], PANELS[toPanel][1], toDirection);
	
		return new CubeEdge(fromRegion, toRegion, fromDirection, toDirection, invertIndices, id);
	}
	
	private void addFace(int index, int ...edgeIndices) {
		int sz = Board.CUBE_SIZE - 1;
		BoardRegion region = new BoardRegion(PANELS[index][0], PANELS[index][1], 
				PANELS[index][0] + sz, PANELS[index][1] + sz);
		
		CubeEdge[] edges = new CubeEdge[edgeIndices.length];
		for(int i = 0; i < edges.length; i ++) {
			edges[i] = this.edges[edgeIndices[i]];
		}
		
		this.faces[index] = new CubeFace(index, region, edges); 
	}
	
	private CubeFace getCurrentFace() {
		for(CubeFace face : faces) {
			if(pos.within(face.region)) return face;
		}
		
		return null;
	}
	
	private void move(int moveDistance, BufferedWriter out) {
		CubeFace currentFace = getCurrentFace();
				
		for(int i = 0; i < moveDistance; i ++) {
			if(!pos.within(currentFace.region)) {
				currentFace = getCurrentFace();
			}
			
			BoardPosition edgeTraversalResult = null;
			for(CubeEdge edge : currentFace.edges) {
				BoardPosition tmpEdgeTraversalResult = edge.traverse(pos);
				if(tmpEdgeTraversalResult != null) {
					edgeTraversalResult = tmpEdgeTraversalResult;
					break;
				}
			}
			
			BoardPosition newPos;
			if(edgeTraversalResult != null) {
				newPos = edgeTraversalResult;
			} else {
				newPos = new BoardPosition();
				
				newPos.facing = pos.facing;
				newPos.row = pos.row + Board.DIRECTIONS[pos.facing][0];
				newPos.col = pos.col + Board.DIRECTIONS[pos.facing][1];
			}
			
			if(board.board[newPos.row][newPos.col] == Board.OPEN) {
				if(edgeTraversalResult != null) print(out);
				pos = newPos;
				if(edgeTraversalResult != null) print(out);
			} else {
				break;
			}
			
			
		}
	}
	
	private char fromDirection(int direction) {
		if(direction == Board.LEFT) return '<';
		else if(direction == Board.RIGHT) return '>';
		else if(direction == Board.UP) return '^';
		else if(direction == Board.DOWN) return 'v';
		
		return 'X';
	}
	
	private String print(BufferedWriter out) {
		StringBuilder str = new StringBuilder();
		for(int row = 0; row < Board.HEIGHT; row ++) {
			for(int col = 0; col < Board.WIDTH; col ++) {
				char c = ' ';
				if(board.board[row][col] == Board.OPEN) c = '.';
				else if(board.board[row][col] == Board.WALL) c = '#';
				if(pos.row == row && pos.col == col) c = fromDirection(pos.facing);
				
				str.append(c);
			}
			str.append('\n');
		}
		
		str.append("\n----------------------------------------------------------------------------------------------------------------\n");
		
		try {
			out.write(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str.toString();
	}
	
	private void runPath() {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter("src/txt/day22/Debug.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Instruction instruction : board.instructions) {
			move(instruction.distance, out);
			pos.turn(instruction.turn);
		}
	}
	
	@Override
	public int getPassword() {
		runPath();
		return pos.getPassword();
	}

}
