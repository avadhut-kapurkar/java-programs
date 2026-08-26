class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        String ans = "";

        int left = 0;
        int ones = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {

            if (s.charAt(right) == '1') {
                ones++;
            }

            // We have more than k ones, so move left
            while (ones > k) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }

            // Exactly k ones
            if (ones == k) {

                // Remove leading zeroes to get the shortest substring
                while (left <= right && s.charAt(left) == '0') {
                    left++;
                }

                int len = right - left + 1;

                if (len < minLen) {
                    minLen = len;
                    ans = s.substring(left, right + 1);
                } 
                else if (len == minLen) {
                    String current = s.substring(left, right + 1);

                    if (current.compareTo(ans) < 0) {
                        ans = current;
                    }
                }
            }
        }

        return ans;
    }
}