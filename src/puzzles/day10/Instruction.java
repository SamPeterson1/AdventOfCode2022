package puzzles.day10;

public abstract class Instruction {
	
	private int cycles;
	private int maxCycles;
	private String name;
	
	public Instruction(String name, int maxCycles) {
		this.name = name;
		this.maxCycles = maxCycles;
	}
	
	public String getName() { return name; }
	
	public abstract void cycle(String[] args, int[] registers, int cycle);
	
	public boolean cycle(String[] args, int[] registers) {	
		cycle(args, registers, cycles);
		
		cycles ++;
		if(cycles == maxCycles) {
			cycles = 0;
			return true;
		}
		
		return false;
	}
	
}
