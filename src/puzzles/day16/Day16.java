package puzzles.day16;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import main.Puzzle;
import main.Solution;

@Solution(day = 16)
public class Day16 extends Puzzle {

	private int getMaxPressureRelease(Volcano volcano, boolean elephantHelp, int maxDepth, int depth) {
		int maxPressureRelease = 0;
		
		if(depth == maxDepth) {
			if(elephantHelp) {
				Volcano elephantVolcano = volcano.cloneClosedValves(26);
				return volcano.getReleasedPressure() + getMaxPressureRelease(elephantVolcano, false, Integer.MAX_VALUE, 0);
			}
			return volcano.getReleasedPressure();
		}
		
		boolean openedValve = false;
		for(Valve valve : volcano.getUnstuckValves()) {
			if(volcano.pushValve(valve)) {
				openedValve = true;
				if(depth == 0 && elephantHelp) System.out.print(valve.getName());
				int pressureRelease = getMaxPressureRelease(volcano, elephantHelp, maxDepth, depth + 1);
				if(pressureRelease > maxPressureRelease) {
					maxPressureRelease = pressureRelease;
				}
				
				volcano.popValve();
			}
		}
		
		if(depth == 0 && elephantHelp) System.out.println();
				
		if(openedValve) {
			return maxPressureRelease;
		} else {
			if(elephantHelp) {
				Volcano elephantVolcano = volcano.cloneClosedValves(26);
				return volcano.getReleasedPressure() + getMaxPressureRelease(elephantVolcano, false, Integer.MAX_VALUE, 0);
			}
			return volcano.getReleasedPressure();
		}
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		Volcano volcano = new Volcano(in);
		out.write(Integer.toString(getMaxPressureRelease(volcano, false, Integer.MAX_VALUE, 0)));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		Volcano volcano = new Volcano(in, 26);
		int best = 0;
		for(int i = 0; i < volcano.getUnstuckValves().size(); i ++) {
			int pressure = getMaxPressureRelease(volcano, true, i, 0);
			if(pressure > best) best = pressure;
		}
		
		out.write(Integer.toString(best));
	}

}
