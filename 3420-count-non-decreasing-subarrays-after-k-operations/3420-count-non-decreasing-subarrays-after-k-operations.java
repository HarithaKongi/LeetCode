
class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int LOG = 17;

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        int[] next = new int[n];
        int[] stack = new int[n];
        int top = -1;

        // Find next greater element
        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            next[i] = top == -1 ? n : stack[top];
            stack[++top] = i;
        }

        long[][] cost = new long[LOG + 1][n];
        int[][] jump = new int[LOG + 1][n];

        for (int i = 0; i < n; i++) {
            jump[0][i] = next[i];

            cost[0][i] =
                (long) nums[i] * (next[i] - i)
                - (prefix[next[i]] - prefix[i]);
        }

        // Binary lifting
        for (int j = 1; j <= LOG; j++) {
            for (int i = 0; i < n; i++) {
                int mid = jump[j - 1][i];

                if (mid < n) {
                    jump[j][i] = jump[j - 1][mid];
                    cost[j][i] =
                        cost[j - 1][i] + cost[j - 1][mid];
                } else {
                    jump[j][i] = n;
                    cost[j][i] = cost[j - 1][i];
                }
            }
        }

        long ans = 0;

        // Binary search for every starting index
        for (int l = 0; l < n; l++) {
            int low = l;
            int high = n - 1;
            int best = l - 1;

            while (low <= high) {
                int r = low + (high - low) / 2;

                long operations = 0;
                int cur = l;

                for (int j = LOG; j >= 0; j--) {
                    if (cur < n && jump[j][cur] <= r) {
                        operations += cost[j][cur];
                        cur = jump[j][cur];
                    }
                }

                if (cur <= r) {
                    operations +=
                        (long) nums[cur] * (r - cur + 1)
                        - (prefix[r + 1] - prefix[cur]);
                }

                if (operations <= k) {
                    best = r;
                    low = r + 1;
                } else {
                    high = r - 1;
                }
            }

            ans += best - l + 1;
        }

        return ans;
    }
}
