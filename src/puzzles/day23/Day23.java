package puzzles.day23;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.Puzzle;
import main.Solution;

@Solution(day = 23)
public class Day23 extends Puzzle {

	private static final int X_OFFSET = 250;
	private static final int Y_OFFSET = 250;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	
	private HashMap<Coordinate, Coordinate> proposedMoves;
	private List<Integer> priorities;
	private List<Coordinate> elves;
	
	private boolean[][] grid;

	private void readElves(BufferedReader in) throws IOException {
		elves = new ArrayList<Coordinate>();
		grid = new boolean[WIDTH][HEIGHT];
		
		String line;
		int y = 0;
		while((line = in.readLine()) != null) {
			char[] lineChars = line.toCharArray();
			for(int x = 0; x < lineChars.length; x ++) {
				if(lineChars[x] == '#') {
					elves.add(new Coordinate(x + X_OFFSET, y + Y_OFFSET));
					grid[x + X_OFFSET][y + Y_OFFSET] = true;
				}
			}
			y ++;
		}
	}
	
	private void proposeMoves() {
		proposedMoves.clear();
		
		for(Coordinate elf : elves) {
			Coordinate proposedMove = getProposedMove(elf);
			if(proposedMove != null) {
				if(proposedMoves.containsKey(proposedMove)) {
					proposedMoves.put(proposedMove, null);
				} else {
					proposedMoves.put(proposedMove, elf);
				}
			}
		}
		
		priorities.add(priorities.remove(0));
	}
	
	private boolean makeMoves() {
		boolean elfMoved = false;
		
		for(Coordinate proposedMove : proposedMoves.keySet()) {
			Coordinate proposingElf = proposedMoves.get(proposedMove);
			if(proposingElf != null) {
				elfMoved = true;
				grid[proposingElf.x][proposingElf.y] = false;
				grid[proposedMove.x][proposedMove.y] = true;
				proposingElf.set(proposedMove);
			}
		}
		
		return elfMoved;
	}
	
	private boolean isEmpty(Coordinate position) {
		return !grid[position.x][position.y];
	}
	
	private Coordinate getProposedMove(Coordinate elf) {
		Coordinate[] adjacent = new Coordinate[] {
				elf.N(), elf.NE(),
				elf.E(), elf.SE(),
				elf.S(), elf.SW(),
				elf.W(), elf.NW()
		};
		
		boolean[] isEmpty = new boolean[8];
		boolean allEmpty = true;
		
		for(int i = 0; i < 8; i ++) {
			isEmpty[i] = isEmpty(adjacent[i]);
			allEmpty &= isEmpty[i];
		}
		
		if(allEmpty) return null;
		
		for(int i = 0; i < 4; i ++) {
			int dir = priorities.get(i);
			if(isEmpty[dir] && isEmpty[(dir + 1) % 8] && isEmpty[(dir + 7) % 8]) {
				return adjacent[dir];
			}
		}
		
		return null;
	}
	
	private void setPriorities(int... priorities) {
		this.priorities = new ArrayList<Integer>();
		for(int i : priorities) {
			this.priorities.add(i);
		}
	}
	
	private int getEmptyTiles() {
		Coordinate min = new Coordinate(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Coordinate max = new Coordinate(Integer.MIN_VALUE, Integer.MIN_VALUE);
		
		for(Coordinate elf : elves) {
			min.min(elf);
			max.max(elf);
		}
		
		int area = (max.x - min.x + 1) * (max.y - min.y + 1);
		return area - elves.size();
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		readElves(in);
		
		proposedMoves = new HashMap<Coordinate, Coordinate>();
		setPriorities(0, 4, 6, 2);
		
		for(int i = 0; i < 10; i ++) {
			proposeMoves();
			makeMoves();
		}
		
		out.write(Integer.toString(getEmptyTiles()));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		readElves(in);
		
		proposedMoves = new HashMap<Coordinate, Coordinate>();
		setPriorities(0, 4, 6, 2);
		
		int rounds = 0;
		do {
			proposeMoves();
			rounds ++;
		} while(makeMoves());
		
		out.write(Integer.toString(rounds));
	}

	

}