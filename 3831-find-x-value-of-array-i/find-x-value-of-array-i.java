class Solution {
    public long[] resultArray(int[] nums, int k) {
        // Result array to store the total counts for each modulo x (from 0 to k-1)
        long[] result = new long[k];
        
        // dp array keeps track of the number of subarrays ending at the previous index 
        // that yield a specific modulo k
        long[] dp = new long[k];
        
        for (int num : nums) {
            long[] nextDp = new long[k];
            int v = num % k;
            
            // Extend existing subarrays ending at the previous index
            for (int r = 0; r < k; r++) {
                if (dp[r] > 0) {
                    nextDp[(r * v) % k] += dp[r];
                }
            }
            
            // Subarray starting exactly at the current element
            nextDp[v] += 1;
            
            // Add current step's counts to the global result and update dp
            for (int x = 0; x < k; x++) {
                result[x] += nextDp[x];
                dp[x] = nextDp[x];
            }
        }
        
        return result;
    }
}
