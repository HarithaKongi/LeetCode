
class Solution {
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();

        boolean[][] dp = new boolean[n + 1][m + 1];

        dp[0][0] = true;

        // Handle patterns like a*, a*b*, a*b*c*
        for (int j = 2; j <= m; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                char ch1 = s.charAt(i - 1);
                char ch2 = p.charAt(j - 1);

                if (ch2 == '.' || ch1 == ch2) {
                    dp[i][j] = dp[i - 1][j - 1];
                }

                else if (ch2 == '*') {

                    // Match zero occurrences
                    dp[i][j] = dp[i][j - 2];

                    // Match one or more occurrences
                    char prev = p.charAt(j - 2);

                    if (prev == '.' || prev == ch1) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }
            }
        }

        return dp[n][m];
    }
}
