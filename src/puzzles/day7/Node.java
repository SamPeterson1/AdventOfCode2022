package puzzles.day7;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public String name;
	public int size;

	public Node(String name) {
		this(name, 0);
	}

	public Node(String name, int size) {
		this.name = name;
		this.size = size;
	}


	public Node parent;
	public List<Node> children = new ArrayList<>();

	public void addChild(Node child) {
		children.add(child);
		child.parent = this;
	}

	public int calculateSize() {
		if(this.size != 0) return this.size;

		size = 0;
		for(Node node : children) {
			size += node.calculateSize();
		}

		return size;
	}
}
