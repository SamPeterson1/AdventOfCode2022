package puzzles.day8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 8)
public class Day8 extends Puzzle {

	private static final int SIZE = 99;

	private int[] trees;

	private void readTrees(BufferedReader in) throws IOException {
		trees = new int[SIZE*SIZE];

		for(int r = 0; r < SIZE; r ++) {
			readTreeRow(in.readLine(), r);
		}
	}

	private int charToInt(char c) {
		return c - 48;
	}

	private void readTreeRow(String row, int r) {
		char[] heightChars = row.toCharArray();
		for(int c = 0; c < SIZE; c ++) {
			int height = charToInt(heightChars[c]);
			trees[r * SIZE + c] = height;
		}
	}

	private boolean isVisible(int tree) {
		int height = trees[tree];
		boolean visible = false;

		boolean visibleThisLine = true;
		for(int i = tree + 1; i % SIZE != 0; i ++) {
			if(trees[i] >= height) visibleThisLine = false;
		}
		if(visibleThisLine) visible = true;

		visibleThisLine = true;
		for(int i = tree - 1; i % SIZE != SIZE - 1 && i >= 0; i --) {
			if(trees[i] >= height) visibleThisLine = false;
		}
		if(visibleThisLine) visible = true;

		visibleThisLine = true;
		for(int i = tree + SIZE; i < SIZE*SIZE; i += SIZE) {
			if(trees[i] >= height) visibleThisLine = false;
		}
		if(visibleThisLine) visible = true;

		visibleThisLine = true;
		for(int i = tree - SIZE; i >= 0; i -= SIZE) {
			if(trees[i] >= height) visibleThisLine = false;
		}
		if(visibleThisLine) visible = true;

		return visible;
	}

	private int getScenicScore(int tree) {
		int height = trees[tree];
		int score = 1;

		int lineScore = 0;
		for(int i = tree + 1; i % SIZE != 0; i ++) {
			lineScore ++;
			if(trees[i] >= height) break;
		}
		score *= lineScore;

		lineScore = 0;
		for(int i = tree - 1; i % SIZE != SIZE - 1 && i >= 0; i --) {
			lineScore ++;
			if(trees[i] >= height) break;
		}
		score *= lineScore;

		lineScore = 0;
		for(int i = tree + SIZE; i < SIZE*SIZE; i += SIZE) {
			lineScore ++;
			if(trees[i] >= height) break;
		}
		score *= lineScore;

		lineScore = 0;
		for(int i = tree - SIZE; i >= 0; i -= SIZE) {
			lineScore ++;
			if(trees[i] >= height) break;
		}
		score *= lineScore;

		return score;
	}

	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		readTrees(in);

		int numVisible = 0;
		for(int i = 0; i < SIZE * SIZE; i ++) {
			if(isVisible(i)) numVisible ++;
		}

		out.write(Integer.toString(numVisible));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		readTrees(in);

		int bestScenicScore = 0;
		for(int i = 0; i < SIZE * SIZE; i ++) {
			int scenicScore = getScenicScore(i);
			if(scenicScore > bestScenicScore) {
				bestScenicScore = scenicScore;
			}
		}

		out.write(Integer.toString(bestScenicScore));
	}

}
