package easy.P009PalindromeNumber;

import java.util.Arrays;
import java.util.Stack;

/**
 * Palindrome number.
 *
 * @author Ruifeng Ma
 * @since 2024-Feb-18
 */
public class PalindromeNumber {
    /**
     * Convert number into a char array and use a stack to check if the array is palindromic.
     */
    private static boolean isNumPalindromeV1( int num ) {
        char[] charArr = String.valueOf(num).toCharArray();

        Stack<Character> stack = new Stack<>();
        int mid = charArr.length / 2;
        for (int i = 0; i < charArr.length; i++) {
            if (i < mid) {
                stack.push(charArr[i]);
            } else {
                if (i == mid && charArr.length % 2 != 0)
                    continue; // skip adding the middle char of array with odd number size
                if (!stack.isEmpty() && stack.peek().equals(charArr[i]))
                    stack.pop();
            }
        }

        return stack.isEmpty();
    }

    /**
     * Convert number into a char array and check by index with early termination
     */
    private static boolean isNumPalindromeV2( int num ) {
        char[] charArr = String.valueOf(num).toCharArray();
        int left = charArr.length / 2 - 1;
        int right = ( charArr.length + 1 ) / 2;

        while (left > -1 && right < charArr.length) {
            if (charArr[left--] != charArr[right++]) return false;
        }

        return true;
    }

    @FunctionalInterface
    private interface IsNumberPalindromic {
        boolean apply( int number );
    }

    private static void execute( int number, IsNumberPalindromic func, boolean expected ) {
        System.out.println("Is " + number + " palindromic?");
        boolean actual = func.apply(number);
        System.out.println(actual ? "Yes" : "No");
        assert ( actual == expected ) : "Expected " + expected + ", but received " + actual;
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of palindromic numbers!");

        System.out.println("# Test case 1: ");
        execute(1, PalindromeNumber::isNumPalindromeV1, true);
        execute(1, PalindromeNumber::isNumPalindromeV2, true);

        System.out.println("# Test case 2: ");
        execute(121, PalindromeNumber::isNumPalindromeV1, true);
        execute(121, PalindromeNumber::isNumPalindromeV2, true);

        System.out.println("# Test case 3: ");
        execute(2233, PalindromeNumber::isNumPalindromeV1, false);
        execute(2233, PalindromeNumber::isNumPalindromeV2, false);

        System.out.println("# Test case 4: ");
        execute(-121, PalindromeNumber::isNumPalindromeV1, false);
        execute(-121, PalindromeNumber::isNumPalindromeV2, false);

        System.out.println("# Test case 5: ");
        execute(10, PalindromeNumber::isNumPalindromeV1, false);
        execute(10, PalindromeNumber::isNumPalindromeV2, false);

        System.out.println("All rabbits gone.");
    }
}
