
import java.util.*;

class Solution {
    public int[] pathsWithMaxScore(List<String> board) {

        int n = board.size();
        int MOD = 1_000_000_007;

        int[][] score = new int[n][n];
        int[][] ways = new int[n][n];

        // -1 means unreachable
        for (int[] row : score) {
            Arrays.fill(row, -1);
        }

        score[n - 1][n - 1] = 0;
        ways[n - 1][n - 1] = 1;

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                char ch = board.get(i).charAt(j);

                if (ch == 'X' || (i == n - 1 && j == n - 1)) {
                    continue;
                }

                int best = -1;
                int count = 0;

                // Move up
                if (i + 1 < n) {
                    if (score[i + 1][j] > best) {
                        best = score[i + 1][j];
                        count = ways[i + 1][j];
                    } else if (score[i + 1][j] == best && best != -1) {
                        count = (count + ways[i + 1][j]) % MOD;
                    }
                }

                // Move right
                if (j + 1 < n) {
                    if (score[i][j + 1] > best) {
                        best = score[i][j + 1];
                        count = ways[i][j + 1];
                    } else if (score[i][j + 1] == best && best != -1) {
                        count = (count + ways[i][j + 1]) % MOD;
                    }
                }

                // Move diagonal
                if (i + 1 < n && j + 1 < n) {
                    if (score[i + 1][j + 1] > best) {
                        best = score[i + 1][j + 1];
                        count = ways[i + 1][j + 1];
                    } else if (score[i + 1][j + 1] == best && best != -1) {
                        count = (count + ways[i + 1][j + 1]) % MOD;
                    }
                }

                if (best != -1) {
                    int value = (ch >= '1' && ch <= '9')
                            ? ch - '0' : 0;

                    score[i][j] = best + value;
                    ways[i][j] = count;
                }
            }
        }

        if (score[0][0] == -1) {
            return new int[]{0, 0};
        }

        return new int[]{score[0][0], ways[0][0]};
    }
}
