package main;

public class PuzzleMaster {
	
	public static final int NUM_PUZZLES = 50;
	public static final int NUM_DAYS = 25;
	public static final int PUZZLES_PER_DAY = 2;

	public static void runPuzzle(int id) {
		PuzzleReflectionFactory.getInstance().getPuzzle(id).run();
	}
	
}
