package puzzles.day22;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 22)
public class Day22 extends Puzzle {

	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		Board board = new Board(in);
		System.out.println(board.getPassword());
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		
	}

}
