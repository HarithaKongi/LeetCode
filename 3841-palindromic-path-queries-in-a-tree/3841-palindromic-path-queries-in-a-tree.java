
import java.util.*;

class Solution {
    int n, timer;
    List<Integer>[] adj;
    int[] parent, depth, size, heavy, head, pos;
    int[] seg;

    void dfs1(int u, int p) {
        parent[u] = p;
        size[u] = 1;
        int maxSize = 0;

        for (int v : adj[u]) {
            if (v == p) continue;

            depth[v] = depth[u] + 1;
            dfs1(v, u);
            size[u] += size[v];

            if (size[v] > maxSize) {
                maxSize = size[v];
                heavy[u] = v;
            }
        }
    }

    void dfs2(int u, int h, int[] value) {
        head[u] = h;
        pos[u] = timer++;
        update(1, 0, n - 1, pos[u], value[u]);

        if (heavy[u] != -1) {
            dfs2(heavy[u], h, value);
        }

        for (int v : adj[u]) {
            if (v == parent[u] || v == heavy[u]) continue;
            dfs2(v, v, value);
        }
    }

    void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            seg[node] = val;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, val);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, val);
        }

        seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
    }

    int query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) return seg[node];
        if (qr < l || r < ql) return 0;

        int mid = (l + r) / 2;

        return query(node * 2, l, mid, ql, qr)
             ^ query(node * 2 + 1, mid + 1, r, ql, qr);
    }

    int pathQuery(int u, int v) {
        int mask = 0;

        while (head[u] != head[v]) {
            if (depth[head[u]] < depth[head[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }

            mask ^= query(
                1, 0, n - 1, pos[head[u]], pos[u]
            );

            u = parent[head[u]];
        }

        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        mask ^= query(1, 0, n - 1, pos[u], pos[v]);

        return mask;
    }

    public List<Boolean> palindromePath(
        int n, int[][] edges, String s, String[] queries
    ) {
        this.n = n;
        timer = 0;

        adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];

        Arrays.fill(heavy, -1);

        int[] value = new int[n];

        for (int i = 0; i < n; i++) {
            value[i] = 1 << (s.charAt(i) - 'a');
        }

        seg = new int[4 * n];

        dfs1(0, -1);
        dfs2(0, 0, value);

        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            int u = Integer.parseInt(parts[1]);

            if (parts[0].equals("update")) {
                char c = parts[2].charAt(0);
                value[u] = 1 << (c - 'a');

                update(1, 0, n - 1, pos[u], value[u]);
            } else {
                int v = Integer.parseInt(parts[2]);
                int mask = pathQuery(u, v);

                result.add((mask & (mask - 1)) == 0);
            }
        }

        return result;
    }
}
