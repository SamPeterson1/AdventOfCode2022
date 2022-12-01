package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Puzzle {
	
	private int id;
	
	public Puzzle(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	protected abstract void compute(BufferedReader in, BufferedWriter out) throws IOException;
	
	private StringBuilder getGenericPath() {
		int dayID = id / PuzzleMaster.PUZZLES_PER_DAY;
		int puzzleID = id % PuzzleMaster.PUZZLES_PER_DAY;
		
		StringBuilder path = new StringBuilder();
		path.append("src/txt/day").append(dayID + 1).append("/Puzzle").append(dayID + 1).append("-").append(puzzleID + 1).append("_");
		
		return path;
	}
	
	private String getInputPath() {
		return getGenericPath().append("Input.txt").toString();
	}
	
	private String getOutputPath() {
		return getGenericPath().append("Output.txt").toString();
	}
	
	private BufferedReader getInput() {
		String inputPath = getInputPath();
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(inputPath));
		} catch (FileNotFoundException e) {
			System.err.println("Unable to find input file \"" + inputPath + "\"");
			e.printStackTrace();
		}
		
		return in;
	}
	
	private BufferedWriter getOutput() {
		String outputPath = getOutputPath();
		BufferedWriter out = null;
		
		try {
			 out = new BufferedWriter(new FileWriter(outputPath));
		} catch (IOException e) {
			System.err.println("Unable to find output file \"" + outputPath + "\"");
			e.printStackTrace();
		}
		
		return out;
	}
	
	public void run() {
		
		BufferedReader in = getInput();
		BufferedWriter out = getOutput();
		
		if(in == null || out == null) return;
		
		try {
			compute(in, out);
		} catch (IOException e) {
			System.err.println("Error processing puzzle files");
			e.printStackTrace();
		}
		
		
		try {
			in.close();
		} catch (IOException e) {
			System.err.println("Error closing input file");
			e.printStackTrace();
		}
		
		try {
			out.close();
		} catch (IOException e) {
			System.err.println("Error closing output file");
			e.printStackTrace();
		}
	}
}
