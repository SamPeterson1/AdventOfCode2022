package puzzles.day1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;

public class Puzzle1_2 extends Puzzle {

	private static final int ID = 1;
	
	public Puzzle1_2() {
		super(ID);
	}

	@Override
	protected void compute(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		
		int currentCalories = 0;
		int[] top3 = new int[3];
		
		while((line = in.readLine()) != null) {
			if(line.equals("")) {
				for(int i = 0; i < 3; i ++) {
					if(currentCalories > top3[i]) {
						for(int j = i; j > 0; j --) {
							top3[j] = top3[j-1];
						}
						
						top3[i] = currentCalories;
						break;
					}
				}
				
				currentCalories = 0;
			} else {
				int itemCalories = Integer.parseInt(line);
				currentCalories += itemCalories;
			}
		}
		
		
		
		int top3Sum = 0;
		for(int i = 0; i < 3; i ++)
			top3Sum += top3[i];
		
		out.write(Integer.toString(top3Sum));
	}
	
	

}
