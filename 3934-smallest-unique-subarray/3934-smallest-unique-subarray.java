import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        int[] sa = buildSuffixArray(nums);
        int[] rank = new int[n];
        int[] lcp = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = rank[i];

            if (r == 0) {
                h = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int i = 0; i < n; i++) {
            int r = rank[i];

            int maxLcp = lcp[r];

            if (r + 1 < n) {
                maxLcp = Math.max(maxLcp, lcp[r + 1]);
            }

            int requiredLength = maxLcp + 1;

            if (requiredLength <= n - i) {
                ans = Math.min(ans, requiredLength);
            }
        }

        return ans;
    }

    private int[] buildSuffixArray(int[] nums) {
        int n = nums.length;
        int N = n + 1;

        int[] s = new int[N];

        for (int i = 0; i < n; i++) {
            s[i] = nums[i];
        }

        s[n] = 0;

        int max = 100001;
        int[] cnt = new int[Math.max(N, max)];

        for (int x : s) {
            cnt[x]++;
        }

        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }

        int[] p = new int[N];

        for (int i = N - 1; i >= 0; i--) {
            p[--cnt[s[i]]] = i;
        }

        int[] c = new int[N];
        int classes = 1;

        for (int i = 1; i < N; i++) {
            if (s[p[i]] != s[p[i - 1]]) {
                classes++;
            }
            c[p[i]] = classes - 1;
        }

        int len = 1;

        while (len < N) {
            int[] pn = new int[N];

            for (int i = 0; i < N; i++) {
                pn[i] = p[i] - len;

                if (pn[i] < 0) {
                    pn[i] += N;
                }
            }

            int[] count = new int[classes];

            for (int x : pn) {
                count[c[x]]++;
            }

            int[] pos = new int[classes];

            for (int i = 1; i < classes; i++) {
                pos[i] = pos[i - 1] + count[i - 1];
            }

            int[] newP = new int[N];

            for (int x : pn) {
                newP[pos[c[x]]++] = x;
            }

            p = newP;

            int[] newC = new int[N];
            int newClasses = 1;

            for (int i = 1; i < N; i++) {
                int cur = p[i];
                int prev = p[i - 1];

                int midCur = (cur + len) % N;
                int midPrev = (prev + len) % N;

                if (c[cur] != c[prev] ||
                    c[midCur] != c[midPrev]) {
                    newClasses++;
                }

                newC[cur] = newClasses - 1;
            }

            c = newC;
            classes = newClasses;
            len <<= 1;
        }

        return Arrays.copyOfRange(p, 1, N);
    }
}