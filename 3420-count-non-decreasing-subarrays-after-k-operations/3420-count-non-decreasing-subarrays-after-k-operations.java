import java.util.*;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> dq = new ArrayDeque<>();

        long cost = 0;
        long ans = 0;
        int right = n - 1;

        for (int left = n - 1; left >= 0; left--) {

            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[left]) {
                int idx = dq.pollLast();
                int next = dq.isEmpty() ? right + 1 : dq.peekLast();

                cost += (long) (next - idx) * (nums[left] - nums[idx]);
            }

            dq.offerLast(left);

            while (cost > k) {
                cost -= (long) nums[dq.peekFirst()] - nums[right];

                if (dq.peekFirst() == right) {
                    dq.pollFirst();
                }

                right--;
            }

            ans += right - left + 1;
        }

        return ans;
    }
}