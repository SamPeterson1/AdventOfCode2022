package puzzles.day19;

import java.util.EnumMap;

public class Blueprint {
	
	public class ResourceAmount {
		
		public final Resource type;
		public final int amount;
		
		public ResourceAmount(Resource type, int amount) {
			this.type = type;
			this.amount = amount;
		}
		
	}
	
	public class Robot {
		
		public final ResourceAmount production;
		public final EnumMap<Resource, ResourceAmount> costs;

		public Robot(ResourceAmount production, ResourceAmount[] costs) {
			this.production = production;
			this.costs = new EnumMap<Resource, ResourceAmount>(Resource.class);
			for(ResourceAmount cost : costs) {
				this.costs.put(cost.type, cost);
			}
		}
		
	}
	
	private EnumMap<Resource, Integer> greatestCosts;
	private EnumMap<Resource, Robot> robots;
	private int id;
	
	public Blueprint(String line) {
		String[] colonTokens = line.split(": ");
		
		String idStr = colonTokens[0];
		int id = Integer.valueOf(idStr.split(" ")[1]);
		this.id = id;
		
		String[] robotsStr = colonTokens[1].substring(0, colonTokens[1].length() - 1).split("\\. ");
		this.robots = new EnumMap<Resource, Robot>(Resource.class);
		this.greatestCosts = new EnumMap<Resource, Integer>(Resource.class);
		
		for(Resource resource : Resource.ALL) {
			this.greatestCosts.put(resource, 0);
		}
		
		for(int i = 0; i < robotsStr.length; i ++) {
			Robot robot = parseRobot(robotsStr[i]);
			this.robots.put(robot.production.type, robot);
		}
	}
	
	public int getGreatestCost(Resource type) {
		return this.greatestCosts.get(type);
	}
	
	public Robot getRobot(Resource type) {
		return this.robots.get(type);
	}
	
	public int getID() {
		return this.id;
	}

	private Robot parseRobot(String robotStr) {
		String[] words = robotStr.split(" ");
		ResourceAmount production = new ResourceAmount(Resource.valueOf(words[1].toUpperCase()), 1);
		ResourceAmount[] costs = new ResourceAmount[(words.length - 4) / 3 + 1];
		
		int j = 0;
		for(int i = 4; i < words.length; i += 3) {
			int costAmount = Integer.valueOf(words[i]);
			Resource costType = Resource.valueOf(words[i + 1].toUpperCase());
			
			if(production.type != costType && costAmount > greatestCosts.get(costType)) {
				greatestCosts.put(costType, costAmount);
			}
			
			costs[j ++] = new ResourceAmount(costType, costAmount);
		}
		
		return new Robot(production, costs);
	}
	
}
