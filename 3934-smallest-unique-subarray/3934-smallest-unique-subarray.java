
import java.util.*;

class Solution {

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        int[] sa = buildSuffixArray(nums);
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int[] lcp = new int[n];
        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = rank[i];

            if (r == 0) {
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int i = 0; i < n; i++) {
            int common = lcp[i];

            if (i + 1 < n) {
                common = Math.max(common, lcp[i + 1]);
            }

            int len = common + 1;

            if (len <= n - sa[i]) {
                ans = Math.min(ans, len);
            }
        }

        return ans;
    }

    private int[] buildSuffixArray(int[] nums) {
        int n = nums.length;

        int[] sa = new int[n];
        int[] r = nums.clone();
        int[] nr = new int[n];

        int max = 0;

        for (int x : nums) {
            max = Math.max(max, x);
        }

        int classes = max + 1;

        for (int i = 0; i < n; i++) {
            sa[i] = i;
        }

        int[] temp = new int[n];
        int[] count = new int[Math.max(n + 2, classes + 2)];

        for (int k = 1; k < n; k *= 2) {

            countingSort(sa, temp, r, k, classes,
                         count, true);

            countingSort(temp, sa, r, k, classes,
                         count, false);

            int newClasses = 1;
            nr[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                int a2 = a + k < n ? r[a + k] : -1;
                int b2 = b + k < n ? r[b + k] : -1;

                if (r[a] != r[b] || a2 != b2) {
                    newClasses++;
                }

                nr[b] = newClasses - 1;
            }

            int[] swap = r;
            r = nr;
            nr = swap;

            classes = newClasses;

            if (classes == n) {
                break;
            }
        }

        return sa;
    }

    private void countingSort(
        int[] input,
        int[] output,
        int[] rank,
        int k,
        int classes,
        int[] count,
        boolean second
    ) {

        Arrays.fill(count, 0, classes + 1, 0);

        int n = input.length;

        for (int x : input) {
            int key;

            if (second) {
                key = x + k < n ? rank[x + k] + 1 : 0;
            } else {
                key = rank[x] + 1;
            }

            count[key]++;
        }

        for (int i = 1; i <= classes; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int x = input[i];
            int key;

            if (second) {
                key = x + k < n ? rank[x + k] + 1 : 0;
            } else {
                key = rank[x] + 1;
            }

            output[--count[key]] = x;
        }
    }
}
