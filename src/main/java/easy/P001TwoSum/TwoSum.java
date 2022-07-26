package easy.P001TwoSum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Two sum problem.
 * <p>
 * Author: Ruifeng Ma
 * Date: 2022-Jul-26
 */

public class TwoSum {
    /**
     * Load numbers into a hash and look for diffs from the same hash. If found, a two sum pair exists.
     */
    private static int[] twoSum( int[] nums, int target ) {
        Map<Integer, Integer> numIdxMap = new HashMap<>();

        int[] res = new int[2];

        for (int i=0; i<nums.length; i++) {
            // note querying first avoids the need to handle the special case where a number is just half of target
            if (numIdxMap.containsKey(target - nums[i])) {
                res[0] = i;
                res[1] = numIdxMap.get(target - nums[i]);
                break;
            }

            numIdxMap.put(nums[i], i);
        }

        return res;
    }

    public static void main( String[] args ) {
        System.out.println("Welcome to the rabbit hole of two sums.\n");

        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;

        System.out.println("Number array: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        System.out.println("Result: " + Arrays.toString(twoSum(nums, target)));

        System.out.println("All rabbits gone.");
    }
}
