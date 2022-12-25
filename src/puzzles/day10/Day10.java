package puzzles.day10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import main.Puzzle;
import main.Solution;

@Solution(day = 10)
public class Day10 extends Puzzle {

	private int[] registers;
	private int cycles;
	private HashMap<String, Instruction> instructions;
	
	private class Noop extends Instruction {

		public Noop() {
			super("noop", 1);
		}

		@Override
		public void cycle(String[] args, int[] registers, int cycle) {
			
		}
		
	}

	private class Addx extends Instruction {

		public Addx() {
			super("addx", 2);
		}

		@Override
		public void cycle(String[] args, int[] registers, int cycle) {
			if(cycle == 1) registers[0] += Integer.parseInt(args[1]);
		}

	}
	
	public Day10() {
		instructions = new HashMap<String, Instruction>();
		
		Instruction i = new Noop();
		instructions.put(i.getName(), i);
		i = new Addx();
		instructions.put(i.getName(), i);
		
	}
	
	@Override
	public void part1(BufferedReader in, BufferedWriter out) throws IOException {
		registers = new int[1];
		registers[0] = 1;
		cycles = 1;
		int sumSignalStrength = 0;
		String line;
		
		while((line = in.readLine()) != null) {
			String[] args = line.split(" ");
			Instruction i = instructions.get(args[0]);

			while(true) {
				boolean finished = i.cycle(args, registers);
				
				cycles ++;
				if((cycles - 20) % 40 == 0) {
					sumSignalStrength += registers[0] * cycles;
				}
				
				if(finished) break;
			}
		}
		
		out.write(Integer.toString(sumSignalStrength));
	}

	@Override
	public void part2(BufferedReader in, BufferedWriter out) throws IOException {
		registers = new int[1];
		registers[0] = 1;
		cycles = 1;
		
		String line;
		boolean[][] screen = new boolean[6][40];
		
		while((line = in.readLine()) != null) {
			String[] args = line.split(" ");
			Instruction i = instructions.get(args[0]);

			while(true) {
				int screenCol = (cycles - 1) % 40;
				int screenRow = (cycles - 1) / 40;
								
				for(int j = registers[0] - 1; j <= registers[0] + 1; j ++) {
					if(screenCol == j) screen[screenRow][screenCol] = true;
				}
				
				boolean finished = i.cycle(args, registers);
				
				cycles ++;
				if(finished) break;
			}
		}
		
		for(int row = 0; row < 6; row ++) {
			StringBuilder sb = new StringBuilder();
			for(int col = 0; col < 40; col ++) {
				sb.append(screen[row][col] ? "#" : ".");
			}
			sb.append("\n");
			out.write(sb.toString());
		}
	}
	
}
