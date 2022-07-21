package medium.P792NumberOfMatchingSubSeqs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Given a string s and an array of strings words, return the number of words[i] that is a
 * subsequence of s.
 * <p>
 * Author: Ruifeng Ma Date: 2022-Jul-19
 */

public class NumberOfMatchingSubSequences {
    /**
     * Time complexity: exponential due to the combinatorics nature
     */
    private static int numMatchingSubSeqBruteForce( String string, String[] words ) {
        // find all subsequences of the string
        List<String> subSeqList = getSubSeqs(string);

        // loop through the words array to find matches
        int count = 0;
        for (String word : words) {
            if (subSeqList.contains(word)) count++;
        }

        return count;
    }

    private static List<String> getSubSeqs( String string ) {
        if (string.length() == 1) return Collections.singletonList(string);

        // divide and conquer; split the string and then find subsequences of both separately
        List<String> left = getSubSeqs(string.substring(0, string.length() - 1));
        List<String> right = getSubSeqs(string.substring(string.length() - 1));

        // collect found subsequences
        List<String> subSeqList = Stream.of(left, right).flatMap(Collection::stream).collect(Collectors.toList());

        // additional subsequences are found via pairwise combination
        for (String l : left) {
            for (String r : right) {
                subSeqList.add(l.concat(r));
            }
        }
        return subSeqList;
    }

    @FunctionalInterface
    private interface NumMatchingSubSeq {
        int apply( String string, String[] words );
    }

    private static void execute( String string, String[] words, int expected, NumMatchingSubSeq func ) {
        System.out.println("String: " + string);
        System.out.println("Words: " + Arrays.toString(words));

        int actual = func.apply(string, words);
        assert ( actual == expected ) : "Failed: expected " + expected + ", but received " + actual;

        System.out.println(String.format(">>> Result: %d\n", actual));
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of matched string subsequences.\n");

        // Test Case 1
        System.out.println("# Test case 1:\n");
        System.out.println("*** Brute force method ***");
        execute("abcde", new String[]{"a", "bb", "acd", "ace"}, 3, NumberOfMatchingSubSequences::numMatchingSubSeqBruteForce);

        // Test Case 2
        System.out.println("# Test case 2:\n");
        System.out.println("*** Brute force method ***");
        execute("dsahjpjauf", new String[]{"ahjpjau","ja","ahbwzgqnuk","tnmlanowax"}, 2, NumberOfMatchingSubSequences::numMatchingSubSeqBruteForce);

        System.out.println("All rabbits gone.");
    }

}
