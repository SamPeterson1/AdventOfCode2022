package puzzles.day22;

public class CubeEdge {
	
	public final BoardRegion fromRegion;
	public final BoardRegion toRegion;
	
	public final int fromDirection;
	public final int toDirection;
	
	public final boolean invertIndices;

	public final int id;
	
	public CubeEdge(BoardRegion fromRegion, BoardRegion toRegion, int fromDirection, int toDirection, boolean invertIndices, int id) {
		this.id = id;	
		this.fromRegion = fromRegion;
		this.toRegion = toRegion;
		this.fromDirection = fromDirection;
		this.toDirection = toDirection;
		
		this.invertIndices = invertIndices;
	}
	
	public BoardPosition traverse(BoardPosition position) {
		BoardPosition newPosition = new BoardPosition();
		
		if(position.within(fromRegion) && position.facing == fromDirection) {
			int edgeDistance = position.distanceTo(fromRegion.fromRow, fromRegion.fromCol);

			if(invertIndices) edgeDistance = Board.CUBE_SIZE - edgeDistance - 1;
			
			if(toRegion.isVertical()) {
				newPosition.row = toRegion.fromRow + edgeDistance;
				newPosition.col = toRegion.fromCol;
			} else if(toRegion.isHorizontal()) {
				newPosition.row = toRegion.fromRow;
				newPosition.col = toRegion.fromCol + edgeDistance;
			}
			
			newPosition.facing = BoardPosition.reverseDirection(toDirection);
		} else if(position.within(toRegion) && position.facing == toDirection) {
			int edgeDistance = position.distanceTo(toRegion.fromRow, toRegion.fromCol);

			if(invertIndices) edgeDistance = Board.CUBE_SIZE - edgeDistance - 1;
			
			if(fromRegion.isVertical()) {
				newPosition.row = fromRegion.fromRow + edgeDistance;
				newPosition.col = fromRegion.fromCol;
			} else if(fromRegion.isHorizontal()) {
				newPosition.row = fromRegion.fromRow;
				newPosition.col = fromRegion.fromCol + edgeDistance;
			}
			
			newPosition.facing = BoardPosition.reverseDirection(fromDirection);
		} else {
			return null;
		}
		
		return newPosition;
	}
	
}
