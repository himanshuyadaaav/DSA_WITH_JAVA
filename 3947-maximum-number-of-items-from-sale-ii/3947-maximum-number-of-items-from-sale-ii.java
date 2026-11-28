import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        int[] freq = new int[n + 1];

        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] divisible = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                divisible[i] += freq[j];
            }
        }

        TreeMap<Integer, Long> bonus = new TreeMap<>();

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            long freeCount = divisible[factor] - 1L;

            if (freeCount > 0) {
                bonus.merge(price, freeCount, Long::sum);
            }
        }

        long remaining = budget;
        long bonusBought = 0;

        for (Map.Entry<Integer, Long> entry : bonus.entrySet()) {
            long price = entry.getKey();
            long count = entry.getValue();

            if (price > 2L * minPrice) {
                break;
            }

            long buy = Math.min(count, remaining / price);

            bonusBought += buy;
            remaining -= buy * price;

            if (buy < count) {
                break;
            }
        }

        long answer = 2L * bonusBought + remaining / minPrice;

        return (int) answer;
    }
}