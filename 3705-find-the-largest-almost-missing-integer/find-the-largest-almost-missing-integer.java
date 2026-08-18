import java.util.*;

class Solution {
    public int largestInteger(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();

        // Every subarray of size k
        for (int i = 0; i <= nums.length - k; i++) {

            // Set avoids counting the same number twice
            // inside one subarray
            Set<Integer> set = new HashSet<>();

            for (int j = i; j < i + k; j++) {
                set.add(nums[j]);
            }

            // Count how many subarrays contain each number
            for (int num : set) {
                count.put(num, count.getOrDefault(num, 0) + 1);
            }
        }

        int ans = -1;

        // Find largest number appearing in exactly one subarray
        for (int num : count.keySet()) {
            if (count.get(num) == 1) {
                ans = Math.max(ans, num);
            }
        }

        return ans;
    }
}