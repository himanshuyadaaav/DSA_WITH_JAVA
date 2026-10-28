class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] dp = new boolean[n][1024];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int value = grid[i][j];
                boolean[] current = new boolean[1024];

                if (i == 0 && j == 0) {
                    current[value] = true;
                } else {
                    if (i > 0) {
                        for (int x = 0; x < 1024; x++) {
                            if (dp[j][x]) {
                                current[x ^ value] = true;
                            }
                        }
                    }

                    if (j > 0) {
                        for (int x = 0; x < 1024; x++) {
                            if (dp[j - 1][x]) {
                                current[x ^ value] = true;
                            }
                        }
                    }
                }

                dp[j] = current;
            }
        }

        for (int x = 0; x < 1024; x++) {
            if (dp[n - 1][x]) {
                return x;
            }
        }

        return 0;
    }
}