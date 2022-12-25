package puzzles.day24;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 24)
public class Day24 extends Puzzle {

	private static final int ROWS = 27;
	private static final int COLS = 122;
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		Player player = new Player(in, ROWS, COLS);
		out.write(Integer.toString(player.part1()));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		Player player = new Player(in, ROWS, COLS);
		out.write(Integer.toString(player.part2()));
	}

}
