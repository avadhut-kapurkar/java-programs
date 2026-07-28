class Solution {
    public String smallestPalindrome(String s) {
        // Step 1: Count character frequencies
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }
        
        char[] result = new char[s.length()];
        int left = 0;
        int right = s.length() - 1;
        
        // Step 2 & 3: Build the palindrome
        for (int i = 0; i < 26; i++) {
            // Place characters in pairs at the ends moving inwards
            while (freq[i] >= 2) {
                result[left++] = (char) (i + 'a');
                result[right--] = (char) (i + 'a');
                freq[i] -= 2;
            }
            // If one instance of the character remains, it goes in the middle
            if (freq[i] == 1) {
                result[s.length() / 2] = (char) (i + 'a');
            }
        }
        
        return new String(result);
    }
}