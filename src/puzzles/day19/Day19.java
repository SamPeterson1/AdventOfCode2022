package puzzles.day19;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 19)
public class Day19 extends Puzzle {

	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		MiningSimulation sim = new MiningSimulation(in);
		out.write(Integer.toString(sim.getQualitySum()));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		MiningSimulation sim = new MiningSimulation(in);
		out.write(Integer.toString(sim.getGeodeProduct()));
	}

}
