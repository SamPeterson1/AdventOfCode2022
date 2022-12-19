package puzzles.day5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import main.Puzzle;
import main.Solution;

@Solution(day = 5)
public class Day5 extends Puzzle {

	private static final int NUM_STACKS = 9;
	private static final int STACK_SPACING = 4;
	private static final int STACK_LINES_END = 7;
	private static final int INSTRUCTION_LINES_START = 10;

	private List<List<Character>> stacks;

	private void initStacks() {
		stacks = new ArrayList<>();
		for(int i = 0; i < NUM_STACKS; i ++) {
			stacks.add(new Stack<Character>());
		}
	}

	private void moveCrate9000(String instruction) {
		String[] tokens = instruction.split(" ");

		int quantity = Integer.parseInt(tokens[1]);
		int originStackIdx = Integer.parseInt(tokens[3]) - 1;
		int destStackIdx = Integer.parseInt(tokens[5]) - 1;

		for(int i = 0; i < quantity; i ++) {
			List<Character> originStack = stacks.get(originStackIdx);
			List<Character> destStack = stacks.get(destStackIdx);

			destStack.add(0, originStack.remove(0));
		}
	}

	private void moveCrate9001(String instruction) {
		String[] tokens = instruction.split(" ");

		int quantity = Integer.parseInt(tokens[1]);
		int originStackIdx = Integer.parseInt(tokens[3]) - 1;
		int destStackIdx = Integer.parseInt(tokens[5]) - 1;

		Character[] movingStack = new Character[quantity];

		for(int i = 0; i < quantity; i ++) {
			movingStack[i] = stacks.get(originStackIdx).remove(0);
		}

		for(int i = quantity - 1; i >= 0; i --) {
			stacks.get(destStackIdx).add(0, movingStack[i]);
		}
	}

	private void addToStacks(String line) {
		for(int stack = 0; stack < NUM_STACKS; stack ++) {
			char crate = line.charAt(stack * STACK_SPACING + 1);

			if(crate != ' ')
				stacks.get(stack).add(crate);
		}
	}

	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		int lineIdx = 0;
		initStacks();

		while((line = in.readLine()) != null) {
			if(lineIdx <= STACK_LINES_END) {
				addToStacks(line);
			} else if(lineIdx >= INSTRUCTION_LINES_START) {
				moveCrate9000(line);
			}
			lineIdx ++;
		}

		StringBuilder topCrates = new StringBuilder();
		for(int i = 0; i < NUM_STACKS; i ++) {
			topCrates.append(stacks.get(i).get(0));
		}

		out.write(topCrates.toString());
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		int lineIdx = 0;
		initStacks();

		while((line = in.readLine()) != null) {
			if(lineIdx <= STACK_LINES_END) {
				addToStacks(line);
			} else if(lineIdx >= INSTRUCTION_LINES_START) {
				moveCrate9001(line);
			}
			lineIdx ++;
		}

		StringBuilder topCrates = new StringBuilder();
		for(int i = 0; i < NUM_STACKS; i ++) {
			topCrates.append(stacks.get(i).get(0));
		}

		out.write(topCrates.toString());
	}

}
