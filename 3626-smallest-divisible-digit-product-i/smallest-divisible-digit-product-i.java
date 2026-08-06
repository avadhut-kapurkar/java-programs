class Solution {
    public int smallestNumber(int n, int t) {
        while (true) {
            int product = 1;
            int x = n;

            // Calculate product of digits
            while (x > 0) {
                product *= (x % 10);
                x /= 10;
            }

            // Check divisibility
            if (product % t == 0) {
                return n;
            }

            n++;
        }
    }
}
