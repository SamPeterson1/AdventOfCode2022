package puzzles.day16;

import java.util.HashMap;

public class Valve {

	private HashMap<Valve, Integer> distanceLUT;
	
	private Volcano volcano;

	private String name;
	private int flowRate;
	private String[] tunnelNames;

	private boolean isOpen;

	public Valve(String line, Volcano volcano) {
		this.volcano = volcano;
		this.distanceLUT = new HashMap<Valve, Integer>();
		
		parseInput(line);
	}
	
	public void computeDistanceLUT() {
		setDistanceTo(this, 0);
	}
	
	private void parseInput(String line) {
		String[] parts = line.split("; ");

		this.name = parts[0].substring(6, 8);
		this.flowRate = Integer.parseInt(parts[0].substring(23));
		
		String[] part2Tokens = parts[1].split(" ");
		this.tunnelNames = new String[part2Tokens.length - 4];
		
		for(int i = 4; i < part2Tokens.length; i ++) {
			tunnelNames[i - 4] = part2Tokens[i].substring(0, 2);
		}
	}
	
	public boolean isStuck() {
		return this.flowRate == 0;
	}
	
	public boolean isOpen() {
		return this.isOpen;
	}
	
	public int getDistanceTo(Valve other) {
		if(distanceLUT.containsKey(other)) {
			return distanceLUT.get(other);
		}
		
		return Integer.MAX_VALUE;
	}
	
	private void setDistanceTo(Valve other, int distance) {
		if(distance < getDistanceTo(other)) {
			distanceLUT.put(other, distance);
			
			for(String connectedValveName : other.getTunnelNames()) {
				Valve connectedValve = volcano.getValve(connectedValveName);
				setDistanceTo(connectedValve, distance + 1);
			}
		}
	}
	
	public String getName() {
		return this.name;
	}

	public String[] getTunnelNames() {
		return this.tunnelNames;
	}

	public void close() {
		this.isOpen = false;
	}
	
	public void open() {
		this.isOpen = true;
	}
	
	public int simulate(int minutes) {
		return isOpen ? (minutes * flowRate) : 0;
	}


}
