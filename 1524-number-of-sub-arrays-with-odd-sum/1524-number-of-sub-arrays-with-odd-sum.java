class Solution {
    public int numOfSubarrays(int[] arr) {
        int mod = 1_000_000_007;
        long even = 1;
        long odd = 0;
        long ans = 0;
        int parity = 0;

        for (int num : arr) {
            parity = (parity + num) % 2;

            if (parity == 0) {
                ans = (ans + odd) % mod;
                even++;
            } else {
                ans = (ans + even) % mod;
                odd++;
            }
        }

        return (int) ans;
    }
}