class Solution {
    public boolean uniformArray(int[] nums1) {
        boolean hasOdd = false;
        boolean hasEven = false;

        for (int num : nums1) {
            if (num % 2 == 0)
                hasEven = true;
            else
                hasOdd = true;
        }

        // If all numbers already have same parity
        if (!hasOdd || !hasEven)
            return true;

        // If both odd and even numbers exist,
        // subtracting an opposite-parity number changes parity.
        return true;
    }
}