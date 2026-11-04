
import java.util.*;

class Solution {

    class Fenwick {
        long[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new long[n + 1];
        }

        void update(int i, long val) {
            while (i <= n) {
                tree[i] += val;
                i += i & -i;
            }
        }

        long query(int i) {
            long sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }

        int kth(long k) {
            int idx = 0;

            for (int bit = Integer.highestOneBit(n);
                 bit > 0; bit >>= 1) {

                int next = idx + bit;

                if (next <= n && tree[next] < k) {
                    idx = next;
                    k -= tree[next];
                }
            }

            return idx + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int[] unique = Arrays.stream(sorted)
                .distinct()
                .toArray();

        int m = unique.length;

        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[i] = Arrays.binarySearch(unique, nums[i]) + 1;
        }

        Fenwick count = new Fenwick(m);
        Fenwick sum = new Fenwick(m);

        long[] cost = new long[n - x + 1];

        long total = 0;

        for (int i = 0; i < n; i++) {

            count.update(rank[i], 1);
            sum.update(rank[i], nums[i]);

            total += nums[i];

            if (i >= x) {
                count.update(rank[i - x], -1);
                sum.update(rank[i - x], -nums[i - x]);
                total -= nums[i - x];
            }

            if (i >= x - 1) {

                long medianRank = (x + 1L) / 2;
                int medianIndex = count.kth(medianRank);

                long median = unique[medianIndex - 1];

                long leftCount = count.query(medianIndex);
                long leftSum = sum.query(medianIndex);

                long rightCount = x - leftCount;
                long rightSum = total - leftSum;

                cost[i - x + 1] =
                        median * leftCount - leftSum
                        + rightSum - median * rightCount;
            }
        }

        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[k + 1][n + 1];

        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        for (int i = 1; i <= n; i++) {

            dp[0][i] = 0;

            for (int j = 1; j <= k; j++) {

                dp[j][i] = dp[j][i - 1];

                if (i >= x && dp[j - 1][i - x] != INF) {

                    long take = dp[j - 1][i - x]
                            + cost[i - x];

                    dp[j][i] = Math.min(dp[j][i], take);
                }
            }
        }

        return dp[k][n];
    }
}
