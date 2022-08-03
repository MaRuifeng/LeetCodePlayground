package medium.P003LongestSubstrWithoutRepeats;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Longest substring without repeating characters.
 * <p>
 * Author: Ruifeng Ma
 * Date: 2022-Aug-03
 */

public class LongestSubstringWithoutRepeats {

    /**
     * Replace map with array to reduce memory footprint.
     * <p>
     * Summary of ideas
     * 1. Use an array of ASCII code table size to store unique occurrences of characters in the string.
     * 2. Use two pointers, head and tail to keep track of the starting and ending positions of the substring.
     * 3. While tail continues moving forward, head is only updated when a collision is found. Its value is updated to point to the position right after the first presence of the collision character, indicating the new start of the substring.
     * 4 The distances between head and tail is kept to their possible maximums and the largest of them is the result.
     */
    private static int lengthOfLongestSubstringV4( String s ) {
        int[] charPosArr = new int[256]; // size of extended ASCII code table for character representation is 256
        Arrays.fill(charPosArr, -1);

        char[] charArr = s.toCharArray();
        int maxLen = 0, head = 0, tail = 0;

        while (tail < charArr.length) {
            if (charPosArr[charArr[tail]] != -1 // collision found
                    && charPosArr[charArr[tail]] >= head // first presence of the collision character is within the substring
            ) {
                // reset head position of the substring right after the first presence of the collision character
                head = charPosArr[charArr[tail]] + 1;
            }
            charPosArr[charArr[tail]] = tail;
            maxLen = Math.max(maxLen, tail - head + 1);
            tail++;
        }

        return maxLen;
    }

    /**
     * Further improved greedy solution.
     * Instead of removing elements from the map, use two pointers to keep track of substring length.
     */
    private static int lengthOfLongestSubstringV3( String s ) {
        Map<Character, Integer> charMap = new HashMap<>();

        char[] charArr = s.toCharArray();
        int maxLen = 0, head = 0, tail = 0;

        while (tail < charArr.length) {
            if (charMap.containsKey(charArr[tail]) // collision found
                    && charMap.get(charArr[tail]) >= head // first presence of the collision character is within the substring
            ) {
                // reset head position of the substring right after the first presence of the collision character
                head = charMap.get(charArr[tail]) + 1;
            }
            charMap.put(charArr[tail], tail);
            maxLen = Math.max(maxLen, tail - head + 1);
            tail++;
        }

        return maxLen;
    }

    /**
     * Improved greedy solution.
     * Instead of re-instantiating the map and start counting all
     * over again, retain map storage and trim current substring accordingly. This
     * reduces repeated counting steps.
     */
    private static int lengthOfLongestSubstringV2( String s ) {
        Map<Character, Integer> charMap = new HashMap<>();

        char[] charArr = s.toCharArray();
        int maxLen = 0, start = 0;

        int i = 0;
        while (i < charArr.length) {
            if (charMap.containsKey(charArr[i])) {
                int alreadyMetAt = charMap.get(charArr[i]);
                // trim off from head of the substring till first position of the collision character
                for (int j = start; j <= alreadyMetAt; j++) charMap.remove(charArr[j]);
                // reset head position of the substring
                start = alreadyMetAt + 1;
            }
            charMap.put(charArr[i], i);
            i++;
            if (charMap.size() > maxLen) maxLen = charMap.size();
        }

        return maxLen;
    }

    /**
     * Greedy.
     * Traverse the array, keep a record of max length of the substring of unique characters
     * and store character positions into a map.
     * When a collision is found, it means the current substring reached an end, and
     * restart the traversal after the first presence of the collision character.
     */
    private static int lengthOfLongestSubstring( String s ) {
        Map<Character, Integer> charMap = new HashMap<>();

        char[] charArr = s.toCharArray();
        int maxLen = 0, curLen = 0;

        int i = 0;
        while (i < charArr.length) {
            if (charMap.get(charArr[i]) != null) {
                i = charMap.get(charArr[i]) + 1;
                curLen = 0;
                charMap = new HashMap<>();
            } else {
                charMap.put(charArr[i], i);
                curLen++;
                i++;
            }
            if (curLen > maxLen) maxLen = curLen;
        }

        return maxLen;
    }

    @FunctionalInterface
    private interface LongestSubstringLength {
        int apply( String s );
    }

    private static void execute( String s, LongestSubstringLength func, int expected ) {
        System.out.println(s);
        int actual = func.apply(s);

        assert ( actual == expected ) : "Failed: expected " + expected + ", but received " + actual;
        System.out.println("Result: " + actual);
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of longest substrings.");

        System.out.println("# Test case 1: ");
        execute("abcabcbb", LongestSubstringWithoutRepeats::lengthOfLongestSubstring, 3);
        execute("abcabcbb", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV2, 3);
        execute("abcabcbb", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV3, 3);
        execute("abcabcbb", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV4, 3);

        System.out.println("# Test case 2: ");
        execute("bbbbb", LongestSubstringWithoutRepeats::lengthOfLongestSubstring, 1);
        execute("bbbbb", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV2, 1);
        execute("bbbbb", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV3, 1);
        execute("bbbbb", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV4, 1);

        System.out.println("# Test case 3: ");
        execute("pwwkew", LongestSubstringWithoutRepeats::lengthOfLongestSubstring, 3);
        execute("pwwkew", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV2, 3);
        execute("pwwkew", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV3, 3);
        execute("pwwkew", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV4, 3);

        System.out.println("# Test case 4: ");
        execute(" ", LongestSubstringWithoutRepeats::lengthOfLongestSubstring, 1);
        execute(" ", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV2, 1);
        execute(" ", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV3, 1);
        execute(" ", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV4, 1);

        System.out.println("# Test case 5: ");
        execute("tmmzuxt", LongestSubstringWithoutRepeats::lengthOfLongestSubstring, 5);
        execute("tmmzuxt", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV2, 5);
        execute("tmmzuxt", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV3, 5);
        execute("tmmzuxt", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV4, 5);

        System.out.println("# Test case 6: ");
        execute("bbtablud", LongestSubstringWithoutRepeats::lengthOfLongestSubstring, 6);
        execute("bbtablud", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV2, 6);
        execute("bbtablud", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV3, 6);
        execute("bbtablud", LongestSubstringWithoutRepeats::lengthOfLongestSubstringV4, 6);

        System.out.println("All rabbits gone.");
    }
}
