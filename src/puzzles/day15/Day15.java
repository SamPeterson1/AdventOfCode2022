package puzzles.day15;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import main.Puzzle;
import main.Solution;

@Solution(day = 15)
public class Day15 extends Puzzle {

	private static final int NUM_SENSORS = 24;
	private Sensor[] sensors;


	private void loadSensors(BufferedReader in) throws IOException {
		int i = 0;
		sensors = new Sensor[NUM_SENSORS];

		String line;
		while((line = in.readLine()) != null) {
			sensors[i ++] = new Sensor(line);
		}
	}

	private boolean beaconCanExist(int x, int y) {
		boolean beaconCanExist = true;
		for(Sensor sensor : sensors) {
			if(!sensor.beaconCanExist(x, 2000000)) {
				beaconCanExist = false;
			}
		}

		return beaconCanExist;
	}

	private boolean distressBeaconCanExist(int x, int y) {
		boolean distressBeaconCanExist = true;
		for(Sensor sensor : sensors) {
			if(!sensor.distressBeaconCanExist(x, 2000000)) {
				distressBeaconCanExist = false;
			}
		}

		return distressBeaconCanExist;
	}

	private int findDistressBeacon(int y) {
		int x = 0;
		while(x <= 4000000) {
			boolean foundSensor = false;
			for(int i = 0; i < sensors.length; i ++) {
				Sensor sensor = sensors[i];
				
				if(sensor.inRange(x, y)) {
					x = sensor.getRightmostCoveredXPosition(y) + 1;
					foundSensor = true;
					break;
				}
			}
			
			if(!foundSensor) return x;
		}

		return -1;
	}

	private int getTuningFrequency(int x, int y) {
		return 4000000 * x + y;
	}

	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		loadSensors(in);

		int minX = Sensor.getLeftmostCoveredPosition();
		int maxX = Sensor.getRightmostCoveredPosition();

		int numEliminatedPositions = 0;

		for(int x = minX; x <= maxX; x ++) {
			if(!beaconCanExist(x, 2000000)) {
				numEliminatedPositions ++;
			}
		}

		out.write(Integer.toString(numEliminatedPositions));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {		
		loadSensors(in);
		
		for(int y = 0; y <= 4000000; y ++) {
			int position = findDistressBeacon(y);
			if(position != -1) {
				out.write("x: " + position + ", y: " + y);
				break;
			}
		}
	}

}
