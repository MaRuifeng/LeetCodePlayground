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
     * Solution 3. Keep an array of indices for words. Each index represents the position of a character
     * in that word that has been matched in the searching string. Loop through all characters of the searching string
     * and increment the indices accordingly. When an index reaches the end of the word, it means the word is a subsequence
     * of the searching string.
     */
    private static int numMatchingSubSeq(String string, String[] words) {
        // put all words into a map to reduce computation on duplicates
        Map<String, Integer> wordMap = new HashMap<>();
        for (String word : words) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }
        String[] uniqWords = wordMap.keySet().toArray(String[]::new);

        // each word index traverses the word for matched characters
        int[] wordIdxArr = new int[uniqWords.length];
        int count = 0;

        for (char c: string.toCharArray()) {
            boolean allWordsChecked = true;

            for (int i=0; i<wordIdxArr.length; i++) {
                if (wordIdxArr[i] >= uniqWords[i].length()) continue; // skip word that has been fully checked

                allWordsChecked = false;

                if (uniqWords[i].charAt(wordIdxArr[i]) == c) { // character matched in word
                    if (wordIdxArr[i] == uniqWords[i].length() - 1) count += wordMap.get(uniqWords[i]);
                    wordIdxArr[i]++;
                }
            }

            if (allWordsChecked) break;
        }

        return count;
    }

    /**
     * Solution 2. Index all characters in the string to a map, with key being the character, and value being
     * the list of positions where the character is found in the string. Loop through the words array
     * and check for matching of characters in each word.
     * Time complexity: linear
     */

    private static class Position {
        int index; // position in the string indicated by index number
        boolean matched; // matched by a char in word

        public Position( int index, boolean matched ) {
            this.index = index;
            this.matched = matched;
        }
    }

    private static int numMatchingSubSeqByMap( String string, String[] words ) {
        // index char positions into a map
        Map<Character, List<Position>> posMap = new HashMap<>();
        char[] charArr = string.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            Character c = charArr[i];
            List<Position> posList = posMap.get(c);
            if (posList == null) {
                posList = new ArrayList<>();
            }
            posList.add(new Position(i, false));
            posMap.put(c, posList);
        }

        // index word array to a map to handle duplicates
        Map<String, Integer> wordMap = new HashMap<>();
        for (String word : words) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }

        // loop through word map key set
        int count = 0;
        for (String word : wordMap.keySet()) {
            boolean wordMatched = true;
            int prevCharPosIdx = -1; // position in string of previous char

            // reset position list for each char
            for (List<Position> posList : posMap.values()) {
                posList.forEach(pos -> pos.matched = false);
            }

            for (Character c : word.toCharArray()) {
                List<Position> posList = posMap.get(c);

                if (posList == null) { // no such char found
                    wordMatched = false;
                    break;
                }

                // find first unmatched position of the char behind the position of the previous char
                int finalPrevCharPosIdx = prevCharPosIdx;
                Optional<Position> curCharPos = posList.stream().filter(pos -> !pos.matched && pos.index > finalPrevCharPosIdx).findFirst();
                if (curCharPos.isEmpty()) { // no such char position found
                    wordMatched = false;
                    break;
                }

                curCharPos.get().matched = true;
                prevCharPosIdx = curCharPos.get().index;
            }

            if (wordMatched) count += wordMap.get(word);
        }

        return count;
    }


    /**
     * Solution 1. Find all subsequences and loop through (brute force)
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
        System.out.println("*** Map method ***");
        execute("abcde", new String[]{"a", "bb", "acd", "ace"}, 3, NumberOfMatchingSubSequences::numMatchingSubSeqByMap);
        System.out.println("*** Indexing method ***");
        execute("abcde", new String[]{"a", "bb", "acd", "ace"}, 3, NumberOfMatchingSubSequences::numMatchingSubSeq);

        // Test Case 2
        System.out.println("# Test case 2:\n");
        System.out.println("*** Brute force method ***");
        execute("dsahjpjauf", new String[]{"ahjpjau", "ja", "ahbwzgqnuk", "tnmlanowax"}, 2, NumberOfMatchingSubSequences::numMatchingSubSeqBruteForce);
        System.out.println("*** Map method ***");
        execute("dsahjpjauf", new String[]{"ahjpjau", "ja", "ahbwzgqnuk", "tnmlanowax"}, 2, NumberOfMatchingSubSequences::numMatchingSubSeqByMap);
        System.out.println("*** Indexing method ");
        execute("dsahjpjauf", new String[]{"ahjpjau", "ja", "ahbwzgqnuk", "tnmlanowax"}, 2, NumberOfMatchingSubSequences::numMatchingSubSeq);

        System.out.println("All rabbits gone.");
    }

}
