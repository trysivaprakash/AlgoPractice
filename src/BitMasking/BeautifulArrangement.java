package BitMasking;

/**
 * https://leetcode.com/problems/beautiful-arrangement/
 *
 * Suppose you have n integers labeled 1 through n. A permutation of those n integers perm (1-indexed) is considered a beautiful arrangement if for every i (1 <= i <= n), either of the following is true:
 *
 * perm[i] is divisible by i.
 * i is divisible by perm[i].
 * Given an integer n, return the number of the beautiful arrangements that you can construct.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 2
 * Output: 2
 * Explanation:
 * The first beautiful arrangement is [1,2]:
 *     - perm[1] = 1 is divisible by i = 1
 *     - perm[2] = 2 is divisible by i = 2
 * The second beautiful arrangement is [2,1]:
 *     - perm[1] = 2 is divisible by i = 1
 *     - i = 2 is divisible by perm[2] = 1
 * Example 2:
 *
 * Input: n = 1
 * Output: 1
 *
 *
 * Constraints:
 *
 * 1 <= n <= 15
 */
public class BeautifulArrangement {

    public static void main(String[] args) {
        BeautifulArrangement o = new BeautifulArrangement();
        System.out.println(o.countArrangement(3));
    }

    public int countArrangement(int n) {
        int mask = (1 << n)-1;
        int[] cache = new int[mask+1];
        int[] res = new int[1];
        return countArrangement(n, 1, mask, cache, res);
    }

    private int countArrangement(int n, int pos, int mask, int[] cache, int[] res) {

        if (cache[mask] != 0) {
            return cache[mask];
        }

        if (pos > n) {
            return 1;
        }

        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if ((mask & (1 << i-1)) != 0 && ((i % pos) == 0 || (pos % i) == 0)) {
                ans += countArrangement(n, pos+1, mask & ~(1 << i-1), cache, res);
            }
        }
        cache[mask] = ans;
        return ans;
    }

    int count = 0;
    public int countArrangement_backtracking(int N) {
        boolean[] visited = new boolean[N + 1];
        calculate(N, 1, visited);
        return count;
    }
    public void calculate(int N, int pos, boolean[] visited) {
        if (pos > N)
            count++;
        for (int i = 1; i <= N; i++) {
            if (!visited[i] && (pos % i == 0 || i % pos == 0)) {
                visited[i] = true;
                calculate(N, pos + 1, visited);
                visited[i] = false;
            }
        }
    }
}
