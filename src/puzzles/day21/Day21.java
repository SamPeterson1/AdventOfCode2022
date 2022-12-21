package puzzles.day21;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.Puzzle;
import main.Solution;

@Solution(day = 21)
public class Day21 extends Puzzle {

	private List<Monke> readMonkes(BufferedReader in, boolean part2) throws IOException {
		List<Monke> monkes = new ArrayList<Monke>();
		
		String line;
		while((line = in.readLine()) != null) {
			monkes.add(new Monke(line, part2));
		}
		
		return monkes;
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		readMonkes(in, false);
		
		Monke root = Monke.get("root");
		out.write(Long.toString(root.monke()));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		List<Monke> monkes = readMonkes(in, true);
		Monke humn = Monke.get("humn");
		
		while(!humn.isSolved()) {
			for(Monke monke : monkes) {
				monke.update();
			}
		}
		
		out.write(Long.toString(humn.getValue()));
	}

}
