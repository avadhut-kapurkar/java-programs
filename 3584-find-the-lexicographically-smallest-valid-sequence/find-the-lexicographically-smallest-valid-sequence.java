import java.util.Arrays;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        
        // last[j] stores the rightmost index in word1 that matches word2[j]
        // such that the remaining suffix of word2 can also be matched.
        int[] last = new int[m];
        Arrays.fill(last, -1);
        
        // Step 1: Suffix preprocessing (Right to Left)
        int j = m - 1;
        for (int i = n - 1; i >= 0 && j >= 0; i--) {
            if (word1.charAt(i) == word2.charAt(j)) {
                last[j] = i;
                j--;
            }
        }
        
        // Step 2: Greedy construction (Left to Right)
        j = 0;
        boolean skipped = false; // Tracks if we used our 1 allowed modification
        int[] ans = new int[m];
        
        for (int i = 0; i < n && j < m; i++) {
            if (word1.charAt(i) == word2.charAt(j)) {
                // Characters match, record the index
                ans[j] = i;
                j++;
            } else {
                // Characters don't match. 
                // We can use our modification IF we haven't used it yet AND:
                // 1. It is the last character of word2 (j == m - 1) OR
                // 2. The required suffix of word2 can still be safely matched in the remaining suffix of word1 (i < last[j + 1])
                if (!skipped && (j == m - 1 || i < last[j + 1])) {
                    ans[j] = i;
                    j++;
                    skipped = true;
                }
            }
        }
        
        // If we successfully matched all 'm' characters of word2
        if (j == m) {
            return ans;
        }
        
        // Otherwise, no valid sequence exists
        return new int[0];
    }
}
