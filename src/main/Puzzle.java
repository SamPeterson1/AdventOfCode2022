package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Puzzle {

	private int day;

	public Puzzle() {
		Solution annotation = this.getClass().getAnnotation(Solution.class);
		if(annotation != null) this.day = annotation.day();
	}

	public int getID() {
		return this.day;
	}

	protected abstract void part1(BufferedReader in, BufferedWriter out) throws IOException;
	protected abstract void part2(BufferedReader in, BufferedWriter out) throws IOException;


	private String getInputPath() {
		StringBuilder path = new StringBuilder();

		path.append("src/txt/day").append(day).append("/Input.txt");
		return path.toString();
	}

	private String getOutputPath(int part) {
		StringBuilder path = new StringBuilder();
		path.append("src/txt/day").append(day).append("/Output_Part").append(part + 1).append(".txt");

		return path.toString();
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

	private BufferedWriter getOutput(int part) {
		String outputPath = getOutputPath(part);
		BufferedWriter out = null;

		try {
			 out = new BufferedWriter(new FileWriter(outputPath));
		} catch (IOException e) {
			System.err.println("Unable to find output file \"" + outputPath + "\"");
			e.printStackTrace();
		}

		return out;
	}

	public void run() throws IOException {

		System.out.println("Running day " + day + "... ");
		long startTime = System.currentTimeMillis();

		BufferedReader in = getInput();
		BufferedWriter outPart1 = getOutput(0);
		BufferedWriter outPart2 = getOutput(1);

		if(in == null) {
			System.err.println("No input found for day " + day);
		}


		part1(in, outPart1);
		outPart1.close();
		in.close();
		in = getInput();

		part2(in, outPart2);
		in.close();
		outPart2.close();

		int elapsedMillis = (int) (System.currentTimeMillis() - startTime);
		System.out.println("Day " + day + " finished in " + elapsedMillis + "ms");
	}
}
