import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        TreeSet<Integer> available = new TreeSet<>();

        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        int[] count = new int[k];

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        for (int i = 0; i < arrival.length; i++) {

            int time = arrival[i];

            // Release completed servers
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] server = busy.poll();
                available.add(server[1]);
            }

            if (available.isEmpty()) {
                continue;
            }

            int server = i % k;

            // Find next available server
            Integer assigned = available.ceiling(server);

            if (assigned == null) {
                assigned = available.first();
            }

            available.remove(assigned);

            count[assigned]++;

            busy.offer(new int[]{
                time + load[i], assigned
            });
        }

        int max = 0;

        for (int i = 0; i < k; i++) {
            max = Math.max(max, count[i]);
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }

        return result;
    }
}