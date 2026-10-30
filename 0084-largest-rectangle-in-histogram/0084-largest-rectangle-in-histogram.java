
import java.util.*;

class Solution {
    public int largestRectangleArea(int[] heights) {

        int maxArea = 0;
        int n = heights.length;

        Stack<int[]> st = new Stack<>();

        // Process of filling the stack
        for (int i = 0; i < n; i++) {

            int start = i;

            while (!st.isEmpty() && heights[i] < st.peek()[0]) {

                int[] top = st.pop();

                int ht = top[0];
                int idx = top[1];

                int wd = i - idx;
                int area = wd * ht;

                maxArea = Math.max(maxArea, area);

                start = idx;
            }

            st.push(new int[]{heights[i], start});
        }

        // Process for emptying the stack
        while (!st.isEmpty()) {

            int[] top = st.pop();

            int ht = top[0];
            int idx = top[1];

            int wd = n - idx;
            int area = wd * ht;

            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
}
