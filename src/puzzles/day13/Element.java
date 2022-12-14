package puzzles.day13;

import java.util.ArrayList;
import java.util.List;

public class Element implements Comparable<Element> {

	public static final int EQUAL_ORDER = 0;
	public static final int CORRECT_ORDER = -1;
	public static final int INCORRECT_ORDER = 1;
	
	private List<Element> elements;
	private int value;
	
	public Element(int value) {
		this.elements = null;
		this.value = value;
	}
	
	public Element() {
		this.elements = new ArrayList<Element>();
		this.value = -1;
	}
	
	public void addElement(Element e) {
		elements.add(e);
	}
	
	private boolean isList() {
		return (this.elements != null);
	}
	
	public Element asList() {
		if(isList()) return this;
		
		Element e = new Element();
		e.addElement(new Element(this.value));
		
		return e;
	}
	
	public int compareTo(Element other) {
		if(!isList() && !other.isList()) {
			if(this.value < other.value) {
				return CORRECT_ORDER;
			} else if(this.value > other.value) {
				return INCORRECT_ORDER;
			} else {
				return EQUAL_ORDER;
			}
		} else if(isList() && other.isList()) {
			int numElementsLeft = this.elements.size();
			int numElementsRight = other.elements.size();
			int minElements = Math.min(numElementsLeft, numElementsRight);

			for(int i = 0; i < minElements; i ++) {
				Element left = this.elements.get(i);
				Element right = other.elements.get(i);
				int comparison = left.compareTo(right);
				
				if(comparison != EQUAL_ORDER) return comparison;
			}
			
			if(numElementsLeft < numElementsRight) {
				return CORRECT_ORDER;
			} else if(numElementsLeft > numElementsRight) {
				return INCORRECT_ORDER;
			} else {
				return EQUAL_ORDER;
			}
		} else {
			return this.asList().compareTo(other.asList());
		}
	}

}
