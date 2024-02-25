package easy.P013RomanToInteger;

import java.util.HashMap;
import java.util.Map;

/**
 * Convert Roman numeral string to integer.
 *
 * @author Ruifeng Ma
 * @since 2024-Feb-24
 */
public class RomanToInt {

    private static int romanToInt( String input ) {
        // initialize a map that contains seed number mapping
        Map<Character, Integer> seedMap = new HashMap<Character, Integer>();
        seedMap.put('I', 1);
        seedMap.put('V', 5);
        seedMap.put('X', 10);
        seedMap.put('L', 50);
        seedMap.put('C', 100);
        seedMap.put('D', 500);
        seedMap.put('M', 1000);

        // parse the input string from left to right and compute the final result
        int result = 0, i = 0;
        while (i < input.length()) {
            Character curr = input.charAt(i);
            Character next = i == input.length() - 1 ? null : input.charAt(i + 1);
            if (next != null && seedMap.get(curr) < seedMap.get(next)) {
                result += seedMap.get(next) - seedMap.get(curr);
                i += 2;
            } else {
                result += seedMap.get(curr);
                i++;
            }
        }

        return result;
    }

    @FunctionalInterface
    private interface ConvertRomanToInt {
        int apply( String input );
    }

    private static void execute( String romanStr, ConvertRomanToInt func, int expected ) {
        int actual = func.apply(romanStr);
        System.out.println("Parsed int: " + actual);
        assert ( actual == expected ) : "Expected " + expected + ", Received " + actual;
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of Roman integer converters.");

        System.out.println("# Test case 1: ");
        execute("III", RomanToInt::romanToInt, 3);

        System.out.println("# Test case 2: ");
        execute("IV", RomanToInt::romanToInt, 4);

        System.out.println("# Test case 3: ");
        execute("LVIII", RomanToInt::romanToInt, 58);

        System.out.println("# Test case 4: ");
        execute("MCMXCIV", RomanToInt::romanToInt, 1994);

        System.out.println("All rabbits gone.");
    }
}
