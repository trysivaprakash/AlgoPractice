package MathAndLogic;

/**
 * Author - Sivaprakash Nithyanandam
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

import java.util.Arrays;

/**
 * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent,
 * with the colors in the order red, white, and blue.
 *
 * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 * Example 2:
 *
 * Input: nums = [2,0,1]
 * Output: [0,1,2]
 * Example 3:
 *
 * Input: nums = [0]
 * Output: [0]
 * Example 4:
 *
 * Input: nums = [1]
 * Output: [1]
 *
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] is 0, 1, or 2.
 */
public class SortColors {

  public static void main(String[] args) {
    SortColors o = new SortColors();
    int[] nums = new int[]{0,2,0,2,1,1,1};
    o.sortColors(nums);
    System.out.println(Arrays.toString(nums));
  }

  public void sortColors(int[] nums) {
    int zeroPos = 0;
    int twoPos = nums.length - 1;
    int i = 0;
    while (i <= twoPos && zeroPos < twoPos) {
      if (nums[i] == 0) {
        nums[i] = nums[zeroPos];
        nums[zeroPos++] = 0;
        i++;
      } else if (nums[i] == 2) {
        nums[i] = nums[twoPos];
        nums[twoPos] = 2;
        twoPos--;
      } else {
        i++;
      }
    }
  }
}
