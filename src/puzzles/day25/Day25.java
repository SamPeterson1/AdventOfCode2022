package puzzles.day25;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 25)
public class Day25 extends Puzzle {

	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		long sum = 0;
		while((line = in.readLine()) != null) {
			//System.out.println(sum);
			sum += SNAFU.toLong(line);
		}
		
		out.write(SNAFU.toString(sum));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
