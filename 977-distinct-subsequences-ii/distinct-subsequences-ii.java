class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1000000007L;

        long[] dp = new long[s.length() + 1];
        long[] last = new long[26];

        // Empty subsequence
        dp[0] = 1;

        for (int i = 1; i <= s.length(); i++) {
            char c = s.charAt(i - 1);
            int idx = c - 'a';

            dp[i] = (2 * dp[i - 1] - last[idx] + MOD) % MOD;

            last[idx] = dp[i - 1];
        }

        // Remove empty subsequence
        return (int) ((dp[s.length()] - 1 + MOD) % MOD);
    }
}