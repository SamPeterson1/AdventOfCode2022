package puzzles.day2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.Puzzle;
import main.Solution;

@Solution(day = 2)
public class Day2 extends Puzzle {
	
	private final int LOSS_POINTS = 0;
	private final int DRAW_POINTS = 3;
	private final int WIN_POINTS = 6;
	
	private Map<Character, Integer> selectionPoints;
	private Map<Character, Integer> selectionStrengths;
	private Map<Character, Integer> outcomeValues;
	
	public Day2() {
		selectionPoints = new HashMap<Character, Integer>();
		selectionPoints.put('X', 1);
		selectionPoints.put('Y', 2);
		selectionPoints.put('Z', 3);
		selectionPoints.put('A', 1);
		selectionPoints.put('B', 2);
		selectionPoints.put('C', 3);
		
		selectionStrengths = new HashMap<Character, Integer>();
		selectionStrengths.put('X', 0);
		selectionStrengths.put('A', 0);
		selectionStrengths.put('Y', 2);
		selectionStrengths.put('B', 2);
		selectionStrengths.put('Z', 1);
		selectionStrengths.put('C', 1);
		
		outcomeValues = new HashMap<Character, Integer>();
		outcomeValues.put('X', LOSS_POINTS);
		outcomeValues.put('Y', DRAW_POINTS);
		outcomeValues.put('Z', WIN_POINTS);
	}

	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		int totalPoints = 0;
		
		while((line = in.readLine()) != null) {
			char selection1 = line.charAt(0);
			char selection2 = line.charAt(2);
			
			totalPoints += computePart1Points(selection1, selection2);
		}
		
		out.write(Integer.toString(totalPoints));
	}
	
	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		int totalPoints = 0;
		
		while((line = in.readLine()) != null) {
			char selection1 = line.charAt(0);
			char selection2 = line.charAt(2);
			
			totalPoints += computePart2Points(selection1, selection2);
		}
		
		
		out.write(Integer.toString(totalPoints));
	}
	
	private char findSelection(int selectionStrength) {		
	    for(Entry<Character, Integer> entry: selectionStrengths.entrySet()) {
	      if(entry.getValue() == selectionStrength) {
	        return entry.getKey();
	      }
	    }
	    
	    return ' ';
	}
	
	private int computePart2Points(char selection1, char desiredOutcome) {
		int outcomePoints = outcomeValues.get(desiredOutcome);
		return getResponsePoints(selection1, outcomePoints);
	}
	
	private int getResponsePoints(char selection1, int outcomePoints) {
		int selectionStrength = selectionStrengths.get(selection1);
		int responseStrength = 0;
		
		if(outcomePoints == DRAW_POINTS) {
			responseStrength = selectionStrength;
		} else if(outcomePoints == WIN_POINTS) {
			responseStrength = (selectionStrength + 2) % 3;
 		} else if(outcomePoints == LOSS_POINTS) {
			responseStrength = (selectionStrength + 1) % 3;
		}
		
		char responseSelection = findSelection(responseStrength);

		return outcomePoints + selectionPoints.get(responseSelection);
	}

	private int computeWinPoints(char selection1, char selection2) {
		int strength1 = selectionStrengths.get(selection1);
		int strength2 = selectionStrengths.get(selection2);
		
		if(strength1 == strength2) {
			return DRAW_POINTS;
		} else if(strength1 == (strength2 + 1) % 3) {
			return WIN_POINTS;
		} else {
			return LOSS_POINTS;
		}
	}
	
	private int computePart1Points(char selection1, char selection2) {
		int winPts = computeWinPoints(selection1, selection2);
		int selectionPts = selectionPoints.get(selection2);
		
		return winPts + selectionPts;
	}

}
