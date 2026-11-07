
import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        // Free servers: weight, index
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return a[0] - b[0];
                return a[1] - b[1];
            }
        );

        // Busy servers: finish time, weight, index
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Long.compare(a[0], b[0]);
                if (a[1] != b[1])
                    return Long.compare(a[1], b[1]);
                return Long.compare(a[2], b[2]);
            }
        );

        // Initially all servers are free
        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        long time = 0;

        for (int j = 0; j < m; j++) {

            time = Math.max(time, j);

            // Move completed servers to free queue
            while (!busy.isEmpty() && busy.peek()[0] <= time) {

                long[] curr = busy.poll();

                free.offer(new int[]{
                    (int) curr[1],
                    (int) curr[2]
                });
            }

            // If no server is free, wait
            if (free.isEmpty()) {

                time = busy.peek()[0];

                while (!busy.isEmpty() && busy.peek()[0] <= time) {

                    long[] curr = busy.poll();

                    free.offer(new int[]{
                        (int) curr[1],
                        (int) curr[2]
                    });
                }
            }

            // Select the best free server
            int[] curr = free.poll();

            int weight = curr[0];
            int index = curr[1];

            ans[j] = index;

            // Assign task and calculate finishing time
            busy.offer(new long[]{
                time + tasks[j],
                weight,
                index
            });
        }

        return ans;
    }
}
