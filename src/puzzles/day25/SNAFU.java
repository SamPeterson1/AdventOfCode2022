package puzzles.day25;

public class SNAFU {

	public static long toLong(String num) {
		long val = 0;
		long placeVal = 1;
		for(int i = 0; i < num.length(); i ++) {
			int digit = parseDigit(num.charAt(num.length() - i - 1));
			val += placeVal * digit;
			placeVal *= 5;
		}
		
		return val;
	}
	
	private static int parseDigit(char c) {
		if(c == '2') return 2;
		else if(c == '1') return 1;
		else if(c == '0') return 0;
		else if(c == '-') return -1;
		else if(c == '=') return -2;
		
		return 0;
	}
	
	private static char toChar(int digit) {
		if(digit == 2) return '2';
		else if(digit == 1) return '1';
		else if(digit == 0) return '0';
		else if(digit == -1) return '-';
		else if(digit == -2) return '=';
		
		return ' ';
	}
	
	public static void test(int num) {
		int[] base5 = toBase5(num);
		int[] SNAFU = toSNAFU(base5);
		int[] foo = removeLeadingZeros(SNAFU);
		System.out.print("Base 5: ");
		for(int i : base5) System.out.print(i + " ");
		System.out.println();
		System.out.print("SNAFU: ");
		for(int i : SNAFU) System.out.print(i + " ");
		System.out.println();
		System.out.print("foo: ");
		for(int i : foo) System.out.print(i + " ");
		System.out.println();
	}
	
	public static String toString(long num) {
		int[] SNAFU = removeLeadingZeros(toSNAFU(toBase5(num)));
		
		StringBuilder sb = new StringBuilder();
		for(int i = SNAFU.length - 1; i >= 0; i --) {
			sb.append(toChar(SNAFU[i]));
		}
	
		return sb.toString();
	}
	
	private static int[] removeLeadingZeros(int[] digits) {
		int highestPlace;
		for(highestPlace = digits.length - 1; highestPlace >= 0; highestPlace --) {
			if(digits[highestPlace] != 0) break;
		}
		
		int[] newDigits = new int[highestPlace + 1];
		for(int i = 0; i < newDigits.length; i ++) {
			newDigits[i] = digits[i];
		}
		
		return newDigits;
	}
	
	private static int[] toSNAFU(int[] base5) {
		int[] SNAFU = new int[base5.length + 1];
		for(int i = 0; i < base5.length; i ++) {
			SNAFU[i] += base5[i];
			if(SNAFU[i] > 2) {
				SNAFU[i] -= 5;
				SNAFU[i + 1] ++;
			}
		}
		
		return SNAFU;
	}
	
	private static int[] toBase5(long num) {
		int numDigits = (int) (Math.log(num) / Math.log(5)) + 1;
		System.out.println(numDigits);
		int[] digits = new int[numDigits];
		
		long placeVal = (long) Math.pow(5, numDigits - 1);
		for(int i = numDigits - 1; i >= 0; i --) {
			digits[i] = (int) (num / placeVal);
			num -= digits[i] * placeVal;
			placeVal /= 5;
		}
		
		return digits;
	}
	
	
	
}
