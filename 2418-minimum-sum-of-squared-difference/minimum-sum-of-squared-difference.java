class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long[] diff = new long[nums1.length];
        long total = (long) k1 + k2;
        long sum = 0;

        for (int i = 0; i < nums1.length; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            sum += diff[i];
        }

        if (total >= sum) {
            return 0;
        }

        long left = 0, right = 100000;

        while (left < right) {
            long mid = (left + right) / 2;
            long need = 0;

            for (long d : diff) {
                need += Math.max(0, d - mid);
            }

            if (need <= total) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        long ans = 0;
        long used = 0;

        for (long d : diff) {
            long reduced = Math.min(d, left);
            ans += reduced * reduced;
            used += d - reduced;
        }

        long remaining = total - used;

        for (long d : diff) {
            if (remaining == 0) {
                break;
            }

            if (d >= left && left > 0) {
                ans -= left * left;
                ans += (left - 1) * (left - 1);
                remaining--;
            }
        }

        return ans;
    }
}