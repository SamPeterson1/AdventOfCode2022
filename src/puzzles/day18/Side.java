package puzzles.day18;

import java.util.EnumMap;

public enum Side {
	
	TOP(0, 0, 1, 0), BOTTOM(1, 0, -1, 0), 
	LEFT(2, -1, 0, 0), RIGHT(3, 1, 0, 0), 
	FRONT(4, 0, 0, 1), BACK(5, 0, 0, -1);
	
	private static final EnumMap<Side, Side> oppositeSides = initOppositeSides();
	
	private static EnumMap<Side, Side> initOppositeSides() {
		EnumMap<Side, Side> oppositeSides = new EnumMap<Side, Side>(Side.class);
		
		oppositeSides.put(Side.TOP, Side.BOTTOM);
		oppositeSides.put(Side.LEFT, Side.RIGHT);
		oppositeSides.put(Side.FRONT, Side.BACK);
		oppositeSides.put(Side.BOTTOM, Side.TOP);
		oppositeSides.put(Side.RIGHT, Side.LEFT);
		oppositeSides.put(Side.BACK, Side.FRONT);
		
		return oppositeSides;
	}
	
	public static Side getOppositeSide(Side side) {
		return oppositeSides.get(side);
	}
	
	public final int id;
	public final int[] offset;
	
	private Side(int id, int... offset) {
		this.id = id;
		this.offset = offset;
	}

}
