class Solution {
    public boolean uniformArray(int[] nums1) {

        int minEven = Integer.MAX_VALUE;
        int minOdd = Integer.MAX_VALUE;

        for (int x : nums1) {
            if (x % 2 == 0)
                minEven = Math.min(minEven, x);
            else
                minOdd = Math.min(minOdd, x);
        }

        boolean allEven = true;
        boolean allOdd = true;

        for (int x : nums1) {

            // To get EVEN:
            // x itself must be even OR
            // x - smaller ODD = EVEN
            if (x % 2 != 0 && minOdd < x)
                allEven = false;

            if (x % 2 == 0) {
                // already even
            } else {
                allEven = false;
            }

            // To get ODD:
            // x itself odd OR
            // x - smaller EVEN = ODD
            if (x % 2 == 0 && minOdd >= x)
                allOdd = false;
        }

        return allEven || allOdd;
    }
}