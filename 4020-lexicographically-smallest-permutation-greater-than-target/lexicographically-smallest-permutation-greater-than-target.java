class Solution {

    public String lexGreaterPermutation(String s, String target) {

        int n = s.length();

        int[] cnt = new int[26];

        for (char ch : s.toCharArray()) {
            cnt[ch - 'a']++;
        }

        // Try to match target from left to right
        for (int i = 0; i < n; i++) {

            int cur = target.charAt(i) - 'a';

            // If target character is unavailable
            if (cnt[cur] == 0) {

                // Try a slightly bigger character
                for (int bigger = cur + 1; bigger < 26; bigger++) {

                    if (cnt[bigger] > 0) {

                        cnt[bigger]--;

                        StringBuilder ans = new StringBuilder();

                        // Keep prefix same as target
                        ans.append(target, 0, i);

                        // Put bigger character
                        ans.append((char) ('a' + bigger));

                        // Put remaining characters in sorted order
                        appendSorted(ans, cnt);

                        return ans.toString();
                    }
                }

                // Current position cannot be increased,
                // so backtrack
                return backtrack(target, cnt, i - 1);
            }

            // Use target character
            cnt[cur]--;
        }

        // target itself can be formed.
        // Need strictly greater permutation.
        return backtrack(target, cnt, n - 1);
    }


    private String backtrack(
            String target,
            int[] cnt,
            int pos) {

        while (pos >= 0) {

            int cur = target.charAt(pos) - 'a';

            // Restore the character
            cnt[cur]++;

            // Find smallest character greater than current
            for (int bigger = cur + 1; bigger < 26; bigger++) {

                if (cnt[bigger] > 0) {

                    cnt[bigger]--;

                    StringBuilder ans = new StringBuilder();

                    // Same prefix
                    ans.append(target, 0, pos);

                    // Bigger character
                    ans.append((char) ('a' + bigger));

                    // Smallest possible suffix
                    appendSorted(ans, cnt);

                    return ans.toString();
                }
            }

            pos--;
        }

        // No greater permutation exists
        return "";
    }


    private void appendSorted(
            StringBuilder sb,
            int[] cnt) {

        for (int c = 0; c < 26; c++) {

            while (cnt[c] > 0) {
                sb.append((char) ('a' + c));
                cnt[c]--;
            }
        }
    }
}