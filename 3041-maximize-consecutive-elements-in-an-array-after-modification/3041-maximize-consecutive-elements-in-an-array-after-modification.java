import java.util.*;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        Map<Integer, Integer> dp = new HashMap<>();
        int ans = 0;

        for (int num : nums) {
            int prev = dp.getOrDefault(num, 0);

            dp.put(num + 1, Math.max(
                dp.getOrDefault(num + 1, 0),
                prev + 1
            ));

            dp.put(num, Math.max(
                dp.getOrDefault(num, 0),
                dp.getOrDefault(num - 1, 0) + 1
            ));

            ans = Math.max(ans, Math.max(
                dp.get(num),
                dp.get(num + 1)
            ));
        }

        return ans;
    }
}