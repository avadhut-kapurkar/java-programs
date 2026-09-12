import java.util.*;

class Solution {

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0); // left
            a[i][1] = intervals.get(i).get(1); // right
            a[i][2] = intervals.get(i).get(2); // weight
            a[i][3] = i;                       // original index
        }

        // Sort by starting point
        Arrays.sort(a, (x, y) -> {
            if (x[0] != y[0])
                return Integer.compare(x[0], y[0]);

            if (x[1] != y[1])
                return Integer.compare(x[1], y[1]);

            return Integer.compare(x[3], y[3]);
        });

        int[] starts = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = a[i][0];
        }

        // next[i] = first interval whose start > current end
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = upperBound(starts, a[i][1]);
        }

        /*
         * dp[i][k] = best state from i onward
         * using at most k intervals.
         */
        State[][] dp = new State[n + 1][5];

        for (int k = 0; k <= 4; k++) {
            dp[n][k] = new State(0, new int[0]);
        }

        for (int i = n - 1; i >= 0; i--) {

            dp[i][0] = new State(0, new int[0]);

            for (int k = 1; k <= 4; k++) {

                // Option 1: skip current interval
                State skip = dp[i + 1][k];

                // Option 2: take current interval
                State nxt = dp[next[i]][k - 1];

                int[] takenIndices =
                    new int[nxt.indices.length + 1];

                takenIndices[0] = a[i][3];

                System.arraycopy(
                    nxt.indices,
                    0,
                    takenIndices,
                    1,
                    nxt.indices.length
                );

                Arrays.sort(takenIndices);

                State take = new State(
                    nxt.score + a[i][2],
                    takenIndices
                );

                dp[i][k] = better(skip, take);
            }
        }

        return dp[0][4].indices;
    }

    // Return the state with:
    // 1. Higher score
    // 2. If score equal -> lexicographically smaller indices
    private State better(State x, State y) {

        if (x.score > y.score) {
            return x;
        }

        if (y.score > x.score) {
            return y;
        }

        // Same score
        if (lexicographicallySmaller(x.indices, y.indices)) {
            return x;
        }

        return y;
    }

    private boolean lexicographicallySmaller(
        int[] a,
        int[] b
    ) {

        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {

            if (a[i] != b[i]) {
                return a[i] < b[i];
            }
        }

        // If one is prefix of another,
        // shorter one is lexicographically smaller.
        return a.length < b.length;
    }

    private int upperBound(int[] arr, int target) {

        int left = 0;
        int right = arr.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    static class State {

        long score;
        int[] indices;

        State(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }
}