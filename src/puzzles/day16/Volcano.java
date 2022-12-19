package puzzles.day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Volcano {

	private static final int NUM_VALVES = 59;

	private HashMap<String, Valve> valvesByName;
	private ArrayList<Valve> valves;
	private ArrayList<Valve> unstuckValves;
	private ArrayList<Valve> openedValves;
	
	private int minutesLeft;
	private int releasedPressure;
	
	private Volcano() {
		this.valves = new ArrayList<Valve>();
		this.openedValves = new ArrayList<Valve>();
		this.unstuckValves = new ArrayList<Valve>();
		this.valvesByName = new HashMap<String, Valve>();
	}
	
	public Volcano(BufferedReader in) throws IOException {
		this(in, 30);
	}
	
	public Volcano(BufferedReader in, int minutes) throws IOException {
		this.valves = new ArrayList<Valve>();
		this.openedValves = new ArrayList<Valve>();
		this.unstuckValves = new ArrayList<Valve>();
		this.valvesByName = new HashMap<String, Valve>();

		for(int i = 0; i < NUM_VALVES; i ++) {
			Valve valve = new Valve(in.readLine(), this);
			valves.add(valve);
			if(!valve.isStuck()) {
				unstuckValves.add(valve);
			}
			valvesByName.put(valve.getName(), valve);
		}
		
		this.openedValves.add(getValve("AA"));
		this.minutesLeft = minutes;
		
		for(Valve valve : valves) {
			valve.computeDistanceLUT();
		}
	}
	
	public Volcano cloneClosedValves(int minutes) {
		Volcano volcano = new Volcano();
		
		for(Valve valve : this.valves) {
			if(!valve.isStuck() && !valve.isOpen()) {
				volcano.unstuckValves.add(valve);
			}
			
		}
		
		volcano.valves = this.valves;
		volcano.valvesByName = this.valvesByName;
		volcano.openedValves.add(getValve("AA"));
		volcano.minutesLeft = minutes;
		
		return volcano;
	}
	
	public ArrayList<Valve> getUnstuckValves() {
		return this.unstuckValves;
	}

	public ArrayList<Valve> getValves() {
		return this.valves;
	}

	public Valve getValve(String name) {
		return valvesByName.get(name);
	}

	
	private int simulateAll(int minutes) {
		int releasedPressure = 0;
		for(Valve valve : unstuckValves) {
			releasedPressure += valve.simulate(minutes);
		}
		
		return releasedPressure;
	}
	
	public int getReleasedPressure() {
		return releasedPressure + simulateAll(minutesLeft);
	}
	
	public int getMinutesLeft() {
		return this.minutesLeft;
	}
	
	private Valve getLastValve() {
		return openedValves.get(openedValves.size() - 1);
	}
	
	private Valve getSecondLastValve() {
		return openedValves.get(openedValves.size() - 2);
	}
	
	
	public void popValve() {
		Valve lastValve = getLastValve();
		Valve secondLastValve = getSecondLastValve();
		
		openedValves.remove(openedValves.size() - 1);
		lastValve.close();
		
		int spentMinutes = lastValve.getDistanceTo(secondLastValve) + 1;
		minutesLeft += spentMinutes;
		
		releasedPressure -= simulateAll(spentMinutes);
		
	}
	
	public boolean pushValve(Valve valve) {
		if(valve.isOpen()) return false;
		Valve lastValve = getLastValve();

		int spentMinutes = lastValve.getDistanceTo(valve) + 1;
		if(minutesLeft - spentMinutes < 0) return false;
		
		minutesLeft -= spentMinutes;
		
		
		releasedPressure += simulateAll(spentMinutes);
		valve.open();
		
		openedValves.add(valve);
		
		return true;
	}
}