import java.util.*;

class Solution {
    private int[][] up;
    private int[] depth;
    private int log;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        parent[0] = 0;

        depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] order = new int[n];
        int[] stack = new int[n];

        int top = 0;
        int timer = 0;
        stack[top++] = 0;

        while (top > 0) {
            int u = stack[--top];

            tin[u] = timer;
            order[timer++] = u;

            for (int v : graph[u]) {
                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        int[] size = new int[n];
        Arrays.fill(size, 1);

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            size[parent[u]] += size[u];
        }

        for (int u = 0; u < n; u++) {
            tout[u] = tin[u] + size[u] - 1;
        }

        log = 1;
        while ((1 << log) <= n) {
            log++;
        }

        up = new int[log][n];
        up[0] = parent.clone();

        for (int j = 1; j < log; j++) {
            for (int u = 0; u < n; u++) {
                up[j][u] = up[j - 1][up[j - 1][u]];
            }
        }

        Fenwick bit = new Fenwick(n);
        char[] chars = s.toCharArray();

        for (int u = 0; u < n; u++) {
            int mask = 1 << (chars[u] - 'a');
            bit.rangeXor(tin[u], tout[u], mask);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                if (chars[u] == c) {
                    continue;
                }

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int difference = oldMask ^ newMask;

                bit.rangeXor(tin[u], tout[u], difference);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                int mask = bit.query(tin[u])
                         ^ bit.query(tin[v])
                         ^ (1 << (chars[ancestor] - 'a'));

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int difference = depth[u] - depth[v];

        for (int j = 0; j < log; j++) {
            if ((difference & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = log - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    private static class Fenwick {
        private final int n;
        private final int[] tree;

        Fenwick(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }

        private void update(int index, int value) {
            for (int i = index + 1; i <= n; i += i & -i) {
                tree[i] ^= value;
            }
        }

        void rangeXor(int left, int right, int value) {
            if (value == 0) {
                return;
            }

            update(left, value);

            if (right + 1 < n) {
                update(right + 1, value);
            }
        }

        int query(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}