# LeetCodePlayground

A playground to sharpen my programming and problem solving skills via [LeetCode](https://leetcode.com/).

## List of problems

### Easy
* [001. Two sum](src/main/java/easy/P001TwoSum/TwoSum.java)
* [004. Median of two sorted arrays](src/main/java/easy/P004MedianOfTwoSortedArray/MedianOfTwoSortedArray.java)
* [496. Next greater element I](src/main/java/easy/P496NextGreaterElementI/NextGreaterElementI.java)

### Medium
* [792. Number of matching Subsequences](src/main/java/medium/P792NumberOfMatchingSubSeqs/NumberOfMatchingSubSequences.java)
* [002. Add two numbers](src/main/java/medium/P002AddListNums/AddTwoNums.java)
* [003. Longest substring without repeats](src/main/java/medium/P003LongestSubstrWithoutRepeats/LongestSubstringWithoutRepeats.java)
* [005. Longest palindromic substring](src/main/java/medium/P005LongestPalindromicSubstring/LongestPalindromicSubstring.java)
* [006. Zigzag conversion](src/main/java/medium/P006ZigZagConversion/ZigzagConversion.java)
* [236. Lowest common ancestor of two nodes in a binary tree](src/main/java/medium/P236BinaryTreeLCA/BinaryTreeLCA.java)

## Dev notes

### Automatic code formatting
[formatter-maven-plugin](https://code.revelc.net/formatter-maven-plugin/) is used for automated code formatting.
* Apply formatting rules: `mvn formatter:format`
* Check formats: `mvn formatter:validate`

### Run programs with Java assertions enabled
Ideally actual exceptions should be thrown or JUnit tests should be used, instead of assertions.

How to enable Java assertions in IntelliJ IDE?
1. Choose Run → Edit Configurations...
2. Add `-ea` to the VM options box

## Contact

Author: Ruifeng Ma (mrfflyer@gmail.com)

Email: mrfflyer@gmail.com
