
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        int[] free = new int[n];

        // Calculate free items received from each item type
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    free[i]++;
                }
            }
        }

        // Group items according to their prices
        List<Integer>[] groups = new ArrayList[budget + 1];

        for (int i = 1; i <= budget; i++) {
            groups[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            int price = items[i][1];

            if (price <= budget) {
                groups[price].add(i);
            }
        }

        int NEG = -1000000000;

        // DP for items with prices greater than current price
        int[] dp = new int[budget + 1];
        Arrays.fill(dp, NEG);
        dp[0] = 0;

        int ans = 0;

        // Try every possible minimum purchased item price
        for (int p = budget; p >= 1; p--) {

            if (groups[p].isEmpty()) {
                continue;
            }

            // At least one item with price p must be selected
            int[] curr = new int[budget + 1];
            Arrays.fill(curr, NEG);

            for (int idx : groups[p]) {
                int value = 1 + free[idx];

                for (int b = budget; b >= p; b--) {

                    if (dp[b - p] != NEG) {
                        curr[b] = Math.max(
                            curr[b],
                            dp[b - p] + value
                        );
                    }

                    if (curr[b - p] != NEG) {
                        curr[b] = Math.max(
                            curr[b],
                            curr[b - p] + value
                        );
                    }
                }
            }

            // Calculate purchased and free items
            for (int b = 0; b <= budget; b++) {
                if (curr[b] != NEG) {
                    int total = curr[b] + (budget - b) / p;
                    ans = Math.max(ans, total);
                }
            }

            // Add current price group to normal DP
            for (int idx : groups[p]) {
                int value = 1 + free[idx];

                for (int b = budget; b >= p; b--) {
                    if (dp[b - p] != NEG) {
                        dp[b] = Math.max(
                            dp[b],
                            dp[b - p] + value
                        );
                    }
                }
            }
        }

        return ans;
    }
}
