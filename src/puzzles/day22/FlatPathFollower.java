package puzzles.day22;

import puzzles.day22.Board.Instruction;

public class FlatPathFollower implements PathFollower {

	private Board board;
	private BoardPosition pos;
	
	public FlatPathFollower(Board board) {
		this.board = board;
		this.pos = new BoardPosition();
		this.pos.col = board.findStartingCol();
	}
	
	private void move(int moveDistance) {
		int[] direction = Board.DIRECTIONS[pos.facing];
		
		int facingEdgeRow = pos.row, facingEdgeCol = pos.col;
		
		while(board.inBounds(facingEdgeRow, facingEdgeCol)) {
			facingEdgeRow += direction[0];
			facingEdgeCol += direction[1];
		}
		
		facingEdgeRow -= direction[0];
		facingEdgeCol -= direction[1];
		
		int oppositeEdgeRow = pos.row, oppositeEdgeCol = pos.col;
		
		while(board.inBounds(oppositeEdgeRow, oppositeEdgeCol)) {
			oppositeEdgeRow -= direction[0];
			oppositeEdgeCol -= direction[1];
		}
		
		oppositeEdgeRow += direction[0];
		oppositeEdgeCol += direction[1];

		for(int i = 0; i < moveDistance; i ++) {
			int newRow, newCol;
			if(pos.row == facingEdgeRow && pos.col == facingEdgeCol) {
				newRow = oppositeEdgeRow;
				newCol = oppositeEdgeCol;
			} else {
				newRow = pos.row + direction[0];
				newCol = pos.col + direction[1];
			}
			
			if(board.board[newRow][newCol] == Board.WALL) {
				break;
			} else {
				pos.row = newRow;
				pos.col = newCol;
			}
		}		
	}
	
	private void runPath() {
		for(Instruction instruction : board.instructions) {
			move(instruction.distance);
			pos.turn(instruction.turn);
		}
	}

	@Override
	public int getPassword() {
		runPath();
		return pos.getPassword();
	}
	
}
