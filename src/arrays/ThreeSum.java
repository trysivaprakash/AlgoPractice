package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
 * Find all unique triplets in the array which gives the sum of zero. Note: The solution set must
 * not contain duplicate triplets. Example: Given array nums = [-1, 0, 1, 2, -1, -4],
 * <p>
 * A solution set is: [ [-1, 0, 1], [-1, -1, 2] ]
 */
public class ThreeSum {

  public static void main(String[] args) {
    ThreeSum o = new ThreeSum();
    List<List<Integer>> res = o.threeSum_faster(new int[]{0, 1, 2, -3, -4, -1, 3, 5});
    //printList(res);

    //res = o.threeSum_faster(new int[]{-2, 0, 0, 2, 2});
    res = o.threeSum_With_duplicates(new int[]{-2, 0, 0, 2, 2});
    res = o.threeSum_5ms(new int[]{-1,0,1,2,-1,-4});
    printList(res);
  }

  public List<List<Integer>> threeSum_own(int[] nums) {

    List<List<Integer>> res = new ArrayList<>();

    Arrays.sort(nums);

    for (int i = 0; i < nums.length - 3; i++) {
      if (i > 0 && nums[i] == nums[i-1]) {
        continue;
      }
      if (nums[i] == 0 && nums[i+2] == 0) {
        res.add(Arrays.asList(0,0,0));
        i = i+2;
        continue;
      }
      twoSum(nums, i+1, nums[i], res);
    }
    return res;
  }

  private void twoSum(int[] nums, int start, int first, List<List<Integer>> res) {

    for (int i = start; i < nums.length-2; i++) {
      if (i > start && nums[i] == nums[i-1]) {
        continue;
      }
      int rem = - first - nums[i];
      int last = Arrays.binarySearch(nums, i+1, nums.length, rem);
      if (last > 0) {
        res.add(Arrays.asList(first, nums[i], nums[last]));
      }
    }
  }

  public List<List<Integer>> threeSum_With_duplicates(int[] nums) {
    Set<List<Integer>> res = new HashSet<>();
    Set<Integer> dups = new HashSet<>();
    Map<Integer, Integer> seen = new HashMap<>();
    for (int i = 0; i < nums.length; ++i)
      if (dups.add(nums[i])) {
        for (int j = i + 1; j < nums.length; ++j) {
          int complement = -nums[i] - nums[j];
          if (seen.containsKey(complement) && seen.get(complement) == i) {
            List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
            Collections.sort(triplet);
            res.add(triplet);
          }
          seen.put(nums[j], i);
        }
      }
    return new ArrayList(res);
  }

