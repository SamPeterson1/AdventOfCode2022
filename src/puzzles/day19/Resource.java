package puzzles.day19;

public enum Resource {
	ORE(0), CLAY(1), OBSIDIAN(2), GEODE(3);
	
	public static Resource[] ALL = new Resource[] {ORE, CLAY, OBSIDIAN, GEODE};
	
	public static Resource fromString(String str) {
		if(str.equals("ore")) {
			return ORE;
		} else if(str.equals("clay")) {
			return CLAY;
		} else if(str.equals("obsidian")) {
			return OBSIDIAN;
		} else if(str.equals("geode")) {
			return GEODE;
		}
		
		return null;
	}
	
	private int id;
	private Resource(int id) { this.id = id; }
	public int getID() { return this.id; }
}
