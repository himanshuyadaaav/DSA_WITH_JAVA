import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        int[] head = new int[n];
        int[] to = new int[n];
        int[] next = new int[n];

        Arrays.fill(head, -1);

        for (int i = 1; i < n; i++) {
            to[i - 1] = i;
            next[i - 1] = head[parent[i]];
            head[parent[i]] = i - 1;
        }

        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] newParent = new int[n];
        int[] oldLast = new int[n];
        int[] order = new int[n];

        int[] stack = new int[2 * n + 5];
        int top = 0;
        int idx = 0;

        stack[top++] = 0;

        while (top > 0) {
            int u = stack[--top];

            if (u >= 0) {
                int c = s.charAt(u) - 'a';

                oldLast[u] = last[c];

                if (u == 0) {
                    newParent[u] = -1;
                } else {
                    newParent[u] = last[c] != -1
                            ? last[c]
                            : parent[u];
                }

                last[c] = u;
                order[idx++] = u;

                stack[top++] = ~u;

                for (int e = head[u]; e != -1; e = next[e]) {
                    stack[top++] = to[e];
                }

            } else {
                int v = ~u;
                int c = s.charAt(v) - 'a';
                last[c] = oldLast[v];
            }
        }

        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];

            if (newParent[u] != -1) {
                ans[newParent[u]] += ans[u];
            }
        }

        return ans;
    }
}