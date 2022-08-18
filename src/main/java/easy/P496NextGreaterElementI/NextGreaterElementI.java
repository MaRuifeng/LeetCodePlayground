package easy.P496NextGreaterElementI;

import java.util.Arrays;
import java.util.Stack;

/**
 * Next greater element - I
 *
 * @author Ruifeng Ma
 * @since 2022-Aug-17
 */

public class NextGreaterElementI {
    /**
     * Bit map & stack.
     * Time complexity: O(m + n)
     */
    private static int[] nextGreaterElementV2( int[] nums1, int[] nums2 ) {
        int[] ngeMap = new int[10000];
        Arrays.fill(ngeMap, -1);
        int[] res = new int[nums1.length];

        Stack<Integer> stack = new Stack<>(); // storing elements whose NGE is not yet found

        int i = 0;
        while (i < nums2.length) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) ngeMap[stack.pop()] = nums2[i];
            stack.push(nums2[i++]);
        }

        for (i = 0; i < nums1.length; i++) res[i] = ngeMap[nums1[i]];

        return res;
    }


    /**
     * Brute force.
     * Time complexity: O(m * n)
     */
    private static int[] nextGreaterElementV1( int[] nums1, int[] nums2 ) {
        int[] res = new int[nums1.length];
        Arrays.fill(res, -1);

        for (int i = 0; i < nums1.length; i++) {
            int j = 0;
            while (j < nums2.length && nums2[j] != nums1[i]) j++; // look for match
            while (j < nums2.length && nums2[j] <= nums1[i]) j++; // look for next greater element
            res[i] = j == nums2.length ? -1 : nums2[j];
        }

        return res;
    }

    @FunctionalInterface
    private interface NextGreaterElement {
        int[] apply( int[] a1, int[] a2 );
    }

    private static void execute( int[] nums1, int[] nums2, NextGreaterElement func, int[] expected ) {
        System.out.println("nums1 array: " + Arrays.toString(nums1));
        System.out.println("nums2 array: " + Arrays.toString(nums2));

        int[] actual = func.apply(nums1, nums2);
        assert ( Arrays.toString(actual).equals(Arrays.toString(expected)) )
                : "Expected " + Arrays.toString(expected) + ", but received " + Arrays.toString(actual);

        System.out.println("Result: " + Arrays.toString(actual));
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of next greater elements.");

        System.out.println("# Test case 1: ");
        execute(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}, NextGreaterElementI::nextGreaterElementV1, new int[]{-1, 3, -1});
        execute(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}, NextGreaterElementI::nextGreaterElementV2, new int[]{-1, 3, -1});

        System.out.println("# Test case 2: ");
        execute(new int[]{2, 4}, new int[]{1, 2, 3, 4}, NextGreaterElementI::nextGreaterElementV1, new int[]{3, -1});
        execute(new int[]{2, 4}, new int[]{1, 2, 3, 4}, NextGreaterElementI::nextGreaterElementV2, new int[]{3, -1});

        System.out.println("# Test case 3: ");
        execute(new int[]{2}, new int[]{2}, NextGreaterElementI::nextGreaterElementV1, new int[]{-1});
        execute(new int[]{2}, new int[]{2}, NextGreaterElementI::nextGreaterElementV2, new int[]{-1});

        System.out.println("# Test case 4: ");
        execute(new int[]{2}, new int[]{1, 2}, NextGreaterElementI::nextGreaterElementV1, new int[]{-1});
        execute(new int[]{2}, new int[]{1, 2}, NextGreaterElementI::nextGreaterElementV2, new int[]{-1});

        System.out.println("# Test case 5: ");
        execute(new int[]{2}, new int[]{2, 3}, NextGreaterElementI::nextGreaterElementV1, new int[]{3});
        execute(new int[]{2}, new int[]{2, 3}, NextGreaterElementI::nextGreaterElementV2, new int[]{3});

        System.out.println("All rabbits gone.");
    }
}
