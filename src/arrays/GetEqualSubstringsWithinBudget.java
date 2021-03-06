package arrays;

/**
 * You are given two strings s and t of the same length. You want to change s to t.
 * Changing the i-th character of s to i-th character of t costs |s[i] - t[i]| that is,
 * the absolute difference between the ASCII values of the characters.
 *
 * You are also given an integer maxCost.
 *
 * Return the maximum length of a substring of s that can be changed to be the same as the
 * corresponding substring of twith a cost less than or equal to maxCost.
 *
 * If there is no substring from s that can be changed to its corresponding substring from t, return 0.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "abcd", t = "bcdf", cost = 3
 * Output: 3
 * Explanation: "abc" of s can change to "bcd". That costs 3, so the maximum length is 3.
 * Example 2:
 *
 * Input: s = "abcd", t = "cdef", cost = 3
 * Output: 1
 * Explanation: Each charactor in s costs 2 to change to charactor in t, so the maximum length is 1.
 * Example 3:
 *
 * Input: s = "abcd", t = "acde", cost = 0
 * Output: 1
 * Explanation: You can't make any change, so the maximum length is 1.
 *
 *
 * Constraints:
 *
 * 1 <= s.length, t.length <= 10^5
 * 0 <= maxCost <= 10^6
 * s and t only contain lower case English letters.
 */
public class GetEqualSubstringsWithinBudget {

    public static void main(String[] args) {
        GetEqualSubstringsWithinBudget o = new GetEqualSubstringsWithinBudget();
        System.out.println(o.equalSubstring("abcde", "dcdgf", 3));
        System.out.println(o.equalSubstring("a", "z", 3));
        System.out.println(o.equalSubstring("abcd", "bcdf", 3));
        System.out.println(o.equalSubstring("abcd", "cdef", 3));
        System.out.println(o.equalSubstring("abcd", "acde", 0));
        System.out.println(o.equalSubstring("krrgw", "zjxss", 19));//2
        System.out.println(o.equalSubstring("pxezla", "loewbi", 25));//4
        System.out.println(o.equalSubstring("krpgjbjjznpzdfy", "nxargkbydxmsgby", 14));//4
    }

    public int equalSubstring(String s, String t, int maxCost) {
        int maxCount = 0;
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        int[] arr = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            arr[i] = Math.abs(sArr[i] - tArr[i]);
        }
        int j = 0, cost = 0;
        for (int i = 0; i < arr.length; i++) {
            cost += arr[i];
            while (j <= i && cost > maxCost) {
                cost -= arr[j];
                j++;
            }
            maxCount = Math.max(maxCount, i - j + 1);
        }
        return maxCount;
    }
}
