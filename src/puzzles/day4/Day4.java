package puzzles.day4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 4)
public class Day4 extends Puzzle {

	private int[] parseAssignments(String str) {
		String[] range = str.split("-");
		int lower = Integer.parseInt(range[0]);
		int upper = Integer.parseInt(range[1]);
				
		return new int[] {lower, upper};
	}
	
	//returns if range1 contains range2
	private boolean contains(int[] range1, int[] range2) {
		return (range2[0] >= range1[0] && range2[1] <= range1[1]);
	}
	
	private boolean hasOverlap(int[] range1, int[] range2) {
		return (range1[0] <= range2[1] && range1[1] >= range2[0]);
	}
	
	private boolean hasOverlappingRange(String str1, String str2) {
		int[] range1 = parseAssignments(str1);
		int[] range2 = parseAssignments(str2);
		
		return hasOverlap(range1, range2);
	}
	
	private boolean hasContainedRange(String str1, String str2) {
		int[] range1 = parseAssignments(str1);
		int[] range2 = parseAssignments(str2);
		
		return contains(range1, range2) || contains(range2, range1);
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		String pair;
		int numContained = 0;
		
		while((pair = in.readLine()) != null) {
			String[] assignments = pair.split(",");
			
			if(hasContainedRange(assignments[0], assignments[1]))
				numContained ++;
		}
		
		out.write(Integer.toString(numContained));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		String pair;
		int numOverlapped = 0;
		
		while((pair = in.readLine()) != null) {
			String[] assignments = pair.split(",");
			
			if(hasOverlappingRange(assignments[0], assignments[1]))
				numOverlapped ++;
		}
		
		out.write(Integer.toString(numOverlapped));	
	}

}
