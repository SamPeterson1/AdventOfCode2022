package puzzles.day11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 11)
public class Day11 extends Puzzle {

	private void monkeAround(BufferedReader in, BufferedWriter out, int rounds, boolean divideWorry) throws IOException {
		while(true) {
			new Monke(in, divideWorry);
			if(in.readLine() == null) break;
		}
		
		for(int i = 0; i < rounds; i ++) {
			for(Monke monke : Monke.monkes) {
				monke.takeTurn();
			}
		}
		

		for(Monke monke : Monke.monkes) {
			out.write(Integer.toString(monke.getBusiness()) + "\n");
		}
	}
	
	@Override
	public void part1(BufferedReader in, BufferedWriter out) throws IOException {
		monkeAround(in, out, 20, true);
	}
	
	@Override
	public void part2(BufferedReader in, BufferedWriter out) throws IOException {
		monkeAround(in, out, 10000, false);
	}
	
}
