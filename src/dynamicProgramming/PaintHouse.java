package dynamicProgramming;

/**
 * There are a row of n houses, each house can be painted with one of the three colors: red, blue or green.
 * The cost of painting each house with a certain color is different.
 * You have to paint all the houses such that no two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by a n x 3 cost matrix.
 * For example, costs[0][0] is the cost of painting house 0 with color red;
 * costs[1][2] is the cost of painting house 1 with color green, and so on...
 * Find the minimum cost to paint all houses.
 */
public class PaintHouse {

  public static void main(String[] args) {
    PaintHouse o = new PaintHouse();
    System.out.println(o.minCost(new int[][]{{1, 2, 3}, {4, 6, 2}, {3, 2, 1}}));
  }
  public int minCost(int[][] costs) {
    if (costs == null || costs.length == 0) {
      return 0;
    }
    for (int i = 1; i < costs.length; i++) {
      costs[i][0] += Math.min(costs[i-1][1], costs[i-1][2]);
      costs[i][1] += Math.min(costs[i-1][0], costs[i-1][2]);
      costs[i][2] += Math.min(costs[i-1][0], costs[i-1][1]);
    }
    int last = costs.length - 1;
    return Math.min(costs[last][0], Math.min(costs[last][1], costs[last][2]));
  }
}
