import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        // Step 1: Build the adjacency list for the graph
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : invocations) {
            graph[edge[0]].add(edge[1]);
        }

        // Step 2: Find all suspicious methods reachable from k using BFS
        boolean[] suspicious = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.offer(k);
        suspicious[k] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph[u]) {
                if (!suspicious[v]) {
                    suspicious[v] = true;
                    q.offer(v);
                }
            }
        }

        // Step 3: If any non-suspicious method invokes a suspicious one,
        // we cannot remove anything. Return all methods.
        for (int[] edge : invocations) {
            int a = edge[0];
            int b = edge[1];
            if (!suspicious[a] && suspicious[b]) {
                List<Integer> all = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    all.add(i);
                }
                return all;
            }
        }

        // Step 4: Return only the remaining (non-suspicious) methods
        List<Integer> remaining = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                remaining.add(i);
            }
        }
        return remaining;
    }
}