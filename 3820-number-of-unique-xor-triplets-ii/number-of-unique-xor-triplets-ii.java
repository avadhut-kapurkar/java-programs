class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[] present = new boolean[MAX];
        for (int x : nums) {
            present[x] = true;
        }

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int t = 1; t <= 3; t++) {
            for (int xor = 0; xor < MAX; xor++) {
                if (!dp[t - 1][xor]) continue;

                for (int v = 0; v < MAX; v++) {
                    if (present[v]) {
                        dp[t][xor ^ v] = true;
                    }
                }
            }
        }

        int ans = 0;
        for (boolean possible : dp[3]) {
            if (possible) ans++;
        }

        return ans;
    }
}
