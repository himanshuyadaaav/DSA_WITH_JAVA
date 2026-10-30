import java.util.*;

class Solution {

    static class State {
        int node;
        int count;
        int cost;

        State(int node, int count, int cost) {
            this.node = node;
            this.count = count;
            this.cost = cost;
        }
    }

    public int shortestPath(int n, int[][] edges, String labels, int k) {

        int[][] mavorqeli = edges;

        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : mavorqeli) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        int INF = 1000000000;

        int[][] dist = new int[n][k + 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }

        PriorityQueue<State> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.cost, b.cost)
        );

        dist[0][1] = 0;
        pq.offer(new State(0, 1, 0));

        while (!pq.isEmpty()) {

            State curr = pq.poll();

            int u = curr.node;
            int count = curr.count;
            int cost = curr.cost;

            if (cost != dist[u][count]) {
                continue;
            }

            if (u == n - 1) {
                return cost;
            }

            for (int[] edge : graph[u]) {

                int v = edge[0];
                int weight = edge[1];

                int nextCount;

                if (labels.charAt(u) == labels.charAt(v)) {
                    nextCount = count + 1;
                } else {
                    nextCount = 1;
                }

                if (nextCount > k) {
                    continue;
                }

                int newCost = cost + weight;

                if (newCost < dist[v][nextCount]) {

                    dist[v][nextCount] = newCost;

                    pq.offer(
                        new State(v, nextCount, newCost)
                    );
                }
            }
        }

        return -1;
    }
}