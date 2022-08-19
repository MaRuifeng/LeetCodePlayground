package medium.P503NextGreaterElementII;

import java.util.Arrays;
import java.util.Stack;

/**
 * Next greater element II - circular array
 *
 * @author Ruifeng Ma
 * @since 2022-Aug-18
 */

public class NextGreaterElementII {

    /**
     * Use a stack to capture elements whose NGE is not yet found (i.e. they will be in descending order).
     * Peek at stack top and compare with next element in the array.
     * If found to be NGE, backtrack until the element is no longer an NGE.
     * <p>
     * In the case of a circular array, when the last element is reached, return to
     * the start of the array, and iterate once more without pushing to the stack.
     * <p>
     * When the second iteration is over, any remaining element in the stack
     * will not have any NGE.
     */
    private static class Element {
        int value;
        int pos;

        public Element( int v, int p ) {
            this.value = v;
            this.pos = p;
        }
    }

    private static int[] nextGreaterElementV2( int[] nums ) {
        Stack<Element> stack = new Stack<>();
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);

        int i = 0;
        while (i < nums.length * 2) {
            int idx = i % nums.length;
            while (!stack.isEmpty() && stack.peek().value < nums[idx]) {
                res[stack.peek().pos] = nums[idx];
                stack.pop();
            }
            if (i < nums.length) stack.push(new Element(nums[idx], idx));
            i++;
        }

        return res;
    }

    private static int[] nextGreaterElementV1( int[] nums ) {
        Stack<Element> stack = new Stack<>();
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);

        // Find NGE till the last element in the array
        int i = 0;
        while (i < nums.length) {
            while (!stack.isEmpty() && stack.peek().value < nums[i]) {
                res[stack.peek().pos] = nums[i];
                stack.pop();
            }
            stack.push(new Element(nums[i], i++));
        }

        // Find NGE for the last element in the array and other remaining elements in the stack
        i = 0;
        while (i < nums.length) {
            while (stack.peek().value < nums[i]) {
                res[stack.peek().pos] = nums[i];
                stack.pop();
            }
            i++;
        }
        return res;
    }

    @FunctionalInterface
    private interface NextGreaterElement {
        int[] apply( int[] a );
    }

    private static void execute( int[] a, NextGreaterElement func, int[] expected ) {
        System.out.println("Array 1: " + Arrays.toString(a));

        int[] actual = func.apply(a);

        assert ( Arrays.toString(actual).equals(Arrays.toString(expected)) )
                : "Expected " + Arrays.toString(expected) + ", but received " + Arrays.toString(actual);

        System.out.println("Result: " + Arrays.toString(actual));
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of next greater elements in circular arrays.");

        System.out.println("# Test case 1: ");
        execute(new int[]{1, 2, 1}, NextGreaterElementII::nextGreaterElementV1, new int[]{2, -1, 2});
        execute(new int[]{1, 2, 1}, NextGreaterElementII::nextGreaterElementV2, new int[]{2, -1, 2});

        System.out.println("# Test case 2: ");
        execute(new int[]{1, 2, 3, 4, 3}, NextGreaterElementII::nextGreaterElementV1, new int[]{2, 3, 4, -1, 4});
        execute(new int[]{1, 2, 3, 4, 3}, NextGreaterElementII::nextGreaterElementV2, new int[]{2, 3, 4, -1, 4});

        System.out.println("# Test case 3: ");
        execute(new int[]{1, 1, 1, 1, 1}, NextGreaterElementII::nextGreaterElementV1, new int[]{-1, -1, -1, -1, -1});
        execute(new int[]{1, 1, 1, 1, 1}, NextGreaterElementII::nextGreaterElementV2, new int[]{-1, -1, -1, -1, -1});

        System.out.println("# Test case 4: ");
        execute(new int[]{5, 4, 3, 2, 1}, NextGreaterElementII::nextGreaterElementV1, new int[]{-1, 5, 5, 5, 5});
        execute(new int[]{5, 4, 3, 2, 1}, NextGreaterElementII::nextGreaterElementV2, new int[]{-1, 5, 5, 5, 5});

        System.out.println("# Test case 5: ");
        execute(new int[]{1, 2, 3, 4, 5, 6, 5, 4, 5, 1, 2, 3}, NextGreaterElementII::nextGreaterElementV1, new int[]{2, 3, 4, 5, 6, -1, 6, 5, 6, 2, 3, 4});
        execute(new int[]{1, 2, 3, 4, 5, 6, 5, 4, 5, 1, 2, 3}, NextGreaterElementII::nextGreaterElementV2, new int[]{2, 3, 4, 5, 6, -1, 6, 5, 6, 2, 3, 4});

        System.out.println("All rabbits gone.");
    }
}
