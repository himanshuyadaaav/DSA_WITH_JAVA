class Solution {
    public int climbStairs(int n, int[] costs) {
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            dp[i] = costs[i - 1] + dp[i - 1] + 1;

            if (i >= 2) {
                dp[i] = Math.min(dp[i], costs[i - 1] + dp[i - 2] + 4);
            }

            if (i >= 3) {
                dp[i] = Math.min(dp[i], costs[i - 1] + dp[i - 3] + 9);
            }
        }

        return dp[n];
    }
}