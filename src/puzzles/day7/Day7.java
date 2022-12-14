package puzzles.day7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.Puzzle;
import main.Solution;

@Solution(day = 7)
public class Day7 extends Puzzle {

	private Node fileSystem;
	private Node currentNode;
	private List<Node> directories;

	private void cd(String arg) {
		if(arg.equals("..")) {
			currentNode = currentNode.parent;
		} else {	
			for(Node child : currentNode.children) {
				if(child.name.equals(arg)) {
					currentNode = child;
					break;
				}
			}
		}
	}
	
	private String dir(BufferedReader in) throws IOException{
		String line;
		
		while((line = in.readLine()) != null) {
			String[] tokens = line.split(" ");
			
			if(tokens[0].equals("dir")) {
				Node directory = new Node(tokens[1]);
				directories.add(directory);
				currentNode.addChild(directory);
			} else if(tokens[0].equals("$")) {
				return line;
			} else {
				Node file = new Node(tokens[1], Integer.parseInt(tokens[0]));
				currentNode.addChild(file);
			}
		}
		
		return null;
	}

	private void readFileSystem(BufferedReader in) throws IOException {
		fileSystem = new Node("/");
		currentNode = fileSystem;
		directories = new ArrayList<Node>();
		directories.add(fileSystem);
		
		String line = in.readLine();
		while(line != null) {
			String[] tokens = line.split(" ");
			String cmd = tokens[1];
						
			if(cmd.equals("cd")) {
				cd(tokens[2]);
				line = in.readLine();
			} else if(cmd.equals("ls")) {
				line = dir(in);
			}
		}
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		readFileSystem(in);
		
		int sumSizes = 0;
		for(Node directory : directories) {
			int size = directory.calculateSize();
			if(size <= 100000) sumSizes += size;
		}
		
		out.write(Integer.toString(sumSizes));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		readFileSystem(in);
		
		int usedSpace = fileSystem.calculateSize();
		int unusedSpace = 70000000 - usedSpace;
		int extraSpace = 30000000 - unusedSpace;
		
		int smallestSize = Integer.MAX_VALUE;
		
		for(Node directory : directories) {
			int size = directory.calculateSize();
			if(size > extraSpace && size < smallestSize) {
				smallestSize = size;
			}
		}
		
		out.write(Integer.toString(smallestSize));
	}

}
