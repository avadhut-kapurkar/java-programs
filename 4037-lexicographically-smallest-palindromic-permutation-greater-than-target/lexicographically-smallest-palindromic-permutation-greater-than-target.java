class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];
        for (int i = 0; i < n; i++) {
            count[s.charAt(i) - 'a']++;
        }

        // Check if a palindrome can be formed
        int oddCount = 0;
        int oddChar = -1;
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 != 0) {
                oddCount++;
                oddChar = i;
            }
        }

        if ((n % 2 == 0 && oddCount > 0) || (n % 2 == 1 && oddCount != 1)) {
            return "";
        }

        int m = n / 2;
        int[] halfCount = new int[26];
        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        char midChar = oddChar != -1 ? (char) ('a' + oddChar) : 0;

        // Case 1: Check if the first half can match target[0...m-1] exactly
        int[] targetHalfCount = new int[26];
        for (int i = 0; i < m; i++) {
            targetHalfCount[target.charAt(i) - 'a']++;
        }

        boolean canMatchTarget = true;
        for (int i = 0; i < 26; i++) {
            if (targetHalfCount[i] != halfCount[i]) {
                canMatchTarget = false;
                break;
            }
        }

        if (canMatchTarget) {
            String candidate = buildPalindrome(target.substring(0, m), midChar, n % 2 != 0);
            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        // Case 2: Diverge at the largest possible index i in [m-1 down to 0]
        int[] prefixCount = new int[26];
        for (int i = 0; i < m; i++) {
            prefixCount[target.charAt(i) - 'a']++;
        }

        for (int i = m - 1; i >= 0; i--) {
            prefixCount[target.charAt(i) - 'a']--; // Prefix count now covers target[0...i-1]

            // Check if target[0...i-1] is a valid subset
            boolean validPrefix = true;
            for (int j = 0; j < 26; j++) {
                if (prefixCount[j] > halfCount[j]) {
                    validPrefix = false;
                    break;
                }
            }
            if (!validPrefix) continue;

            // Remaining available characters
            int[] rem = new int[26];
            for (int j = 0; j < 26; j++) {
                rem[j] = halfCount[j] - prefixCount[j];
            }

            // Find the smallest char strictly greater than target[i]
            int targetCharVal = target.charAt(i) - 'a';
            int chosenChar = -1;
            for (int c = targetCharVal + 1; c < 26; c++) {
                if (rem[c] > 0) {
                    chosenChar = c;
                    break;
                }
            }

            if (chosenChar != -1) {
                StringBuilder firstHalf = new StringBuilder(target.substring(0, i));
                firstHalf.append((char) ('a' + chosenChar));
                rem[chosenChar]--;

                // Fill remaining positions in sorted ascending order
                for (int c = 0; c < 26; c++) {
                    while (rem[c] > 0) {
                        firstHalf.append((char) ('a' + c));
                        rem[c]--;
                    }
                }

                return buildPalindrome(firstHalf.toString(), midChar, n % 2 != 0);
            }
        }

        return "";
    }

    private String buildPalindrome(String firstHalf, char midChar, boolean hasMid) {
        StringBuilder sb = new StringBuilder(firstHalf);
        if (hasMid) {
            sb.append(midChar);
        }
        for (int i = firstHalf.length() - 1; i >= 0; i--) {
            sb.append(firstHalf.charAt(i));
        }
        return sb.toString();
    }
}