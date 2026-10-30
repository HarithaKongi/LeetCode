
import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {

        List<int[]>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
        }

        // dist[node][last character streak length]
        int[][] dist = new int[n][k + 1];

        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // State: {distance, node, consecutive count}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        int startCount = 1;
        dist[0][startCount] = 0;

        pq.offer(new int[]{0, 0, startCount});

        while (!pq.isEmpty()) {

            int[] curr = pq.poll();

            int cost = curr[0];
            int node = curr[1];
            int count = curr[2];

            if (cost != dist[node][count]) {
                continue;
            }

            if (node == n - 1) {
                return cost;
            }

            for (int[] edge : adj[node]) {

                int next = edge[0];
                int weight = edge[1];

                int newCount;

                if (labels.charAt(node) == labels.charAt(next)) {
                    newCount = count + 1;
                } else {
                    newCount = 1;
                }

                if (newCount > k) {
                    continue;
                }

                int newCost = cost + weight;

                if (newCost < dist[next][newCount]) {

                    dist[next][newCount] = newCost;

                    pq.offer(new int[]{
                        newCost,
                        next,
                        newCount
                    });
                }
            }
        }

        return -1;
    }
}
