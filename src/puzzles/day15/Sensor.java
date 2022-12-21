package puzzles.day15;

public class Sensor {

	private static int leftmostSensor;
	private static int rightmostSensor;
	private static int highestCoverage;

	public static int getLeftmostCoveredPosition() {
		return leftmostSensor - highestCoverage;
	}

	public static int getRightmostCoveredPosition() {
		return rightmostSensor + highestCoverage;
	}

	public static int manhattanDist(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}
	
	public static int manhattanDist(int[] a, int b1, int b2) {
		return Math.abs(a[0] - b1) + Math.abs(a[1] - b2);
	}

	private int[] sensorPos;
	private int[] beaconPos;

	private int coveredRadius;

	public Sensor(String line) {
		String[] tokens = line.split(": ");
		String sensorXY = tokens[0].substring(10);
		String beaconXY = tokens[1].substring(21);

		this.sensorPos = parseCoordinates(sensorXY);
		this.beaconPos = parseCoordinates(beaconXY);

		if(beaconPos[0] > rightmostSensor) {
			rightmostSensor = beaconPos[0];
		} else if(beaconPos[0] < leftmostSensor) {
			leftmostSensor = beaconPos[0];
		}

		this.coveredRadius = manhattanDist(sensorPos, beaconPos);

		if(coveredRadius > highestCoverage) {
			highestCoverage = coveredRadius;
		}
	}

	public int[] getSensorPos() {
		return this.sensorPos;
	}

	public int[] getBeaconPos() {
		return this.beaconPos;
	}

	public int getCoveredRadius() {
		return this.coveredRadius;
	}

	public int getRightmostCoveredXPosition(int y) {
		int dy = Math.abs(sensorPos[1] - y);
		int localRadius = coveredRadius - dy;

		return sensorPos[0] + localRadius;
	}

	public boolean distressBeaconCanExist(int x, int y) {
		if(beaconPos[0] == x && beaconPos[1] == y) return false;

		int dist = manhattanDist(new int[] {x, y}, sensorPos);
		return (dist > coveredRadius);
	}

	public boolean beaconCanExist(int x, int y) {
		if(beaconPos[0] == x && beaconPos[1] == y) return true;

		int dist = manhattanDist(new int[] {x, y}, sensorPos);
		return (dist > coveredRadius);
	}

	public boolean inRange(int x, int y) {
		return distanceTo(x, y) <= coveredRadius;
	}

	public int distanceTo(int x, int y) {
		return manhattanDist(sensorPos, x, y);
	}

	private int[] parseCoordinates(String coordinates) {
		String[] coordinateTokens = coordinates.split(", ");
		String xString = coordinateTokens[0].substring(2);
		String yString = coordinateTokens[1].substring(2);

		return new int[] {Integer.parseInt(xString), Integer.parseInt(yString)};
	}



}
