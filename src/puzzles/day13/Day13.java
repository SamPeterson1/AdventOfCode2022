package puzzles.day13;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Puzzle;
import main.Solution;

@Solution(day = 13)
public class Day13 extends Puzzle {
	
	private Element parseElement(String line) {
		if(line.length() == 0) 
			return new Element();
		
		if(line.charAt(0) == '[') {
			String listContents = line.substring(1, line.length() - 1);
			String[] listElements = splitOutsideBrackets(listContents);
			
			Element list = new Element();
			for(String element : listElements) {
				Element e = parseElement(element);
				list.addElement(e);
			}
			
			return list;
		} else {
			int value = Integer.parseInt(line);
			return new Element(value);
		}
	}
	
	private String[] splitOutsideBrackets(String str) {
		int brackets = 0;
		List<Integer> commaIndices = new ArrayList<Integer>();
		
		int len = str.length();
		for(int i = 0; i < len; i ++) {
			char c = str.charAt(i);
			if(c == '[') brackets ++;
			else if(c == ']') brackets --;
			
			if(brackets == 0 && c == ',') {
				commaIndices.add(i);
			}
		}
		
		String[] retVal = new String[commaIndices.size() + 1];
		int prevCommaIndex = -1;
		for(int i = 0; i < commaIndices.size(); i ++) {
			int commaIndex = commaIndices.get(i);
			retVal[i] = str.substring(prevCommaIndex + 1, commaIndex);
			prevCommaIndex = commaIndex;
		}
		
		retVal[commaIndices.size()] = str.substring(prevCommaIndex + 1);
		return retVal;
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		int index = 1;
		int sumCorrectIndices = 0;
		
		while(true) {
			String leftStr = in.readLine();
			if(leftStr == null) break;
			String rightStr = in.readLine();
			
			Element left = parseElement(leftStr);
			Element right = parseElement(rightStr);
			
			if(left.compareTo(right) == Element.CORRECT_ORDER) {
				sumCorrectIndices += index;
			}
			
			in.readLine();
			index ++;
		}

		out.write(Integer.toString(sumCorrectIndices));
	}
	
	private void printList(List<Integer> list) {
		System.out.print("[");
		for(int i = 0; i < list.size(); i ++) {
			System.out.print(list.get(i));
			if(i < list.size() - 1) System.out.print(", ");
		}
		System.out.println("]");
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		ArrayList<Element> elements = new ArrayList<Element>();
		String line;
		
		while((line = in.readLine()) != null) {
			if(!line.equals("")) {
				elements.add(parseElement(line));
			}
		}
		
		
		
		Element divider1 = parseElement("[[2]]");
		Element divider2 = parseElement("[[6]]");
		
		elements.add(divider1);
		elements.add(divider2);
		
		Collections.sort(elements);
		
		int decoderKey = (elements.indexOf(divider1) + 1) * (elements.indexOf(divider2) + 1);
		out.write(Integer.toString(decoderKey));
	}

}
