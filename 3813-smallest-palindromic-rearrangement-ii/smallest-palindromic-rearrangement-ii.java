class Solution {
    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        int[] halfFreq = new int[26];
        String mid = "";
        
        // Build the frequency map for half of the string
        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
            if (freq[i] % 2 != 0) {
                mid = String.valueOf((char) (i + 'a'));
            }
        }
        
        long maxVal = k + 1; // Safely cap to prevent arithmetic overflow and optimize loops
        long totalWays = boundedPermutations(halfFreq, maxVal);
        
        // If k exceeds the total possible unique permutations, return empty
        if (k > totalWays) {
            return "";
        }
        
        int halfLength = s.length() / 2;
        StringBuilder halfStr = new StringBuilder();
        
        // Construct the left half character by character
        for (int step = 0; step < halfLength; step++) {
            for (int c = 0; c < 26; c++) {
                if (halfFreq[c] == 0) continue;
                
                halfFreq[c]--; // Tentatively place character `c`
                long ways = boundedPermutations(halfFreq, maxVal);
                
                if (k <= ways) {
                    halfStr.append((char) (c + 'a')); // Lock in character
                    break;
                } else {
                    k -= ways;       // Skip this branch's combinations
                    halfFreq[c]++;   // Restore and try the next character
                }
            }
        }
        
        String leftHalf = halfStr.toString();
        return leftHalf + mid + halfStr.reverse().toString();
    }
    
    // Calculates the distinct permutations for the given frequencies, capped at maxVal
    private long boundedPermutations(int[] freq, long maxVal) {
        long ways = 1;
        int n = 0;
        
        for (int i = 0; i < 26; i++) {
            for (int j = 1; j <= freq[i]; j++) {
                n++;
                // Compute the incremental combinations. 
                // Using previously built `ways` implicitly computes (n! / f_0!...f_i!)
                ways = ways * n / j; 
                
                if (ways > maxVal) {
                    return maxVal + 1;
                }
            }
        }
        return ways;
    }
}