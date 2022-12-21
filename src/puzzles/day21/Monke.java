package puzzles.day21;

import java.util.HashMap;

public class Monke {

	private static HashMap<String, Monke> monkes = new HashMap<String, Monke>();
	
	public static Monke get(String name) {
		return monkes.get(name);
	}
	
	private long value;
	
	private char operation;
	private String name;
	private String monkeA;
	private String monkeB;
	
	public Monke(String line) {
		this(line, false);
	}
	
	public Monke(String line, boolean part2) {
		String[] colonTokens = line.split(": ");
		name = colonTokens[0];
		monkes.put(name, this);
		
		String[] eval = colonTokens[1].split(" ");
		
		value = Integer.MAX_VALUE;
		if(eval.length == 3) {
			monkeA = eval[0];
			monkeB = eval[2];
			operation = eval[1].charAt(0);
		} else if(!(name.equals("humn") && part2)){
			value = Integer.parseInt(eval[0]);
		}
	}
	
	public long getValue() {
		return this.value;
	}
	
	public boolean isSolved() {
		return this.value != Integer.MAX_VALUE;
	}
	
	public void update() {
		Monke a = monkes.get(monkeA);
		Monke b = monkes.get(monkeB);

		if(a == null) return;
				
		if(name.equals("root")) {
			if(a.isSolved() && !b.isSolved()) {
				b.value = a.value;
			} else if(!a.isSolved() && b.isSolved()) {
				a.value = b.value;
			}			
		} else {
			if(!this.isSolved() && a.isSolved() && b.isSolved()) {
				this.value = monke();
			} else if(this.isSolved() && a.isSolved() && !b.isSolved()) {
				if(operation == '+') {
					b.value = this.value - a.value;
				} else if(operation == '-') {
					b.value = a.value - this.value;
				} else if(operation == '*') {
					b.value = this.value / a.value;
				} else if(operation == '/') {
					b.value = a.value / this.value;
				}
			} else if(this.isSolved() && !a.isSolved() && b.isSolved()) {
				if(operation == '+') {
					a.value = this.value - b.value;
				} else if(operation == '-') {
					a.value = this.value + b.value;
				} else if(operation == '*') {
					a.value = this.value / b.value;
				} else if(operation == '/') {
					a.value = this.value * b.value;
				}
			}
		}
	}
	
	public long monke() {
		if(value != Integer.MAX_VALUE) return value;
		
		Monke a = monkes.get(monkeA);
		Monke b = monkes.get(monkeB);
		
		if(operation == '+') {
			return a.monke() + b.monke();
		} else if(operation == '-') {
			return a.monke() - b.monke();
		} else if(operation == '*') {
			return a.monke() * b.monke();
		} else if(operation == '/') {
			return a.monke() / b.monke();
		}
		
		return 0;
	}
	
}