  public List<List<Integer>> threeSum1(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums.length < 3) {
      return res;
    }
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++) {
      //Avoid duplicates
      if (i > 0 && nums[i] == nums[i-1]) {
        continue;
      }
      if (i+2 >= nums.length || nums[i] > 0) {
        break;
      }
      if (nums[i] == 0 && nums[i+2] == 0) {
        res.add(Arrays.asList(0, 0, 0));
        break;
      }
      twoSum(nums, i, res);
    }
    return res;
  }

  private void twoSum(int[] nums, int i, List<List<Integer>> res) {
    int target = 0 - nums[i];
    for (int j = i+1; j < nums.length; j++) {
      if (j > i+1 && nums[j] == nums[j-1]) {
        continue;
      }
      int bal = target - nums[j];
      int k = Arrays.binarySearch(nums, j+1, nums.length, bal);
      if (k > 0) {
        res.add(Arrays.asList(nums[i], nums[j], nums[k]));
      }
    }
  }

  public List<List<Integer>> threeSum_faster(int[] nums) {
    int len = nums.length;
    if (len < 1) {
      return new ArrayList<>();
    }
    // Sort input array 1st
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    // Allocate enough space to avoid check in if statement
    int max = Math.max(nums[len - 1], Math.abs(nums[0]));
    byte[] hash = new byte[(max << 1) + 1];
    // Hash and count appearing times of every num
    for (int v : nums) {
      hash[v + max]++;
    }
    // Search the position of 0.
    // It also represents the position of the last negative number in the array
    int lastNeg = Arrays.binarySearch(nums, 0);
    // The pos. of the 1st pos. number in the array
    int firstPos = lastNeg;
    // 0 not found
    if (lastNeg < 0) {
      firstPos = ~lastNeg;
      lastNeg = -lastNeg - 2;
      // see Java API
    } else {
      // found
      // skip all 0
      while (lastNeg >= 0 && nums[lastNeg] == 0) {
        --lastNeg;
      }
      while (firstPos < len && nums[firstPos] == 0) {
        ++firstPos;
      }
      int zeroCount = firstPos - lastNeg - 1;
      // 0 appears 3 times at least
      if (zeroCount >= 3) {
        res.add(Arrays.asList(0, 0, 0));
      }
      // 0 appears at least 1 time
      if (zeroCount > 0) {
        // traverse all the pos. numbers to see whether or not there's a neg. number whose abs. val.
        // equals the pos. number
        for (int i = firstPos; i < len; ++i) {
          // skip duplicate (same) elements
          if (i > firstPos && nums[i] == nums[i - 1]) {
            continue;
          }
          if (hash[-nums[i] + max] > 0) {
            res.add(Arrays.asList(0, nums[i], -nums[i]));
          }
        }
      }
    }
    // one positive number and two negetive numbers
    // traverse all the pos. numbers to find whether there are 2 neg. numbers to make the 3 numbers
    // add up to 0
    for (int i = firstPos; i < len; ++i) {
      // skip dups. (same elements)
      if (i > firstPos && nums[i] == nums[i - 1]) {
        continue;
      }
      // we can only traverse half of the pos. numbers
      int half;
      if (nums[i] % 2 != 0) {
        half = -((nums[i] >> 1) + 1);
      } else {
        half = -(nums[i] >> 1);
        if (hash[half + max] > 1) {
          res.add(Arrays.asList(nums[i], half, half));
        }
      }
      for (int j = lastNeg; j >= 0 && nums[j] > half; --j) {
        if (j < lastNeg && nums[j] == nums[j + 1]) {
          continue;
        }
        if (hash[(-nums[i] - nums[j]) + max] > 0) {
          res.add(Arrays.asList(nums[i], nums[j], -nums[i] - nums[j]));
        }
      }
    }
    // one negative number and two positive numbers
    // traverse all the negative numbers to find whether there are two positive numbers to make the
    // 3 numbers add up to 0
    for (int i = lastNeg; i >= 0; --i) {
      // skip dups. (same elements)
      if (i < lastNeg && nums[i] == nums[i + 1]) {
        continue;
      }
      // we can only traverse half of the negative numbers
      int half;
      if (nums[i] % 2 != 0) {
        half = -(nums[i] / 2 - 1);
      } else {
        half = -(nums[i] >> 1);
        if (hash[half + max] > 1) {
          res.add(Arrays.asList(nums[i], half, half));
        }
      }
      for (int j = firstPos; j < len && nums[j] < half; ++j) {
        if (j > firstPos && nums[j] == nums[j - 1]) {
          continue;
        }
        if (hash[(-nums[i] - nums[j]) + max] > 0) {
          res.add(Arrays.asList(nums[i], nums[j], -nums[i] - nums[j]));
        }
      }
    }
    return res;
  }

  public List<List<Integer>> threeSum_5ms(int[] nums) {
    int maxVal = Integer.MIN_VALUE;
    int minVal = Integer.MAX_VALUE;
    int negNums = 0;
    int posNums = 0;
    List<List<Integer>> result = new LinkedList<>();
    int zeroNums = 0;

    for (int num : nums) {
      if (num < minVal)  minVal = num;
      if (num > maxVal)  maxVal = num;
      if (num == 0)
        zeroNums++;
      else if (num > 0)
        posNums++;
      else
        negNums++;
    }


    if (zeroNums >= 3)  result.add(Arrays.asList(0, 0, 0));


    if (minVal >= 0 || maxVal <= 0)  return result;

    int[] negNumMap = new int[negNums];     // Array of all possible negative 3sum values.
    int[] posNumMap = new int[posNums];     // Array of all possible positive 3sum values.
    int posStart = 0;

    // If max or min values are too far from zero to use to make a 3sum,
    // then adjust max and/or min closer to zero.  This could eliminate
    // some of the outlying numbers that cannot be used to make a 3sum.
    // Those outlying numbers will be removed later when copying numbers
    // to the arrays of positives and negatives.
    if (maxVal + 2 * minVal > 0)  maxVal = -2 * minVal;
    if (minVal + 2 * maxVal < 0)  minVal = -2 * maxVal;

    // Scan through all of the numbers to build arrays of negative
    // numbers, positive numbers, and and array of counts of all
    // the numbers.
    byte[] numMap = new byte[maxVal - minVal + 1]; // Contains a count for each possible number
    // between the min and max values.  To see if
    // a number n exists in the passed array, just
    // check if numMap[n-minVal] is non-zero.  The
    // value in numMap[n-minVal] is the number of
    // occurrences of n in the original array.
    negNums = 0;
    posNums = 0;
    for (int num : nums) {                      // Loop through all numbers in passed array.
      if (num >= minVal && num <= maxVal) {   // Skip numbers that cannot possibly make a 3sum because
        // they are too large (too positive) or too small
        // (too negative).
        if (numMap[num - minVal]++ != 0) {  // Count an occurrence if this number.  If already
          numMap[num - minVal] = 2;       // seen this number, then set the count to 2, because
          // anything greater than 2 isn't any different than a
          // count of 2, and this lets the counts fit into a byte.
          // Because this number already seen, skip adding this
          // number to the positive or negative maps, thereby NOT
          // allowing duplicate numbers in the positive or
          // negative maps.
        } else {                            // Else we haven't seen this number yet, so unless zero,
          // add the number to the positive or negative map.
          if (num > 0) {
            posNumMap[posNums++] = num; // Add unique positive numbers to positive map.
          } else if (num < 0) {
            negNumMap[negNums++] = num; // Add unique negative numbers to negative map.
          }
        }
      }
    }

    // Sort the arrays of positive and negative numbers.  If arrays
    // are large, then .parallelSort() could be faster than .sort()
    Arrays.parallelSort(posNumMap, 0, posNums);
    Arrays.parallelSort(negNumMap, 0, negNums);

    // Loop through the negative numbers from highest negative number
    // (closest to zero) to lowest (farthest from zero; most negative).
    // By getting the negative numbers in this order (increasingly
    // negative), the 3sum will need to include a positive number with
    // the positive numbers being increasingly positive.
    for (int i = negNums - 1; i >= 0; i--) {
      int nv = negNumMap[i];                  // Get next neg number to try as first num of a 3sum.
      int minpv = (-nv) / 2;                  // Minimum positive value needed for the 3sum.  The
      // second 3sum value will be selected from the positive
      // numbers from half of the absolute value of the
      // negative number (first 3sum number), to a higher
      // positive number that would make the calculated third
      // 3sum number more negative than the first 3sum number.
      // This reduces the range of positive numbers to be tried
      // as the second 3sum number.

      while (posStart < posNums && posNumMap[posStart] < minpv)  posStart++;
      // Skip over any positive values that are below the
      // minimum positive value needed for the 3sum (minpv).
      // Since the negitive value nv will be increasingly
      // negative, the minimum positive value will be
      // increasingly positive, so start skipping positive
      // values that are below the minimum, starting at the
      // index (posStart) of the previous negative value
      // rather than scanning through all positive numbers
      // again.

      for (int j = posStart; j < posNums; j++) {  // Scan through possible pos values for this 3sum.
        int pv = posNumMap[j];              // Next possible highest positive value for this 3sum.
        // This could be the second value of the 3sum.
        int ln = 0 - nv - pv;               // Calculate the required third possible value for
        // this possible 3sum.

        if (ln >= nv && ln <= pv) {         // If the calculated third 3sum value is not between
          // the first and second 3sum values, then ignore this
          // 3sum combination.  This will eliminate duplicate 3sum
          // combinations, by having the first 3sum value being
          // the most negative number of the 3sum, and the second
          // 3sum value being the most positive number of the 3sum.
          if (numMap[ln - minVal] == 0) { // If the calculated third value for the 3sum does not
            continue;                   // exist in the passed array, this skip invalid 3sum.
          } else if (ln == pv || ln == nv) {  // If the calculated third 3sum value is the same as
            // first or second 3sum value, then this is only allowed
            // if the calculated number occurs more than once in the
            // original passed array.
            if (numMap[ln - minVal] > 1)  // If occurrence count of third number is more than once.
              result.add(Arrays.asList(nv, pv, ln));  // Valid 3sum with two numbers being the same.
          } else {
            result.add(Arrays.asList(nv, pv, ln));  // Valid 3sum with all numbers being different.
          }
        } else if (ln < nv) {               // If third possible 3sum value is below negative first
          break;                          // possible value for this 3sum, then we don't need to
          // test any higher positive values, because they would
          // only make the third value even more negative.  We don't
          // want the third value to be more negative than the first
          // 3sum value because this combination of 3sum numbers could
          // be tested later as i gets closer to zero, and skipping
          // this more negative third 3sum value now, will prevent
          // duplicate 3sum combinations.
        }
      }
    }

    return result;
  }

  private static void printList(List<List<Integer>> res) {
    for (List<Integer> list : res) {
      for (int i : list) {
        System.out.print(i + ",");
      }
      System.out.println();
    }
  }

  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums.length < 3) {
      return res;
    }

    List<Integer> subRes;
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      int j = i + 1;
      int k = nums.length - 1;
      while (j < k) {
        int sum = nums[i] + nums[j] + nums[k];
        if (sum == 0) {
          subRes = new ArrayList<>();
          subRes.add(nums[i]);
          subRes.add(nums[j]);
          subRes.add(nums[k]);
          res.add(subRes);
          j++;
          k--;
          while (j < k && nums[j] == nums[j - 1]) {
            j++;
          }
        } else if (sum > 0) {
          k--;
        } else {
          j++;
        }
      }
    }
    return res;
  }
}