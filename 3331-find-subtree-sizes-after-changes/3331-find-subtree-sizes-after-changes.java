
import java.util.*;

class Solution {

    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        List<Integer>[] children = new ArrayList[n];
        List<Integer>[] newChildren = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
            newChildren[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] oldLast = new int[n];
        int[] newParent = new int[n];

        // Iterative DFS with enter/exit events
        int[] stack = new int[2 * n + 1];
        int top = 0;

        stack[top++] = 0;

        while (top > 0) {

            int event = stack[--top];
            int node = event / 2;
            int ch = s.charAt(node) - 'a';

            if (event % 2 == 0) {

                // Enter node
                oldLast[node] = last[ch];

                if (last[ch] != -1) {
                    newParent[node] = last[ch];
                } else {
                    newParent[node] = parent[node];
                }

                last[ch] = node;

                // Exit event
                stack[top++] = node * 2 + 1;

                // Visit original children
                for (int child : children[node]) {
                    stack[top++] = child * 2;
                }

            } else {

                // Exit node: restore previous ancestor
                last[ch] = oldLast[node];
            }
        }

        // Build modified tree
        for (int i = 1; i < n; i++) {
            newChildren[newParent[i]].add(i);
        }

        // Get traversal order
        int[] order = new int[n];
        top = 0;
        int count = 0;

        stack[top++] = 0;

        while (top > 0) {

            int node = stack[--top];
            order[count++] = node;

            for (int child : newChildren[node]) {
                stack[top++] = child;
            }
        }

        // Calculate subtree sizes bottom-up
        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = n - 1; i >= 0; i--) {

            int node = order[i];

            if (newParent[node] != -1) {
                ans[newParent[node]] += ans[node];
            }
        }

        return ans;
    }
}
