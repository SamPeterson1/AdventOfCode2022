package puzzles.day14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 14)
public class Day14 extends Puzzle {
	
	SandGrid grid;
	SandGrain fallingGrain;
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		grid = new SandGrid(in);
		int numGrains = 0;
		
		do {
			fallingGrain = new SandGrain(500, 0, grid);
			numGrains ++;
			while(fallingGrain.fall());
		} while(!fallingGrain.wouldFallForever());
		
		out.write(Integer.toString(numGrains - 1));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		grid = new SandGrid(in);
		grid.addFloor();
		
		int numGrains = 0;
		
		do {
			fallingGrain = new SandGrain(500, 0, grid);
			numGrains ++;
			while(fallingGrain.fall());
		} while(!(fallingGrain.getX() == 500 && fallingGrain.getY() == 0));
				
		out.write(Integer.toString(numGrains));
	}

}
