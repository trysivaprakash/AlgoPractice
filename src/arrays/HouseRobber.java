package arrays;

import java.util.Arrays;

/**
 * You are a professional robber planning to rob houses along a street. Each house has a certain
 * amount of money stashed, the only constraint stopping you from robbing each of them is that
 * adjacent houses have security system connected and it will automatically contact the police
 * if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house,
 * determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 *
 * Input: [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 *              Total amount you can rob = 1 + 3 = 4.
 * Example 2:
 *
 * Input: [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 *              Total amount you can rob = 2 + 9 + 1 = 12.
 */
public class HouseRobber {

    public static void main(String[] args) {
        HouseRobber o = new HouseRobber();
        System.out.println(o.rob1(new int[]{1,5,3,1}));
        System.out.println(o.rob(new int[]{1,2,3,1}));
        System.out.println(o.rob(new int[]{2,7,9,3,1}));
        System.out.println(o.rob(new int[]{2,1,1,2}));
        System.out.println(o.rob(new int[]{2,1,1,1,1,2}));
    }

    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length > 1) {
            nums[1] = Math.max(nums[0], nums[1]);
        }
        for (int i = 2; i < nums.length; i++) {
            nums[i] = Math.max(nums[i-1], nums[i] + nums[i-2]);
        }
        return nums[nums.length-1];
    }

    public int rob1(int[] nums) {
        if(nums.length==0){
            return 0;
        }

        int[] mem = new int[nums.length+1];
        Arrays.fill(mem, -1);

        mem[0] = 0;

        return helper(nums.length, mem, nums);
    }

    private int helper(int size, int[] mem, int[] nums){
        if(size <1){
            return 0;
        }

        if(mem[size]!=-1){
            return mem[size];
        }

        //two cases
        int firstSelected = helper(size-2, mem, nums) + nums[nums.length -size];
        int firstUnselected = helper(size-1, mem, nums);

        return mem[size] = Math.max(firstSelected, firstUnselected);
    }
}
