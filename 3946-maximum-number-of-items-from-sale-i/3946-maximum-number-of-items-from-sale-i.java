import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] bonus = new int[n];

        for (int i = 0; i < n; i++) {
            bonus[i] = 1;

            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    bonus[i]++;
                }
            }
        }

        List<Integer>[] groups = new ArrayList[1501];

        for (int i = 0; i < n; i++) {
            int price = items[i][1];

            if (groups[price] == null) {
                groups[price] = new ArrayList<>();
            }

            groups[price].add(i);
        }

        int NEG = -1000000000;

        int[] dp = new int[budget + 1];
        Arrays.fill(dp, NEG);
        dp[0] = 0;

        int ans = 0;

        for (int p = 1500; p >= 1; p--) {
            if (groups[p] == null) {
                continue;
            }

            int[] withCurrentPrice = new int[budget + 1];
            Arrays.fill(withCurrentPrice, NEG);

            for (int id : groups[p]) {
                for (int cost = budget; cost >= p; cost--) {
                    int previous = Math.max(
                        dp[cost - p],
                        withCurrentPrice[cost - p]
                    );

                    if (previous != NEG) {
                        withCurrentPrice[cost] = Math.max(
                            withCurrentPrice[cost],
                            previous + bonus[id]
                        );
                    }
                }
            }

            for (int cost = 0; cost <= budget; cost++) {
                if (withCurrentPrice[cost] != NEG) {
                    int total = withCurrentPrice[cost]
                            + (budget - cost) / p;

                    ans = Math.max(ans, total);
                }
            }

            for (int id : groups[p]) {
                for (int cost = budget; cost >= p; cost--) {
                    if (dp[cost - p] != NEG) {
                        dp[cost] = Math.max(
                            dp[cost],
                            dp[cost - p] + bonus[id]
                        );
                    }
                }
            }
        }

        return ans;
    }
}