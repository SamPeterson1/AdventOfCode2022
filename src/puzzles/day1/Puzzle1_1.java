package puzzles.day1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;

public class Puzzle1_1 extends Puzzle {

	private static final int ID = 0;
	
	public Puzzle1_1() {
		super(ID);
	}

	@Override
	protected void compute(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		
		int maxCalories = 0;
		int currentCalories = 0;
		
		while((line = in.readLine()) != null) {
			if(line.equals("")) {
				if(currentCalories > maxCalories) {
					maxCalories = currentCalories;
					System.out.println(maxCalories);
				}

				currentCalories = 0;
			} else {
				int itemCalories = Integer.parseInt(line);
				currentCalories += itemCalories;
			}
		}
		
		out.write(Integer.toString(maxCalories));
	}
	
	

}
