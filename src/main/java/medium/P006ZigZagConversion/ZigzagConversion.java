package medium.P006ZigZagConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Zigzag string conversion.
 *
 * @author Ruifeng Ma
 * @since 2022-Aug-11
 */

public class ZigzagConversion {
    /**
     * Upon a deeper analysis, we can find that the zigzag shape of the string can be represented by
     * alternating string tokens, matching vertical columns and diagonals.
     * Typically diagonals have a length less than that of the columns by 2.
     */
    private static String convertV2( String s, int numRows ) {
        List<String> tokenList = new ArrayList<>();

        int alt = 1; // alternator between column and diagonal
        int start = 0, end;

        while (start < s.length()) {
            if (alt == 1) { // column
                end = Math.min(numRows, s.length() - start) + start;
                tokenList.add(s.substring(start, end));
            } else { // diagonal
                end = Math.min(Math.max(0, numRows - 2), s.length() - start) + start;
                if (start < end) {
                    String str = s.substring(start, end);
                    tokenList.add(" ".repeat(numRows - str.length() - 1) + new StringBuilder(str).reverse().toString() + ' ');
                }
            }

            start = end;
            alt ^= 1;
        }

//        for (String token : tokenList) {
//            System.out.println(token);
//        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            for (String token : tokenList) {
                if (token.length() > i && token.charAt(i) != ' ') sb.append(token.charAt(i));
            }
        }

        return sb.toString();
    }

    /**
     * Brute force.
     * Use a 2D array to represent the zigzag shape of the string.
     * Traverse the string, put each character into the 2D array following the
     * zigzag path. For table arr[i][j],
     * - when i == 0, fill up the vertical column with j unchanged, and i incremented from 0 to num of rows,
     * - when i == num of rows, fill up the diagonal path with j++ and i--, with i decremented till 0.
     * - continue until end of the string is reached.
     * <p>
     * Space complexity is high because of empty spots in the 2D array.
     */
    private static String convertV1( String s, int numRows ) {
        // a bit of maths is done to get required number of columns
        int numColumns = numRows > 1 ? (int) Math.ceil((double) s.length() / 2) : s.length();

        char[][] table = new char[numRows][numColumns];

        int i = 0, j = 0, k = 0;
        while (k < s.length()) {
            // vertical
            while (i < numRows && k < s.length()) table[i++][j] = s.charAt(k++);
            j++;

            // diagonal
            i = Math.max(0, i - 2);
            while (i > 0 && k < s.length()) table[i--][j++] = s.charAt(k++);
        }

//        for (i = 0; i < numRows; i++) {
//            for (j = 0; j < numColumns; j++) {
//                System.out.print(table[i][j] + " ");
//            }
//            System.out.println();
//        }

        StringBuilder sb = new StringBuilder();
        for (i = 0; i < numRows; i++) {
            for (j = 0; j < numColumns; j++) {
                if ((int) table[i][j] != 0) sb.append(table[i][j]);
            }
        }

        return sb.toString();
    }

    @FunctionalInterface
    private interface ZigzagConvert {
        String apply( String s, int numRows );
    }

    private static void execute( String s, int numRows, ZigzagConvert func, String expected ) {
        System.out.println("Given string: " + s);
        System.out.println("Given number of rows: " + numRows);

        String actual = func.apply(s, numRows);
        assert ( actual.equals(expected) ) : "Failed: expected " + expected + ", received " + actual;
        System.out.println("Result: " + actual);
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of zigzag strings.");

        System.out.println("# Test case 1: ");
        execute("PAYPALISHIRING", 3, ZigzagConversion::convertV1, "PAHNAPLSIIGYIR");
        execute("PAYPALISHIRING", 3, ZigzagConversion::convertV2, "PAHNAPLSIIGYIR");

        System.out.println("# Test case 2: ");
        execute("PAYPALISHIRING", 4, ZigzagConversion::convertV1, "PINALSIGYAHRPI");
        execute("PAYPALISHIRING", 4, ZigzagConversion::convertV2, "PINALSIGYAHRPI");

        System.out.println("# Test case 3: ");
        execute("A", 1, ZigzagConversion::convertV1, "A");
        execute("A", 1, ZigzagConversion::convertV2, "A");

        System.out.println("# Test case 4: ");
        execute("AB", 1, ZigzagConversion::convertV1, "AB");
        execute("AB", 1, ZigzagConversion::convertV2, "AB");

        System.out.println("# Test case 5: ");
        execute("A", 2, ZigzagConversion::convertV1, "A");
        execute("A", 2, ZigzagConversion::convertV2, "A");

        System.out.println("# Test case 6: ");
        execute("ABC", 2, ZigzagConversion::convertV1, "ACB");
        execute("ABC", 2, ZigzagConversion::convertV2, "ACB");

        System.out.println("# Test case 7: ");
        execute("ABCDE", 2, ZigzagConversion::convertV1, "ACEBD");
        execute("ABCDE", 2, ZigzagConversion::convertV2, "ACEBD");

        System.out.println("# Test case 8: ");
        execute("pqwuzifwovyddwyvvbu", 10, ZigzagConversion::convertV1, "puqbwvuvzyiwfdwdoyv");
        execute("pqwuzifwovyddwyvvbu", 10, ZigzagConversion::convertV2, "puqbwvuvzyiwfdwdoyv");

        System.out.println("# Test case 9: ");
        execute("ABCDE", 4, ZigzagConversion::convertV1, "ABCED");
        execute("ABCDE", 4, ZigzagConversion::convertV2, "ABCED");

        System.out.println("All rabbits gone.");
    }
}
