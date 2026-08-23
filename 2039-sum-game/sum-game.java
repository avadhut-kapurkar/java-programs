class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int sumLeft = 0, sumRight = 0;
        int qLeft = 0, qRight = 0;
        
        // Process the left half
        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) == '?') {
                qLeft++;
            } else {
                sumLeft += num.charAt(i) - '0';
            }
        }
        
        // Process the right half
        for (int i = n / 2; i < n; i++) {
            if (num.charAt(i) == '?') {
                qRight++;
            } else {
                sumRight += num.charAt(i) - '0';
            }
        }
        
        // If the total number of '?' is odd, Alice always gets the last move and wins
        if ((qLeft + qRight) % 2 != 0) {
            return true;
        }
        
        // Check if Bob can perfectly balance the sums
        int expectedSumDifference = sumLeft - sumRight + (qLeft - qRight) / 2 * 9;
        
        // If it equals 0, Bob wins (return false). Otherwise, Alice wins (return true).
        return expectedSumDifference != 0;
    }
}