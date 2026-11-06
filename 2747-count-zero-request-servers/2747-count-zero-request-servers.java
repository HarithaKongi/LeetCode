
import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        Arrays.sort(logs, (a, b) -> a[1] - b[1]);

        int m = queries.length;

        int[][] q = new int[m][2];

        for (int i = 0; i < m; i++) {
            q[i][0] = queries[i];
            q[i][1] = i;
        }

        Arrays.sort(q, (a, b) -> a[0] - b[0]);

        int[] ans = new int[m];

        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int right = 0;

        for (int i = 0; i < m; i++) {

            int start = q[i][0] - x;
            int end = q[i][0];

            // Add logs within the right boundary
            while (right < logs.length &&
                   logs[right][1] <= end) {

                int server = logs[right][0];

                map.put(server,
                    map.getOrDefault(server, 0) + 1);

                right++;
            }

            // Remove logs before the left boundary
            while (left < right &&
                   logs[left][1] < start) {

                int server = logs[left][0];

                map.put(server, map.get(server) - 1);

                if (map.get(server) == 0) {
                    map.remove(server);
                }

                left++;
            }

            ans[q[i][1]] = n - map.size();
        }

        return ans;
    }
}
