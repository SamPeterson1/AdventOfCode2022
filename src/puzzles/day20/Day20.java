package puzzles.day20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.Puzzle;
import main.Solution;

@Solution(day = 20)
public class Day20 extends Puzzle {

	private static final long DECRYPTION_KEY = 811589153;
	
	private class Element {
		public long val;
		public Element(long val) { this.val = val; }
	}
	
	private List<Element> readNums(BufferedReader in) throws IOException {
		return readNums(in, false);
	}
	
	private List<Element> readNums(BufferedReader in, boolean useKey) throws IOException {
		List<Element> nums = new ArrayList<Element>();
		
		String line;
		long key = useKey ? DECRYPTION_KEY : 1;
		
		while((line = in.readLine()) != null) {
			nums.add(new Element(key * Integer.valueOf(line)));
		}
		
		return nums;
	}
	
	private void moveNum(List<Element> nums, Element num) {
		int index;
		for(index = 0; index < nums.size(); index ++) {
			if(nums.get(index) == num) break;
		}
		
		int newIndex = Math.floorMod(index + num.val, nums.size() - 1);
		
		nums.add(newIndex, nums.remove(index));
	}
	
	private void mix(List<Element> nums, List<Element> originalOrder) {
		for(Element num : originalOrder) {
			moveNum(nums, num);
		}
	}
	
	
	private long getAfterZero(List<Element> nums, int index) {
		int i;
		for(i = 0; i < nums.size(); i ++) {
			if(nums.get(i).val == 0) break;
		}
		
		index += i;
		index %= nums.size();
		
		return nums.get(index).val;
	}
	
	@Override
	protected void part1(BufferedReader in, BufferedWriter out) throws IOException {
		List<Element> nums = readNums(in);
		List<Element> originalOrder = new ArrayList<Element>();
		for(Element num : nums) originalOrder.add(num);

		
		mix(nums, originalOrder);
		
		long sum = getAfterZero(nums, 1000) + getAfterZero(nums, 2000) + getAfterZero(nums, 3000);
		out.write(Long.toString(sum));
	}

	@Override
	protected void part2(BufferedReader in, BufferedWriter out) throws IOException {
		List<Element> nums = readNums(in, true);
		List<Element> originalOrder = new ArrayList<Element>();
		for(Element num : nums) originalOrder.add(num);
		
		for(int i = 0; i < 10; i ++) {
			for(Element j : nums) {
				System.out.print(j.val + " ");
			}
			System.out.println();
			mix(nums, originalOrder);
		}
		for(Element j : nums) {
			System.out.print(j.val + " ");
		}
		System.out.println();
		
		long sum = getAfterZero(nums, 1000) + getAfterZero(nums, 2000) + getAfterZero(nums, 3000);
		out.write(Long.toString(sum));
	}

}
