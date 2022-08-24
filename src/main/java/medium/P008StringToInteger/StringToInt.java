package medium.P008StringToInteger;

/**
 * Function that converts a string to an integer (atoi in C/C++).
 * Problem URL: https://leetcode.com/problems/string-to-integer-atoi/
 *
 * @author Ruifeng Ma
 * @since 2022-Aug-23
 */

public class StringToInt {
    /**
     * V1 is crap. Spaghetti code with lots of flags.
     * Let's do it with proper modularization.
     */
    private static int myAtoiV2( String s ) {
        return parseInt(getSignedIntString(s));
    }

    // examples of signed int string: +234, -234
    private static String getSignedIntString( String s ) {
        int i = 0;
        StringBuilder sb = new StringBuilder();

        // skip leading empty spaces
        while (i < s.length() && s.charAt(i) == ' ') i++;

        // read sign
        if (i < s.length() && ( s.charAt(i) == '+' || s.charAt(i) == '-' )) sb.append(s.charAt(i++));
        else sb.append('+');

        // skip leading zeros
        while (i < s.length() && s.charAt(i) == '0') i++;

        // read digits
        while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') sb.append(s.charAt(i++));

        return sb.toString();
    }

    private static int parseInt( String signedStr ) { // s consists of number digits only
        int i = signedStr.length() - 1;
        int sign = signedStr.charAt(0) == '-' ? -1 : 1;
        int exponent = 1;
        int res = 0;

        while (i > 0) {
            int digit = signedStr.charAt(i) - '0';
            if (exponent == 1000000000 && ( i > 1 || digit > 2 )) {
                // max exponent reached, but more than one digit still exist, all last digit is larger than 2
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            int addend = exponent * digit;
            if (Integer.MAX_VALUE - addend < res) { // max result value reached
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            res += addend;
            exponent *= 10;
            i--;
        }

        return sign * res;
    }

    /**
     * Traverse through the char array and convert to integer by
     * performing arithmetic operations.
     */
    private static int myAtoiV1( String s ) {
        int i = 0;
        int sign = 1; // default to be positive
        int numLen = 0; // length of number
        boolean leading = true;

        while (i < s.length()) {
            if (leading && s.charAt(i) == ' ') {
                i++;
                continue;
            }
            if (leading && s.charAt(i) == '-') {
                sign = -1;
                leading = false;
                i++;
                continue;
            }
            if (leading && s.charAt(i) == '+') {
                leading = false;
                i++;
                continue;
            }

            leading = false;

            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                break;
            }
            numLen++;
            i++;
        }

        // now i is the end position of number (exclusive)

        // remove leading zeros
        int k = i - numLen;
        while (k < i && s.charAt(k++) == '0') numLen--;

        int j = i - 1;
        int res = 0, exponent = 1;
        while (numLen > 0) {
            int lsd = s.charAt(j) - '0'; // least significant digit
            if (exponent == 1000000000 && ( numLen > 1 || lsd > 2 )) {
                res = sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                break;
            }
            int addend = lsd * exponent;
            if (Integer.MAX_VALUE - addend < res) {
                res = sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                break;
            }

            res += addend;
            exponent *= 10;
            numLen--;
            j--;
        }

        return res * sign;
    }

    @FunctionalInterface
    private interface StringToInteger {
        int apply( String s );
    }

    private static void execute( String s, StringToInteger func, int expected ) {
        System.out.println("Given string: " + s);
        int actual = func.apply(s);

        assert ( actual == expected ) : "Expected " + expected + ", but received " + actual;
        System.out.println("Result: " + actual);
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of atoi functions.");

        System.out.println("# Test case 1: ");
        execute("42", StringToInt::myAtoiV1, 42);
        execute("42", StringToInt::myAtoiV2, 42);

        System.out.println("# Test case 2: ");
        execute("      -42", StringToInt::myAtoiV1, -42);
        execute("      -42", StringToInt::myAtoiV2, -42);

        System.out.println("# Test case 3: ");
        execute("  4193 with words", StringToInt::myAtoiV1, 4193);
        execute("  4193 with words", StringToInt::myAtoiV2, 4193);

        System.out.println("# Test case 4: ");
        execute(String.valueOf(Integer.MAX_VALUE), StringToInt::myAtoiV1, Integer.MAX_VALUE);
        execute(String.valueOf(Integer.MAX_VALUE), StringToInt::myAtoiV2, Integer.MAX_VALUE);

        System.out.println("# Test case 5: ");
        execute("-" + Integer.MAX_VALUE, StringToInt::myAtoiV1, -Integer.MAX_VALUE);
        execute("-" + Integer.MAX_VALUE, StringToInt::myAtoiV2, -Integer.MAX_VALUE);

        System.out.println("# Test case 6: ");
        execute("2147483648", StringToInt::myAtoiV1, Integer.MAX_VALUE);
        execute("2147483648", StringToInt::myAtoiV2, Integer.MAX_VALUE);

        System.out.println("# Test case 7: ");
        execute(Integer.MAX_VALUE + "8", StringToInt::myAtoiV1, Integer.MAX_VALUE);
        execute(Integer.MAX_VALUE + "8", StringToInt::myAtoiV2, Integer.MAX_VALUE);

        System.out.println("# Test case 8: ");
        execute(String.valueOf(Integer.MIN_VALUE), StringToInt::myAtoiV1, Integer.MIN_VALUE);
        execute(String.valueOf(Integer.MIN_VALUE), StringToInt::myAtoiV2, Integer.MIN_VALUE);

        System.out.println("# Test case 9: ");
        execute("-2147483649", StringToInt::myAtoiV1, Integer.MIN_VALUE);
        execute("-2147483649", StringToInt::myAtoiV2, Integer.MIN_VALUE);

        System.out.println("# Test case 10: ");
        execute(Integer.MIN_VALUE + "0", StringToInt::myAtoiV1, Integer.MIN_VALUE);
        execute(Integer.MIN_VALUE + "0", StringToInt::myAtoiV2, Integer.MIN_VALUE);

        System.out.println("# Test case 11: ");
        execute("-", StringToInt::myAtoiV1, 0);
        execute("-", StringToInt::myAtoiV2, 0);

        System.out.println("# Test case 12: ");
        execute("", StringToInt::myAtoiV1, 0);
        execute("", StringToInt::myAtoiV2, 0);

        System.out.println("# Test case 13: ");
        execute(",no.number +.-", StringToInt::myAtoiV1, 0);
        execute(",no.number +.-", StringToInt::myAtoiV2, 0);

        System.out.println("# Test case 14: ");
        execute("+1", StringToInt::myAtoiV1, 1);
        execute("+1", StringToInt::myAtoiV2, 1);

        System.out.println("# Test case 15: ");
        execute("+-12", StringToInt::myAtoiV1, 0);
        execute("+-12", StringToInt::myAtoiV2, 0);

        System.out.println("# Test case 16: ");
        execute("01147483648", StringToInt::myAtoiV1, 1147483648);
        execute("01147483648", StringToInt::myAtoiV2, 1147483648);

        System.out.println("# Test case 17: ");
        execute("  0000000000012345678", StringToInt::myAtoiV1, 12345678);
        execute("  0000000000012345678", StringToInt::myAtoiV2, 12345678);

        System.out.println("# Test case 18: ");
        execute("00000-42a1234", StringToInt::myAtoiV1, 0);
        execute("00000-42a1234", StringToInt::myAtoiV2, 0);

        System.out.println("# Test case 19: ");
        execute("-6147483648", StringToInt::myAtoiV1, Integer.MIN_VALUE);
        execute("-6147483648", StringToInt::myAtoiV2, Integer.MIN_VALUE);

        System.out.println("All rabbits gone.");
    }
}
