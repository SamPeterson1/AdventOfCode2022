package puzzles.day6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 6)
public class Day6 extends Puzzle {

	private boolean isUnique(String str) {
		for(char c : str.toCharArray()) {
			if(str.lastIndexOf(c) != str.indexOf(c)) return false;
		}
		
		return true;
	}
	
	private int getPacketStart(String data) {
		for(int i = 0; i < data.length() - 4; i ++) {
			String recentFour = data.substring(i, i + 4);
			if(isUnique(recentFour)) return i;
		}
		
		return -1;
	}
	
	private int getMessageStart(String data) {
		for(int i = 0; i < data.length() - 14; i ++) {
			String recentFour = data.substring(i, i + 14);
			if(isUnique(recentFour)) return i;
		}
		
		return -1;
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		String line = in.readLine();
		int packetStart = getPacketStart(line);

		out.write(Integer.toString(packetStart + 4));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		String line = in.readLine();
		int messageStart = getMessageStart(line);

		out.write(Integer.toString(messageStart + 14));
	}

}
