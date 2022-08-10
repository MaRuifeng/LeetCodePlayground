package easy.P004MedianOfTwoSortedArray;

import java.util.Arrays;

/**
 * Median of 2 sorted arrays.
 *
 * @author Ruifeng Ma
 * @since 2022-Aug-07
 */

public class MedianOfTwoSortedArray {

    /**
     * Following the idea of MergeSort, merge two arrays into a single and then look for the median.
     * Time complexity: O(m+n)
     */
    private static double findMedianSortedArrays( int[] nums1, int[] nums2 ) {
        int i = 0, j = 0, k = 0;
        int totalLen = nums1.length + nums2.length;
        int[] merged = new int[totalLen];

        while (k < totalLen) {
            if (i >= nums1.length) {
                merged[k++] = nums2[j++];
                continue;
            }
            if (j >= nums2.length) {
                merged[k++] = nums1[i++];
                continue;
            }
            if (nums1[i] < nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }

        if (totalLen % 2 == 0) { // even
            return ( merged[totalLen / 2] + merged[totalLen / 2 - 1] ) / (double) 2;
        } else { // odd
            return merged[totalLen / 2];
        }
    }

    @FunctionalInterface
    private interface MedianOfSortedArrays {
        double apply( int[] a1, int[] a2 );
    }

    private static void execute( int[] a1, int[] a2, MedianOfSortedArrays func, double expected ) {
        System.out.println("Array 1:" + Arrays.toString(a1));
        System.out.println("Array 2:" + Arrays.toString(a1));

        double actual = func.apply(a1, a2);
        double epsilon = 0.000001d;
        assert ( ( Math.abs(actual - expected) < epsilon ) ) : "Failed: expected " + expected + ", but received " + actual;

        System.out.println("Result: " + actual);
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of median of two sorted arrays");

        System.out.println("# Test case 1: ");
        System.out.println("O(n) method");
        execute(new int[]{1, 3}, new int[]{2}, MedianOfTwoSortedArray::findMedianSortedArrays, 2.00000d);

        System.out.println("# Test case 2: ");
        System.out.println("O(n) method");
        execute(new int[]{1, 2}, new int[]{3, 4}, MedianOfTwoSortedArray::findMedianSortedArrays, 2.50000d);

        System.out.println("All rabbits gone.");
    }
}
