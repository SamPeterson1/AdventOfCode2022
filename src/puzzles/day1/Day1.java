package puzzles.day1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;;

@Solution(day = 1)
public class Day1 extends Puzzle {

	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		
		int maxCalories = 0;
		int currentCalories = 0;
		
		while((line = in.readLine()) != null) {
			if(line.equals("")) {
				if(currentCalories > maxCalories) {
					maxCalories = currentCalories;
				}

				currentCalories = 0;
			} else {
				int itemCalories = Integer.parseInt(line);
				currentCalories += itemCalories;
			}
		}
		
		out.write(Integer.toString(maxCalories));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
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
