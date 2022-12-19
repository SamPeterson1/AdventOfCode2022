package puzzles.day9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.Puzzle;
import main.Solution;

@Solution(day = 9)
public class Day9 extends Puzzle {

	int[] positions;

	int numUniquePositions = 0;
	int numSegments = 0;

	List<Integer> visitedPositions;

	private void moveHead(char direction) {
		switch(direction) {
		case 'L':
			positions[0] --;
			break;
		case 'R':
			positions[0] ++;
			break;
		case 'U':
			positions[1] ++;
			break;
		case 'D':
			positions[1] --;
			break;
		}
	}

	private int getDist(int x, int y, int index) {
		int xDist = Math.abs(x - positions[2 * index]);
		int yDist = Math.abs(y - positions[2 * index + 1]);

		return xDist + yDist;
	}

	private boolean isDisconnected(int index1, int index2) {
		int xDist = Math.abs(positions[2 * index1] - positions[2 * index2]);
		int yDist = Math.abs(positions[2 * index1 + 1] - positions[2 * index2 + 1]);

		return (xDist == 2 || yDist == 2);
	}

	private void updateTail(int index) {
		int closestTailDist = Integer.MAX_VALUE;
		int bestTailX = 0;
		int bestTailY = 0;

		for(int x = -1; x <= 1; x ++) {
			for(int y = -1; y <= 1; y ++) {
				int newTailX = positions[2 * index] + x;
				int newTailY = positions[2 * index + 1] + y;

				int newTailDist = getDist(newTailX, newTailY, index - 1);
				if(newTailDist < closestTailDist) {
					closestTailDist = newTailDist;
					bestTailX = newTailX;
					bestTailY = newTailY;
				}
			}
		}

		positions[2 * index] = bestTailX;
		positions[2 * index + 1] = bestTailY;
	}

	private void readInstruction(String line) {
		String[] tokens = line.split(" ");
		char direction = tokens[0].charAt(0);
		int distance = Integer.parseInt(tokens[1]);


		logTailPosition(numSegments - 1);
		for(int i = 0; i < distance; i ++) {
			moveHead(direction);

			for(int tailIndex = 1; tailIndex < numSegments; tailIndex ++) {
				if(isDisconnected(tailIndex, tailIndex - 1))
					updateTail(tailIndex);
			}


			logTailPosition(numSegments - 1);
		}
	}

	private boolean hasVisited(int x, int y) {
		for(int i = 0; i < visitedPositions.size(); i += 2) {
			int visitedX = visitedPositions.get(i);
			int visitedY = visitedPositions.get(i + 1);

			if(x == visitedX && y == visitedY)
				return true;
		}

		return false;
	}

	private void logTailPosition(int index) {
		if(!hasVisited(positions[2 * index], positions[2 * index + 1])) {
			numUniquePositions ++;

			visitedPositions.add(positions[2 * index]);
			visitedPositions.add(positions[2 * index + 1]);
		}
	}

	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		visitedPositions = new ArrayList<Integer>();
		numUniquePositions = 0;
		numSegments = 2;

		positions = new int[numSegments * 2];

		while((line = in.readLine()) != null) {
			readInstruction(line);
		}

		out.write(Integer.toString(numUniquePositions));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		String line;
		visitedPositions = new ArrayList<Integer>();
		numUniquePositions = 0;
		numSegments = 10;

		positions = new int[numSegments * 2];

		while((line = in.readLine()) != null) {
			readInstruction(line);
		}

		out.write(Integer.toString(numUniquePositions));
	}

}
