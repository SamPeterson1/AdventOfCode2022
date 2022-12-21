package puzzles.day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Cave {

	public static final int WIDTH = 7;
	public static final int FALLING_ROCK = 2;
	public static final int STABLE_ROCK = 1;
	public static final int AIR = 0;
	
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	
	private static Rock[] rocks = initRocks();
	private int numRocksFallen;
	private int floor = 0;
	
	@SuppressWarnings("unused")
	private int cycleHash = Integer.MIN_VALUE;
	private HashMap<Integer, Integer> states;
	
	private static Rock[] initRocks() {
		Rock[] rocks = new Rock[5];
		
		rocks[0] = new Rock(new Row("@@@@"));
		rocks[1] = new Rock(new Row(".@."), new Row("@@@"), new Row(".@."));
		rocks[2] = new Rock(new Row("@@@"), new Row("..@"), new Row("..@"));
		rocks[3] = new Rock(new Row("@"), new Row("@"), new Row("@"), new Row("@"));
		rocks[4] = new Rock(new Row("@@"), new Row("@@"));
		
		return rocks;
	}
	
	private HashMap<Integer, Row> rows;
	private int[] jets;
	
	private int jetIndex;
	private int rockIndex;
	private int highestStableRow;
	
	public Cave(BufferedReader in) throws IOException {
		this.rows = new HashMap<Integer, Row>();
		readJets(in);
		this.states = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < 10; i ++) getRow(i);
	}
	
	private void readJets(BufferedReader in) throws IOException {
		String line = in.readLine();
		jets = new int[line.length()];
		
		for(int i = 0; i < jets.length; i ++) {
			jets[i] = line.charAt(i) == '<' ? LEFT : RIGHT;
		}
	}
	
	public void print() {
		for(int i = highestStableRow + 5; i >= highestStableRow - 10; i --) {
			getRow(i).print();
		}
		System.out.println();
	}
	
	public void nextRock() {
		rocks[rockIndex ++].copyTo(this, highestStableRow + 3);
		rockIndex %= rocks.length;
	}
	
	public boolean isStable() {
		for(int y = highestStableRow; rows.containsKey(y); y ++) {
			if(!getRow(y).isStable(true)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean canFall() {
		for(int y = highestStableRow; rows.containsKey(y); y ++) {
			if(y != floor && !getRow(y).canFallTowards(getRow(y - 1))) {
				return false;
			}
		}
		
		return true;
	}
	
	private void pushRock() {
		int direction = jets[jetIndex ++];
		jetIndex %= jets.length;
		
		boolean canPushRock = true;
		for(int y = highestStableRow; rows.containsKey(y); y ++) {
			if(!getRow(y).canPushRock(direction)) {
				canPushRock = false;
			}
		}
			
		if(canPushRock) {
			for(int y = highestStableRow; rows.containsKey(y); y ++) {
				getRow(y).pushRock(direction);
			}
		}
	}
	
	private void fall() {
		boolean canFall = canFall();
		
		for(int y = highestStableRow; rows.containsKey(y); y ++) {
			Row row = getRow(y);
			
			if(y == floor) {
				row.stabilize();
			} else {
				Row nextRow = rows.get(y - 1);
				if(canFall) {
					row.fallTowards(nextRow);
					if(y - 1 <= highestStableRow) highestStableRow = Math.max(floor, y - 2);
				} else {
					row.stabilize();	
					if(row.isFull()) {
						for(int i = row.getY() - 1; i >= 0; i --) {
							rows.remove(i);
						}
						floor = row.getY();
						highestStableRow = row.getY();
					}
				}
			}			
		}
	}
	
	public void update() {
		if(isStable()) {
			//if(hashState()) System.out.println("CUYCLEE " + numRocksFallen);
			nextRock();
		}
		pushRock();
		fall();
		
		while(getRow(highestStableRow).isStable(false)) {
			highestStableRow ++;
		}
		
		if(isStable()) numRocksFallen ++;
	}
	
	@SuppressWarnings("unused")
	private boolean hashState() {
		byte[] rowHashes = new byte[5];
		if(highestStableRow > 5) {
			for(int i = 0; i < 5; i ++) {
				rowHashes[i] = getRow(highestStableRow - i).getHash();
			}
		}
		
		Integer rock = rockIndex;
		Integer jet = jetIndex;
		
		int hash = Objects.hash(Integer.valueOf(Arrays.hashCode(rowHashes)), rock, jet);

		if(hash == 2097596146) System.out.println("foo " + numRocksFallen);
		
		if(states.containsKey(hash)) {
			//System.out.println(hash + " " + states.get(hash));
			//return true;
		}
		else states.put(hash, this.numRocksFallen);
		
		
		return false;
	}
	
	public int getNumRocksFallen() {
		return this.numRocksFallen;
	}
	
	public int getHighestStableRow() {
		return this.highestStableRow;
	}
	
	public Row getRow(int y) {
		if(rows.containsKey(y)) {
			return rows.get(y);
		} else {
			rows.put(y, new Row(y));
			return rows.get(y);
		}
	}
	
}
