package puzzles.day19;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.EnumMap;

import puzzles.day19.Blueprint.ResourceAmount;
import puzzles.day19.Blueprint.Robot;

public class MiningSimulation {

	private static final int NUM_BLUEPRINTS = 30;
	
	private Blueprint[] blueprints;
	private Blueprint blueprint;
	
	private EnumMap<Resource, Integer> robotAmounts;
	private EnumMap<Resource, Integer> resourceAmounts;
	
	int[] foo;
	int[][] bar;
	
	public MiningSimulation(BufferedReader in) throws IOException {
		this.robotAmounts = new EnumMap<Resource, Integer>(Resource.class);
		this.resourceAmounts = new EnumMap<Resource, Integer>(Resource.class);
		
		this.blueprints = new Blueprint[NUM_BLUEPRINTS];		
		for(int i = 0; i < NUM_BLUEPRINTS; i ++) {
			this.blueprints[i] = new Blueprint(in.readLine());
		}
	}
	
	private void selectBlueprint(Blueprint blueprint) {
		this.blueprint = blueprint;
		
		for(Resource resource : Resource.ALL) {
			robotAmounts.put(resource, 0);
			resourceAmounts.put(resource, 0);
		}
	}
	
	private void runRobots(int minutes) {
		runRobots(false, minutes);
	}
	
	private void runRobots(boolean reverse, int minutes) {
		int sign = reverse ? -1 : 1;
		for(Resource resource : Resource.ALL) {
			int resourceAmount = resourceAmounts.get(resource);
			resourceAmount += sign * minutes * robotAmounts.get(resource);
			
			resourceAmounts.put(resource, resourceAmount);
		}
	}

	private void addRobot(Resource type) {
		for(ResourceAmount cost : blueprint.getRobot(type).costs.values()) {
			int newAmount = resourceAmounts.get(cost.type) - cost.amount;
			resourceAmounts.put(cost.type, newAmount);
		}
		
		robotAmounts.put(type, robotAmounts.get(type) + 1);		
	}

	private void restoreCosts(Resource type) {
		for(ResourceAmount cost : blueprint.getRobot(type).costs.values()) {
			resourceAmounts.put(cost.type, resourceAmounts.get(cost.type) + cost.amount);
		}
	}
	
	private int getAbundance(Resource resource, int minutesLeft) {
		int maxCost = blueprint.getGreatestCost(resource);
		int currentProduction = robotAmounts.get(resource);
		int currentAmount = resourceAmounts.get(resource);
		int minFinalAmount = currentAmount + minutesLeft * (currentProduction - maxCost);
		
		return minFinalAmount;
	}
	
	private int timeToProduce(ResourceAmount amount) {
		if(robotAmounts.get(amount.type) == 0) return Integer.MAX_VALUE;
		float exactTime = (float) (amount.amount - resourceAmounts.get(amount.type)) / robotAmounts.get(amount.type);
		return (int) Math.ceil(exactTime);
	}
	
	private int timeToProduce(Robot robot) {
		int maxTime = 0;
		for(ResourceAmount cost : robot.costs.values()) {
			int time = timeToProduce(cost);
			if(time > maxTime) maxTime = time;
		}
		
		return maxTime;
	}
	
	private void removeRobot(Resource type) {
		restoreCosts(type);
		robotAmounts.put(type, robotAmounts.get(type) - 1);
	}
	
	private int getNumGeodes() {
		return resourceAmounts.get(Resource.GEODE);
	}
	
	private int runMinute(int minutesRemaining) {
		if(minutesRemaining == 0) return getNumGeodes();
		
		int mostGeodes = 0;
		for(Resource resource : Resource.ALL) {
			boolean needsProduction = (resource == Resource.GEODE || getAbundance(resource, minutesRemaining) < 0);
			if(needsProduction) {
				int minutes = timeToProduce(blueprint.getRobot(resource));
				if(minutes < minutesRemaining) {
					runRobots(minutes + 1);
					addRobot(resource);
					
					int numGeodes = runMinute(minutesRemaining - minutes - 1);
					if(numGeodes > mostGeodes) mostGeodes = numGeodes;
					
					removeRobot(resource);
					runRobots(true, minutes + 1);
				}
			}
		}
		
		runRobots(minutesRemaining);
		int numGeodes = getNumGeodes();
		if(numGeodes > mostGeodes) mostGeodes = numGeodes;
		runRobots(true, minutesRemaining);
		
		return mostGeodes;
	}
	
	private int getQualityLevel(Blueprint blueprint, int numMinutes) {
		return getGeodePotential(blueprint, numMinutes) * blueprint.getID();
	}
	
	private int getGeodePotential(Blueprint blueprint, int numMinutes) {
		selectBlueprint(blueprint);
		robotAmounts.put(Resource.ORE, 1);

		return runMinute(numMinutes);
	}

	public int getGeodeProduct() {
		int prod = 1;
		for(int i = 0; i < 3; i ++) {
			int geodes = getGeodePotential(blueprints[i], 32);
			prod *= geodes;
			System.out.println(blueprint.getID() + " " + geodes);
		}
		
		return prod;
	}
	
	public int getQualitySum() {
		int sum = 0;
		
		for(Blueprint blueprint : blueprints) {
			int quality = getQualityLevel(blueprint, 24);
			sum += quality;
			System.out.println(blueprint.getID() + " " + quality);
		}
		
		
		return sum;
	}
	
}
