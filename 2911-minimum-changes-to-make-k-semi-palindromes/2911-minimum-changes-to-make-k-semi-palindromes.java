
class Solution {

    public int minimumChanges(String s, int k) {

        int n = s.length();

        // cost[i][j] = minimum changes needed
        // to make substring s[i...j] semi-palindrome

        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {

            for (int i = 0; i + len <= n; i++) {

                int j = i + len - 1;
                cost[i][j] = Integer.MAX_VALUE;

                // Try every valid divisor
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Compare characters in each group
                    for (int r = 0; r < d; r++) {

                        int left = i + r;
                        int right = i + len - d + r;

                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    cost[i][j] =
                        Math.min(cost[i][j], changes);
                }
            }
        }

        // DP
        int[][] dp = new int[k + 1][n + 1];

        for (int i = 0; i <= k; i++) {
            java.util.Arrays.fill(dp[i], 1000000);
        }

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {

            for (int end = 2 * parts; end <= n; end++) {

                for (int start = 2 * (parts - 1);
                     start <= end - 2; start++) {

                    dp[parts][end] = Math.min(
                        dp[parts][end],
                        dp[parts - 1][start] +
                        cost[start][end - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
