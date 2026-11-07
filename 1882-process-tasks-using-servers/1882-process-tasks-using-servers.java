import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;

        PriorityQueue<int[]> free = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        PriorityQueue<long[]> busy = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return Long.compare(a[0], b[0]);
            }
            return Long.compare(a[1], b[1]);
        });

        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        int[] ans = new int[m];
        long time = 0;

        for (int i = 0; i < m; i++) {
            time = Math.max(time, i);

            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                long[] server = busy.poll();
                int index = (int) server[1];
                free.offer(new int[]{servers[index], index});
            }

            if (free.isEmpty()) {
                time = busy.peek()[0];

                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    long[] server = busy.poll();
                    int index = (int) server[1];
                    free.offer(new int[]{servers[index], index});
                }
            }

            int[] server = free.poll();
            int serverIndex = server[1];

            ans[i] = serverIndex;

            busy.offer(new long[]{
                time + tasks[i],
                serverIndex
            });
        }

        return ans;
    }
}