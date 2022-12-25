package puzzles.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Monke {

	public static Monke[] monkes = new Monke[8];
	private static long LCM = 1;
	
	private static final int OLD = -1;
	
	private List<Long> items;
	
	private int id;
	private long arg1 = OLD;
	private long arg2 = OLD;
	
	private long divisibleBy = 0;
	
	private boolean add = false;
	
	private boolean divideWorry;
	
	private int business;
	private int trueMonke;
	private int falseMonke;
	
	public Monke(BufferedReader in) {
		this(in, true);
	}
	
	public Monke(BufferedReader in, boolean divideWorry) {
		this.divideWorry = divideWorry;
		try {
			parseBehavior(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print() {
		System.out.println("id: " + id);
		
		System.out.print("Starting items: ");
		for(long item : items) System.out.print(item + ", ");
		System.out.println();
		
		System.out.println("Arg1: " + arg1 + ", Arg2: " + arg2 + " Add: " + add);
		System.out.println("True: " + trueMonke + " False: " + falseMonke);
	}
	
	private long getArg1(long old) {
		return (arg1 == OLD) ? old : arg1;
	}
	
	private long getArg2(long old) {
		return (arg2 == OLD) ? old : arg2;
	}
	
	private long applyOperation(long old) {
		if(add) {
			return getArg1(old) + getArg2(old);
		} else {
			return getArg1(old) * getArg2(old);
		}
	}
	
	public int getBusiness() {
		return this.business;
	}
	
	public void takeTurn() {
		for(long item : items) {
			business ++;
			long newItem = applyOperation(item);
			if(divideWorry) newItem /= 3;
			newItem = newItem % LCM;
			
			if(newItem % divisibleBy == 0) {
				monkes[trueMonke].addItem(newItem);
			} else {
				monkes[falseMonke].addItem(newItem);
			}
		}
		
		items.clear();
	}
	
	public void addItem(long item) {
		items.add(item);
	}
	
	private long gcd(long a, long b) {
		if (b == 0) return a;
		return gcd(b, a % b);
	}

	private long lcm(long a, long b) {
		return (a / gcd(a, b)) * b;
	}
	
	private void parseBehavior(BufferedReader in) throws IOException {
		id = Integer.parseInt(in.readLine().split(" ")[1].substring(0, 1));
		monkes[id] = this;
		
		items = new ArrayList<Long>();
		String[] startingItems = in.readLine().substring(18).split(", ");
		for(String item : startingItems) {
			items.add((long) Integer.parseInt(item));
		}
		
		String[] operationTokens = in.readLine().substring(19).split(" ");
		
		if(!operationTokens[0].equals("old")) arg1 = Integer.parseInt(operationTokens[0]);
		if(!operationTokens[2].equals("old")) arg2 = Integer.parseInt(operationTokens[2]);
		
		add = operationTokens[1].equals("+");
		
		divisibleBy = Integer.parseInt(in.readLine().substring(21));
		LCM = lcm(divisibleBy, LCM);
		
		trueMonke = Integer.parseInt(in.readLine().substring(29));
		falseMonke = Integer.parseInt(in.readLine().substring(30));
	}
	
}
