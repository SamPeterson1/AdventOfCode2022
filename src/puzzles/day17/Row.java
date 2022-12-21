package puzzles.day17;

public class Row {
	
	private int[] state;
	private int y;
	
	public Row(String state) {
		this.state = new int[Cave.WIDTH];
		
		for(int i = 0; i < state.length(); i ++) {
			char c = state.charAt(i);
			if(c == '@') this.state[i] = Cave.FALLING_ROCK;
			else if(c == '#') this.state[i] = Cave.STABLE_ROCK;
			else if(c == '.') this.state[i] = Cave.AIR;
		}
	}
	
	public Row(int y) {
		this.state = new int[Cave.WIDTH];
		this.y = y;
	}
	
	public void stabilize() { 
		for(int i = 0; i < Cave.WIDTH; i ++) {
			if(state[i] == Cave.FALLING_ROCK) state[i] = Cave.STABLE_ROCK;
		}
	}
	
	public void print() {
		for(int i = 0; i < state.length; i ++) {
			if(state[i] == Cave.FALLING_ROCK) System.out.print("@");
			else if(state[i] == Cave.STABLE_ROCK) System.out.print("#");
			else if(state[i] == Cave.AIR) System.out.print(".");
		}
		System.out.println();
	}
	
	public boolean canFallTowards(Row other) {
		for(int i = 0; i < Cave.WIDTH; i ++) {
			if(this.state[i] == Cave.FALLING_ROCK && other.state[i] == Cave.STABLE_ROCK) {
				return false;
			}
		}
		
		return true;
	}
	
	public void copyTo(Row other, int offset) {
		for(int i = 0; i < Cave.WIDTH; i ++) {
			if(this.state[i] != Cave.AIR) {
				other.state[i + offset] = this.state[i];
			}
		}
	}
	
	public void fallTowards(Row other) {
		for(int i = 0; i < Cave.WIDTH; i ++) {
			if(this.state[i] == Cave.FALLING_ROCK) {
				other.state[i] = this.state[i];
				this.state[i] = Cave.AIR;
			}
		}
	}
	
	public int getY() {
		return this.y;
	}
	
	public int get(int col) {
		return this.state[col];
	}
	
	public boolean canPushRock(int direction) {
		for(int col = 0; col < Cave.WIDTH; col ++) {
			if(this.state[col] == Cave.FALLING_ROCK) {
				int offsetCol = col + direction;
				if(offsetCol < 0 || offsetCol >= Cave.WIDTH || this.state[offsetCol] == Cave.STABLE_ROCK) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void pushRock(int direction) {
		int[] newState = new int[Cave.WIDTH];
		for(int col = 0; col < Cave.WIDTH; col ++) {
			if(this.state[col] == Cave.FALLING_ROCK) {
				int offsetCol = col + direction;
				newState[offsetCol] = this.state[col];
			}
		}
		
		for(int col = 0; col < Cave.WIDTH; col ++) {
			if(this.state[col] != Cave.STABLE_ROCK) {
				this.state[col] = newState[col];
			}
		}
	}
	
	public boolean isFull() {
		boolean isFull = true;
		for(int i = 0; i < Cave.WIDTH; i ++) {
			if(this.state[i] != Cave.STABLE_ROCK) isFull = false;
		}
		
		return isFull;
	}
	
	public byte getHash() {
		byte val = 0;
		byte placeVal = 1;
		for(int i = 0; i < Cave.WIDTH; i ++) {
			if(this.state[i] == Cave.STABLE_ROCK) val += placeVal;
			placeVal *= 2;
		}
		
		return val;
	}
	
	public boolean isStable(boolean airIsStable) {
		boolean isAir = true;
		
		for(int i = 0; i < Cave.WIDTH; i ++) {
			if(this.state[i] == Cave.FALLING_ROCK) {
				return false;
			} else if(this.state[i] != Cave.AIR) {
				isAir = false;
			}
		}
		
		return airIsStable ? true : !isAir;
	}
}
