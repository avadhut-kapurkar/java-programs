import java.util.Arrays;

class Solution {
    // DP array to store the minimum number of digits to get 'i' 2s and 'j' 3s
    static int[][] dp = new int[60][40];
    
    static {
        for (int[] row : dp) Arrays.fill(row, 1000000);
        dp[0][0] = 0;
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 40; j++) {
                if (i == 0 && j == 0) continue;
                int min = 1000000;
                min = Math.min(min, 1 + dp[Math.max(0, i - 1)][j]);                   // digit 2
                min = Math.min(min, 1 + dp[i][Math.max(0, j - 1)]);                   // digit 3
                min = Math.min(min, 1 + dp[Math.max(0, i - 2)][j]);                   // digit 4
                min = Math.min(min, 1 + dp[Math.max(0, i - 1)][Math.max(0, j - 1)]);  // digit 6
                min = Math.min(min, 1 + dp[Math.max(0, i - 3)][j]);                   // digit 8
                min = Math.min(min, 1 + dp[i][Math.max(0, j - 2)]);                   // digit 9
                dp[i][j] = min;
            }
        }
    }

    // Quick lookup for prime factors in digits 0-9
    static final int[] C2 = {0, 0, 1, 0, 2, 0, 1, 0, 3, 0};
    static final int[] C3 = {0, 0, 0, 1, 0, 0, 1, 0, 0, 2};
    static final int[] C5 = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
    static final int[] C7 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};

    public String smallestNumber(String num, long t) {
        long temp = t;
        int req2 = 0, req3 = 0, req5 = 0, req7 = 0;
        
        while (temp % 2 == 0) { req2++; temp /= 2; }
        while (temp % 3 == 0) { req3++; temp /= 3; }
        while (temp % 5 == 0) { req5++; temp /= 5; }
        while (temp % 7 == 0) { req7++; temp /= 7; }
        
        // If t has prime factors greater than 7, it's impossible
        if (temp > 1) return "-1"; 

        int n = num.length();
        int[] pref2 = new int[n + 1];
        int[] pref3 = new int[n + 1];
        int[] pref5 = new int[n + 1];
        int[] pref7 = new int[n + 1];

        int zeroIdx = n;
        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            if (d == 0 && zeroIdx == n) {
                zeroIdx = i; // Tracking the first 0 found
            }
            pref2[i + 1] = pref2[i] + C2[d];
            pref3[i + 1] = pref3[i] + C3[d];
            pref5[i + 1] = pref5[i] + C5[d];
            pref7[i + 1] = pref7[i] + C7[d];
        }

        // 1. Check if the original num is strictly valid itself
        if (zeroIdx == n) {
            if (pref2[n] >= req2 && pref3[n] >= req3 && pref5[n] >= req5 && pref7[n] >= req7) {
                return num;
            }
        }

        // 2. Try to increment a digit from right-to-left
        for (int i = Math.min(n - 1, zeroIdx); i >= 0; i--) {
            int d_orig = num.charAt(i) - '0';
            
            for (int d = d_orig + 1; d <= 9; d++) {
                int c2 = req2 - pref2[i] - C2[d];
                int c3 = req3 - pref3[i] - C3[d];
                int c5 = req5 - pref5[i] - C5[d];
                int c7 = req7 - pref7[i] - C7[d];

                // If replacing `num[i]` with `d` leaves us enough length to fulfill required primes
                if (minLen(c2, c3, c5, c7) <= n - 1 - i) {
                    return build(num.substring(0, i) + d, n, req2, req3, req5, req7);
                }
            }
        }

        // 3. Fallback: If no modification on the same length yields a valid number, we need a longer one
        int L = Math.max(n + 1, minLen(req2, req3, req5, req7));
        return build("", L, req2, req3, req5, req7);
    }

    private int minLen(int c2, int c3, int c5, int c7) {
        c2 = Math.max(0, c2);
        c3 = Math.max(0, c3);
        c5 = Math.max(0, c5);
        c7 = Math.max(0, c7);
        // Safety bound check
        if (c2 >= 60 || c3 >= 40) return 1000000; 
        return c5 + c7 + dp[c2][c3];
    }

    private String build(String prefix, int totalLen, int req2, int req3, int req5, int req7) {
        StringBuilder sb = new StringBuilder(prefix);
        
        // Offset previously fulfilled prime constraints by the prefix 
        for (int i = 0; i < prefix.length(); i++) {
            int d = prefix.charAt(i) - '0';
            req2 -= C2[d];
            req3 -= C3[d];
            req5 -= C5[d];
            req7 -= C7[d];
        }

        int remLen = totalLen - prefix.length();
        
        // Greedily append the minimal digits (1-9) matching the required factors in remaining spots
        for (int pos = 1; pos <= remLen; pos++) {
            for (int d = 1; d <= 9; d++) {
                int nc2 = req2 - C2[d];
                int nc3 = req3 - C3[d];
                int nc5 = req5 - C5[d];
                int nc7 = req7 - C7[d];
                
                if (minLen(nc2, nc3, nc5, nc7) <= remLen - pos) {
                    sb.append(d);
                    req2 = nc2;
                    req3 = nc3;
                    req5 = nc5;
                    req7 = nc7;
                    break;
                }
            }
        }
        return sb.toString();
    }
}