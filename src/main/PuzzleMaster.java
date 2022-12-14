package main;

import java.io.IOException;

public class PuzzleMaster {
	
	public static final int NUM_PUZZLES = 50;
	public static final int NUM_DAYS = 25;
	public static final int PUZZLES_PER_DAY = 2;

	public static void runAll() {
		int latestDay = PuzzleReflectionFactory.getInstance().getLatestDay();
		for(int day = 1; day <= latestDay; day ++) {
			runPuzzle(day);
		}
	}
	
	public static void runPuzzle() {
		try {
			PuzzleReflectionFactory.getInstance().getLatestPuzzle().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void runPuzzle(int id) {
		Puzzle puzzle = PuzzleReflectionFactory.getInstance().getPuzzle(id);
		if(puzzle == null) return;
		
		try {
			puzzle.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
