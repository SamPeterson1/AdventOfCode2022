package puzzles.day18;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import main.Puzzle;
import main.Solution;

@Solution(day = 18)
public class Day18 extends Puzzle {

	private static final Side[] sides = (Side[]) Side.class.getEnumConstants();
	private static final int SIZE = 20;
	
	private ArrayList<LavaCell> lavaCellList;
	private LavaCell[][][] lavaCellGrid;
	private boolean[][][] isOutside;
	
	private void readInput(BufferedReader in) throws IOException {
		
		lavaCellList = new ArrayList<LavaCell>();
		lavaCellGrid = new LavaCell[SIZE][SIZE][SIZE];
		
		String line;
		while((line = in.readLine()) != null) {
			String[] coordsStr = line.split(",");
			int[] coords = new int[3];
			
			for(int i = 0; i < 3; i ++) {
				coords[i] = Integer.parseInt(coordsStr[i]);
			}
			
			LavaCell cell = new LavaCell();
			
			int[] neighborCoords = new int[3];
			for(Side side : sides) {
				for(int i = 0; i < 3; i ++) {
					neighborCoords[i] = coords[i] + side.offset[i];
				}
				
				LavaCell neighbor = getCell(neighborCoords);
				if(neighbor != null) cell.addNeighbor(neighbor, side);
			}
			
			setCell(cell, coords);
			lavaCellList.add(cell);
		}
	}
		
	private void setOutside(int... coords) {
		if(!checkBounds(coords)) return;
		
		boolean current = isOutside[coords[0]][coords[1]][coords[2]];
		if(current) return;
		
		isOutside[coords[0]][coords[1]][coords[2]] = true;
		
		int[] neighborCoords = new int[3];
		for(Side side : sides) {
			for(int i = 0; i < 3; i ++) {
				neighborCoords[i] = coords[i] + side.offset[i];
			}
			
			if(getCell(neighborCoords) == null) {
				setOutside(neighborCoords);
			}
		}
	}
	
	private void setCell(LavaCell cell, int ...coords) {
		lavaCellGrid[coords[0]][coords[1]][coords[2]] = cell;
	}
	
	private boolean checkBounds(int ...coords) {
		for(int i = 0; i < 3; i ++) {
			if(coords[i] < 0 || coords[i] >= SIZE) return false;
		}
		
		return true;
	}
	
	private LavaCell getCell(int ...coords) {
		if(!checkBounds(coords)) return null;
		return lavaCellGrid[coords[0]][coords[1]][coords[2]];
	}
	
	private int calculateSurfaceArea() {
		int surfaceArea = 0;
		for(LavaCell cell : lavaCellList) {
			surfaceArea += cell.getSurfaceArea();
		}
		
		return surfaceArea;
	}
	
	private void fillInterior() {
		this.isOutside = new boolean[SIZE][SIZE][SIZE];
		setOutside(1, 0, 0, 0);
		
		for(int x = 0; x < SIZE; x ++) {
			for(int y = 0; y < SIZE; y ++) {
				for(int z = 0; z < SIZE; z ++) {
					if(!isOutside[x][y][z] && getCell(x, y, z) == null) {
						for(Side side : sides) {
							LavaCell neighbor = getCell(x + side.offset[0], y + side.offset[1], z + side.offset[2]);
							if(neighbor != null) {
								neighbor.addNeighbor(null, Side.getOppositeSide(side));
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		readInput(in);
		
		out.write(Integer.toString(calculateSurfaceArea()));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		readInput(in);
		fillInterior();
		//printSlice(10);
		out.write(Integer.toString(calculateSurfaceArea()));
	}

	
}
