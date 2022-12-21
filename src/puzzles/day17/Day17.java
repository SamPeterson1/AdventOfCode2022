package puzzles.day17;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import main.Puzzle;
import main.Solution;

@Solution(day = 17)
public class Day17 extends Puzzle {
	

	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		Cave cave = new Cave(in);
		//while(cave.getNumRocksFallen() < 2022) {
			//cave.update();
		//}
		
		out.write(Integer.toString(cave.getHighestStableRow()));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		Cave cave = new Cave(in);
		int lastVal = 0;
		while(cave.getNumRocksFallen() < 50000) {
			cave.update();
			
			if(cave.isStable() && cave.getNumRocksFallen() % 1715 == 15) {
				System.out.println(cave.getHighestStableRow() - lastVal);
				lastVal = cave.getHighestStableRow();
			}
			
		}
		cave.print();
		//out.write(Integer.toString(cave.getHighestStableRow()));
	}

}
