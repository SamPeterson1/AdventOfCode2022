package puzzles.day3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 3)
public class Day3 extends Puzzle {
	
	private char getDuplicatedItem(String rucksack) {
		String[] compartments = getCompartments(rucksack);
		return findCommonChars(compartments[0], compartments[1]).charAt(0);
	}
	
	private char getBadgeItem(String[] groupRucksacks) {
		String badgeCandidates = findCommonChars(groupRucksacks[0], groupRucksacks[1]);
		badgeCandidates = findCommonChars(badgeCandidates, groupRucksacks[2]);
		
		return badgeCandidates.charAt(0);
	}
	
	private String findCommonChars(String a, String b) {
		StringBuilder commonChars = new StringBuilder();
		
		for(char c1 : a.toCharArray()) {
			for(char c2 : b.toCharArray()) {
				if(c1 == c2) commonChars.append(c1);
			}
		}
		
		return commonChars.toString();
	}
	
	private String[] getCompartments(String rucksack) {
		int numItems = rucksack.length();
		int divider = numItems / 2;
		return new String[] {rucksack.substring(0, divider), rucksack.substring(divider)};
	}
	
	private int getPriority(char item) {
		int ascii = (int) item;

		if(ascii >= 97) return ascii - 96;
		else return ascii - 38;
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		String rucksack;
		int sumDuplicatedPriorities = 0;
		
		while((rucksack = in.readLine()) != null) {
			char duplicatedItem = getDuplicatedItem(rucksack);
			sumDuplicatedPriorities += getPriority(duplicatedItem);
		}
		
		out.write(Integer.toString(sumDuplicatedPriorities));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		String[] groupRucksacks = new String[3];
		int sumBadgePriorities = 0;
		
		while((groupRucksacks[0] = in.readLine()) != null) {
			groupRucksacks[1] = in.readLine();
			groupRucksacks[2] = in.readLine();
			
			char badgeItem = getBadgeItem(groupRucksacks);
			sumBadgePriorities += getPriority(badgeItem);
		}
		
		out.write(Integer.toString(sumBadgePriorities));
	}

}
