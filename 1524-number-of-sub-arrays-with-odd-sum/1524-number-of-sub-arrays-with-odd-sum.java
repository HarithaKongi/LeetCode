
class Solution {
    public int numOfSubarrays(int[] arr) {
        int mod = 1000000007;

        int odd = 0;
        int even = 1;
        int prefix = 0;
        int ans = 0;

        for (int num : arr) {

            prefix += num;

            if (prefix % 2 == 0) {
                even++;
            } else {
                odd++;
            }

            ans = (ans + (prefix % 2 == 0 ? odd : even)) % mod;
        }

        return ans;
    }
}
