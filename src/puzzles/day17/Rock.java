package puzzles.day17;

public class Rock {

	private Row[] rows;
	
	public Rock(Row ...rows) {
		this.rows = rows;
	}

	public void copyTo(Cave cave, int bottomY) {
		for(int y = 0; y < rows.length; y ++) {
			rows[y].copyTo(cave.getRow(bottomY + y), 2);
		}
	}
}
