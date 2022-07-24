package de.ancash.misc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ConversionUtil {

	public static String bytesToHex(byte...bytes) {
		StringBuilder sb = new StringBuilder();
	    for (byte b : bytes) 
	        sb.append("0x" + String.format("%02X ", b));
	    return sb.toString();
	}
	
	/** Smallest and largest base you want to accept as valid input */
	final int MINIMUM_BASE = 2;

	final int MAXIMUM_BASE = 36;

	/** Checks if a number (as a String) is valid for a given base. */
	public static boolean validForBase(String n, int base) {
		char[] validDigits = {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};
		// digitsForBase contains all the valid digits for the base given
		char[] digitsForBase = Arrays.copyOfRange(validDigits, 0, base);
		
		// Convert character array into set for convenience of contains() method
		HashSet<Character> digitsList = new HashSet<>();
		for (int i = 0; i < digitsForBase.length; i++) digitsList.add(digitsForBase[i]);
		
		// Check that every digit in n is within the list of valid digits for that base.
		for (char c : n.toCharArray()) if (!digitsList.contains(c)) return false;

		return true;
	}

	public static byte[] shortToBytes(short s) {
		byte []array = new byte[2];
		array[0] = (byte)(s & 0xff);
		array[1] = (byte)((s >> 8) & 0xff);
		return array;
	}
	
	public static int bytesToInt(byte[] bytes) {
	     return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
	}
	
	public static short bytesToShort(byte firstByte, byte secondByte) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(firstByte);
		bb.put(secondByte);
		return bb.getShort(0);
	}
	
	public static byte[] intToBytes(int value) {
	    return new byte[] { 
		        (byte)(value >> 24),
		        (byte)(value >> 16),
		        (byte)(value >> 8),
		        (byte)value };
		}
	
	/**
     * Creates a new instance from given boolean.
     * This will create a new single array element array instance using the convention that false is zero.
     * E.g. Creates array <code>new byte[] {1}</code> if booleanValue is true and <code>new byte[] {0}</code> if
     * booleanValue is false.
     *
     * @param booleanValue to convert (false is zero, true is one)
     * @return new instance
     */
    public static byte from(boolean booleanValue) {
        return booleanValue ? (byte) 1 : 0;
    }

    /**
     * Creates a new instance from given unsigned 2 byte char.
     *
     * @param char2Byte to create from
     * @return new instance
     */
    public static byte[] from(char char2Byte) {
        return ByteBuffer.allocate(2).putChar(char2Byte).array();
    }

    /**
     * Creates a new instance from given 2 byte short.
     *
     * @param short2Byte to create from
     * @return new instance
     */
    public static byte[] from(short short2Byte) {
        return ByteBuffer.allocate(2).putShort(short2Byte).array();
    }
    /**
     * Creates a new instance from given 4 byte integer.
     *
     * @param integer4byte to create from
     * @return new instance
     */
    public static byte[] from(int integer4byte) {
        return ByteBuffer.allocate(4).putInt(integer4byte).array();
    }
    
    /**
     * Creates a new instance from given 8 byte long.
     *
     * @param long8byte to create from
     * @return new instance
     */
    public static byte[] from(long long8byte) {
    	return ByteBuffer.allocate(8).putLong(long8byte).array();
    }

    /**
     * Creates a new instance from given 4 byte floating point number (float).
     *
     * @param float4byte to create from
     * @return new instance
     */
    public static byte[] from(float float4byte) {
        return ByteBuffer.allocate(4).putFloat(float4byte).array();
    }

    /**
     * Creates a new instance from given 8 byte floating point number (double).
     *
     * @param double8Byte to create from
     * @return new instance
     */
    public static byte[] from(double double8Byte) {
        return ByteBuffer.allocate(8).putDouble(double8Byte).array();
    }
		
	/**
	 * Method to convert any integer from base b1 to base b2. Works by converting from b1 to decimal,
	 * then decimal to b2.
	 *
	 * @param n The integer to be converted.
	 * @param b1 Beginning base.
	 * @param b2 End base.
	 * @return n in base b2.
	 */
	public static String baseTobase(String n, int b1, int b2) {
		// Declare variables: decimal value of n,
		// character of base b1, character of base b2,
		// and the string that will be returned.
		int decimalValue = 0, charB2;
		char charB1;
		String output = "";
		// Go through every character of n
		for (int i = 0; i < n.length(); i++) {
			// store the character in charB1
			charB1 = n.charAt(i);
			// if it is a non-number, convert it to a decimal value >9 and store it in charB2
			if (charB1 >= 'A' && charB1 <= 'Z') charB2 = 10 + (charB1 - 'A');
			// Else, store the integer value in charB2
			else charB2 = charB1 - '0';
			// Convert the digit to decimal and add it to the
			// decimalValue of n
			decimalValue = decimalValue * b1 + charB2;
		}

		// Converting the decimal value to base b2:
		// A number is converted from decimal to another base
		// by continuously dividing by the base and recording
		// the remainder until the quotient is zero. The number in the
		// new base is the remainders, with the last remainder
		// being the left-most digit.
		if (0 == decimalValue) return "0";
		// While the quotient is NOT zero:
		while (decimalValue != 0) {
			// If the remainder is a digit < 10, simply add it to
			// the left side of the new number.
			if (decimalValue % b2 < 10) output = Integer.toString(decimalValue % b2) + output;
			// If the remainder is >= 10, add a character with the
			// corresponding value to the new number. (A = 10, B = 11, C = 12, ...)
			else output = (char) ((decimalValue % b2) + 55) + output;
			// Divide by the new base again
			decimalValue /= b2;
		}
		return output;
	}	
	
	/**
	 * Convert any radix to decimal number
	 *
	 * @param s the string to be convert
	 * @param radix the radix
	 * @return decimal of bits
	 * @throws NumberFormatException if {@code bits} or {@code radix} is invalid
	 */
	public static int convertToDec(String s, int radix) {
		int num = 0;
		int pow = 1;
		
		for (int i = s.length() - 1; i >= 0; i--) {
			int digit = valOfChar(s.charAt(i));
			if (digit >= radix) {
				throw new NumberFormatException("For input string " + s);
			}
			num += valOfChar(s.charAt(i)) * pow;
			pow *= radix;
		}
		return num;
	}
	
	/**
	 * Convert character to integer
	 *
	 * @param c the character
	 * @return represented digit of given character
	 * @throws NumberFormatException if {@code ch} is not UpperCase or Digit character.
	 */
	public static int valOfChar(char c) {
		if (!(Character.isUpperCase(c) || Character.isDigit(c))) {
			throw new NumberFormatException("invalid character :" + c);
		}
		return Character.isDigit(c) ? c - '0' : c - 'A' + 10;
	}
	

	public static int binToDec(int binNum) {
		int binCopy, d, s = 0, power = 0;
		binCopy = binNum;
		while (binCopy != 0) {
			d = binCopy % 10;
			s += d * (int) Math.pow(2, power++);
			binCopy /= 10;
		}
		return s;
	}
	
	/**
	 * This method converts a binary number to a hexadecimal number.
	 *
	 * @param binary The binary number
	 * @return The hexadecimal number
	 */
	public static String binToHex(int binary) {
		// hm to store hexadecimal codes for binary numbers within the range: 0000 to 1111 i.e. for
		// decimal numbers 0 to 15
		HashMap<Integer, String> hm = new HashMap<>();
		// String to store hexadecimal code
		String hex = "";
		int i;
		for (i = 0; i < 10; i++) {
			hm.put(i, String.valueOf(i));
		}
		for (i = 10; i < 16; i++) hm.put(i, String.valueOf((char) ('A' + i - 10)));
		int currbit;
		while (binary != 0) {
			int code4 = 0; // to store decimal equivalent of number formed by 4 decimal digits
			for (i = 0; i < 4; i++) {
				currbit = binary % 10;
				binary = binary / 10;
				code4 += currbit * Math.pow(2, i);
			}
			hex = hm.get(code4) + hex;
		}
		return hex;
	}
	
	/**
	 * This method converts a binary number to an octal number.
	 *
	 * @param binary The binary number
	 * @return The octal number
	 */
	public static String binToOct(int binary) {
		String octal = "";
		int currBit = 0, j = 1;
		while (binary != 0) {
			int code3 = 0;
			for (int i = 0; i < 3; i++) {
				currBit = binary % 10;
				binary = binary / 10;
				code3 += currBit * j;
				j *= 2;
			}
			octal = code3 + octal;
			j = 1;
		}
		return octal;
	}
	
	/**
	 * This method produces a String value of any given input decimal in any base
	 *
	 * @param inp Decimal of which we need the value in base in String format
	 * @return string format of the converted value in the given base
	 */
	public static String convertToAnyBase(int inp, int base) {
		ArrayList<Character> charArr = new ArrayList<>();
		
		while (inp > 0) {
			charArr.add(reVal(inp % base));
			inp /= base;
		}
		
		StringBuilder str = new StringBuilder(charArr.size());
		
		for (Character ch : charArr) {
			str.append(ch);
		}
		
		return str.reverse().toString();
	}
	
	/**
	 * This method produces character value of the input integer and returns it
	 *
	 * @param num integer of which we need the character value of
	 * @return character value of input integer
	 */
	public static char reVal(int num) {
		if (num >= 0 && num <= 9) return (char) (num + '0');
		else return (char) (num - 10 + 'A');
	}
	
	/** This method converts a decimal number to a binary number using a conventional algorithm. */
	public static int conventionalConversionDecToBin(int n) {
		int b = 0, c = 0, d;
		
		while (n != 0) {
			d = n % 2;
			b = b + d * (int) Math.pow(10, c++);
			n /= 2;
		} // converting decimal to binary
		return b;
	}

	/** This method converts a decimal number to a binary number using a bitwise algorithm */
	public static int bitwiseConversionDecToBin(int n) {
		int b = 0, c = 0, d;
		while (n != 0) {
			d = (n & 1);
			b += d * (int) Math.pow(10, c++);
			n >>= 1;
		}
		return b;
	}
	
	private final static int LONG_BITS = 8;

	public static String hexDecToBin(String numHex) {
		// String a HexaDecimal:
		int conHex = Integer.parseInt(numHex, 16);
		// Hex a Binary:
		String binary = Integer.toBinaryString(conHex);
		// Output:
		return completeDigits(binary);
	}

	private static String completeDigits(String binNum) {
		for (int i = binNum.length(); i < LONG_BITS; i++) {
			binNum = "0" + binNum;
		}
		return binNum;
	}
	
	/**
	 * This method converts a Hexadecimal number to a decimal number
	 *
	 * @param s The Hexadecimal Number
	 * @return The Decimal number
	 */
	public static int hexToDec(String s) {
		String str = "0123456789ABCDEF";
		s = s.toUpperCase();
		int val = 0;
		for (int i = 0; i < s.length(); i++) {
			char a = s.charAt(i);
			int n = str.indexOf(a);
			val = 16 * val + n;
		}
		return val;
	}
	
	/**
	 * This method converts a Decimal number to a octal number
	 *
	 * @param q The Decimal Number
	 * @return The Octal number
	 */
	public static int decToOct(int q) {
		int now;
		int i = 1;
		int octnum = 0;
		while (q > 0) {
			now = q % 8;
			octnum = (now * (int) (Math.pow(10, i))) + octnum;
			q /= 8;
			i++;
		}
		octnum /= 10;
		return octnum;
	}
	
	private static int[] allArabianRomanNumbers =
			new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
	private static String[] allRomanNumbers =
			new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

	// Value must be > 0

	public static String intToRoman(int num) {
		if (num <= 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		
		for (int a = 0; a < allArabianRomanNumbers.length; a++) {
			int times = num / allArabianRomanNumbers[a];
			for (int b = 0; b < times; b++) {
				builder.append(allRomanNumbers[a]);
			}
			
			num -= times * allArabianRomanNumbers[a];
		}

		return builder.toString();
	}
	
	/**
	 * This method converts a Octal number to a decimal number
	 *
	 * @param s The Octal Number
	 * @return The Decimal number
	 */
	public static int octToDec(String s) {
		int i = 0;
		for (int j = 0; j < s.length(); j++) {
			char num = s.charAt(j);
			num -= '0';
			i *= 8;
			i += num;
		}
		return i;
	}
	
	/**
	 * This method converts a Decimal number to a Hexadecimal number
	 *
	 * @param d The Decimal Number
	 * @return The Hexadecimal number
	 */
	public static String decToHex(int d) {
		String digits = "0123456789ABCDEF";
		if (d <= 0) return "0";
		String hex = "";
		while (d > 0) {
			int digit = d % 16;
			hex = digits.charAt(digit) + hex;
			d = d / 16;
		}
		return hex;
	}
	
	private static Map<Character, Integer> map =
			new HashMap<Character, Integer>() {
		/** */
		private static final long serialVersionUID = 87605733047260530L;

		{
			put('I', 1);
			put('V', 5);
			put('X', 10);
			put('L', 50);
			put('C', 100);
			put('D', 500);
			put('M', 1000);
		}
	};
	// Roman Number = Roman Numerals
	
	/**
	 * This function convert Roman number into Integer
	 *
	 * @param A Roman number string
	 * @return integer
	 */
	public static int romanToInt(String A) {
		
		A = A.toUpperCase();
		char prev = ' ';
		
		int sum = 0;
		
		int newPrev = 0;
		for (int i = A.length() - 1; i >= 0; i--) {
			char c = A.charAt(i);
			
			if (prev != ' ') {
				// checking current Number greater then previous or not
				newPrev = map.get(prev) > newPrev ? map.get(prev) : newPrev;
			}
			
			int currentNum = map.get(c);
			
			// if current number greater then prev max previous then add
			if (currentNum >= newPrev) {
				sum += currentNum;
			} else {
				// subtract upcoming number until upcoming number not greater then prev max
				sum -= currentNum;
			}
			
			prev = c;
		}
		
		return sum;
	}
}
