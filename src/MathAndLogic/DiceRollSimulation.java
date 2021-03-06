package MathAndLogic;

/**
 * A die simulator generates a random number from 1 to 6 for each roll.
 * You introduced a constraint to the generator such that it cannot roll the number i more than
 * rollMax[i] (1-indexed) consecutive times.
 *
 * Given an array of integers rollMax and an integer n, return the number of distinct sequences that can be obtained with exact n rolls.
 *
 * Two sequences are considered different if at least one element differs from each other.
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 2, rollMax = [1,1,2,2,2,3]
 * Output: 34
 * Explanation: There will be 2 rolls of die, if there are no constraints on the die,
 * there are 6 * 6 = 36 possible combinations. In this case, looking at rollMax array,
 * the numbers 1 and 2 appear at most once consecutively,
 * therefore sequences (1,1) and (2,2) cannot occur, so the final answer is 36-2 = 34.
 * Example 2:
 *
 * Input: n = 2, rollMax = [1,1,1,1,1,1]
 * Output: 30
 * Example 3:
 *
 * Input: n = 3, rollMax = [1,1,1,2,2,3]
 * Output: 181
 *
 *
 * Constraints:
 *
 * 1 <= n <= 5000
 * rollMax.length == 6
 * 1 <= rollMax[i] <= 15
 */

/**
 * https://leetcode.com/problems/dice-roll-simulation/discuss/403756/Java-Share-my-DP-solution
 * https://leetcode.com/problems/dice-roll-simulation/discuss/403964/Java-two-method-DP-solutions-and-DFS-solution
 */
//TODO Revisit
public class DiceRollSimulation {
    public static void main(String[] args) {
        DiceRollSimulation o = new DiceRollSimulation();
        System.out.println(o.dieSimulatorFaster(2, new int[]{3,1,1,1,1,3}));
        System.out.println(o.dieSimulatorFaster(3, new int[]{1,1,1,1,1,1}));
        System.out.println(o.dieSimulatorFaster(20, new int[]{8,5,10,8,7,2}));
    }

    private static int dieSimulatorFaster(int n, int[] rollMax) {
        int mod = (int)1e9 + 7;
        //dp[i][j] represents the number of distinct sequences that can be obtained when rolling
        // i times and ending with j
        //The one more row represents the total sequences when rolling i times
        int[][] dp = new int[n + 1][7];
        //init for the first roll
        for (int i = 0; i < 6; i++) {
            dp[1][i] = 1;
        }
        dp[1][6] = 6;
        for (int i = 2; i <= n; i++) {
            int total = 0;
            for (int j = 0; j < 6; j++) {
                //If there are no constraints, the total sequences ending with j should be the total
                // sequences from preious rolling
                dp[i][j] = dp[i - 1][6];
                //For xx1, only 111 is not allowed, so we only need to remove 1 sequence from previous sum
                if (i - rollMax[j] == 1) {
                    dp[i][j]--;
                }
                //For axx1, we need to remove the number of a11 (211 + 311 + 411 + 511 + 611) =>
                // (..2 + ..3 + ..4 + ..5 + ..6) => (sum - ..1)
                if (i - rollMax[j] >= 2) {
                    int reduciton = dp[i - rollMax[j] - 1][6] - dp[i - rollMax[j] - 1][j];
                    //must add one more mod because subtraction may introduce negative numbers
                    dp[i][j] = ((dp[i][j] - reduciton) % mod + mod) % mod;
                }
                total = (total + dp[i][j]) % mod;
            }
            dp[i][6] = total;
        }
        return dp[n][6];
    }

    private int dieSimulator(int n, int[] rollMax) {
        int[][][] dp = new int[n+1][7][16];
        for (int i = 1; i < 7; i++) {
            dp[1][i][1] = 1;
        }
        for (int x = 2; x <= n; x++) {
            for (int i = 1; i < 7; i++) {
                for (int k = 1; k < rollMax[i-1]; k++) {
                    dp[x][i][k+1] += dp[x-1][i][k];
                }
                for (int j = 1; j < 7; j++) {
                    if (i == j) continue;
                    for (int k = 1; k <= rollMax[i-1]; k++) {
                        dp[x][j][1] += dp[x-1][i][k];
                    }
                }
            }
        }
        int res = 0;
        for (int a = 1; a < 7; a++) {
            for (int b = 1; b < 16; b++) {
                System.out.println(a+"-"+b+"-"+dp[n][a][b]);
                res += dp[n][a][b];
            }
        }
        return res;
    }

    private static final int MOD = 1000000007;
    public int dieSimulator2(int n, int[] rollMax) {
        if (n == 0) return 0;
        int[][][] dp = new int[n+1][7][16];
        // initialization
        for (int i = 1; i <= 6; ++i) {
            if (rollMax[i-1] > 0) {
                dp[1][i][1] = 1;
            }
        }
        for (int x = 2; x <= n; ++x) {
            for (int i = 1; i <= 6; ++i) {
                // if continue to add tail i, make sure consecutive num of i would less than or equal to rollMax[i-1]
                for (int k = 1; k < rollMax[i-1]; ++k) {
                    dp[x][i][k+1] = (dp[x][i][k+1]+dp[x-1][i][k])%MOD;
                }
                // if add new tail j that differs from i, so we should jump out i
                for (int j = 1; j <= 6; ++j) {
                    if (j == i) continue;
                    for (int k = 1; k <= rollMax[i-1]; ++k) {
                        dp[x][j][1] = (dp[x][j][1]+dp[x-1][i][k])%MOD;
                    }
                }
            }
        }
        int num = 0;
        for (int i = 1; i <= 6; ++i) {
            for (int k = 1; k <= 15; ++k) {
                num = (num + dp[n][i][k]) % MOD;
            }
        }
        return num;
    }

    public int dieSimulator1(int n, int[] rollMax) {
        int mod = 1000000007;
        long[][] dp = new long[6][16];
        for(int i = 0;i < 6;i++){
            dp[i][1] = 1;
        }
        for(int i = 2;i <= n;i++){
            long[][] ndp = new long[6][16];
            for(int j = 0;j < 6;j++){
                for(int k = 0;k < 16;k++){
                    for(int l = 0;l < 6;l++){
                        if(j == l){
                            if(k+1 <= rollMax[l]){
                                ndp[l][k+1] += dp[j][k];
                                ndp[l][k+1] %= mod;
                            }
                        }else{
                            ndp[l][1] += dp[j][k];
                            ndp[l][1] %= mod;
                        }
                    }
                }
            }
            dp = ndp;
        }

        long ret = 0;
        for(int i = 0;i < 6;i++){
            for(int j = 0;j < 16;j++) {
                ret += dp[i][j];
            }
        }
        return (int)(ret%mod);
    }
}
