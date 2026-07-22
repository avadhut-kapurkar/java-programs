import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        
        // Count global '1's
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                totalOnes++;
            }
        }

        // Extract all contiguous zero blocks
        // zeroBlocks stores: {startIndex, endIndex, length}
        List<int[]> zeroBlocks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                int st = i;
                while (i < n && s.charAt(i) == '0') i++;
                zeroBlocks.add(new int[]{st, i - 1, i - st});
                i--; // adjust due to outer loop increment
            }
        }

        int m = zeroBlocks.size();
        int[][] st = null;
        int[] log2 = null;

        // Build Sparse Table for O(1) Range Maximum Query (RMQ) on adjacent block sums
        if (m >= 2) {
            int wSize = m - 1;
            log2 = new int[wSize + 1];
            log2[1] = 0;
            for (int i = 2; i <= wSize; i++) {
                log2[i] = log2[i / 2] + 1;
            }
            
            int k = log2[wSize] + 1;
            st = new int[k][wSize];
            
            for (int i = 0; i < wSize; i++) {
                st[0][i] = zeroBlocks.get(i)[2] + zeroBlocks.get(i + 1)[2];
            }
            
            for (int j = 1; j < k; j++) {
                for (int i = 0; i + (1 << j) <= wSize; i++) {
                    st[j][i] = Math.max(st[j - 1][i], st[j - 1][i + (1 << (j - 1))]);
                }
            }
        }

        int[] ansArray = new int[queries.length];
        
        for (int q = 0; q < queries.length; q++) {
            int l = queries[q][0];
            int r = queries[q][1];

            if (m < 2) { 
                ansArray[q] = totalOnes;
                continue;
            }

            // Binary search to find the first zero block covering or trailing after `l`
            int low = 0, high = m - 1, A = -1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (zeroBlocks.get(mid)[1] >= l) {
                    A = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            // If no blocks are within the right boundary
            if (A == -1 || zeroBlocks.get(A)[0] > r) {
                ansArray[q] = totalOnes;
                continue;
            }

            // Binary search to find the last zero block starting before or at `r`
            low = A; 
            high = m - 1;
            int B = -1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (zeroBlocks.get(mid)[0] <= r) {
                    B = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            if (A == B) {
                // Only one zero block in the queried range
                ansArray[q] = totalOnes;
            } else if (A + 1 == B) {
                // Exactly two zero blocks in the queried range
                int zA = Math.min(zeroBlocks.get(A)[1], r) - Math.max(zeroBlocks.get(A)[0], l) + 1;
                int zB = Math.min(zeroBlocks.get(B)[1], r) - Math.max(zeroBlocks.get(B)[0], l) + 1;
                ansArray[q] = totalOnes + zA + zB;
            } else {
                // Three or more zero blocks in the queried range
                int zA = zeroBlocks.get(A)[1] - Math.max(zeroBlocks.get(A)[0], l) + 1;
                int zA1 = zeroBlocks.get(A + 1)[2];
                int gainLeft = zA + zA1;

                int zB1 = zeroBlocks.get(B - 1)[2];
                int zB = Math.min(zeroBlocks.get(B)[1], r) - zeroBlocks.get(B)[0] + 1;
                int gainRight = zB1 + zB;

                int gainMid = 0;
                // Check inner contiguous blocks utilizing the Sparse Table
                if (A + 1 <= B - 2) {
                    int L = A + 1;
                    int R = B - 2;
                    int j = log2[R - L + 1];
                    gainMid = Math.max(st[j][L], st[j][R - (1 << j) + 1]);
                }

                ansArray[q] = totalOnes + Math.max(gainLeft, Math.max(gainRight, gainMid));
            }
        }

        // Convert the array to a List<Integer> to match the required return type
        List<Integer> result = new ArrayList<>(queries.length);
        for (int val : ansArray) {
            result.add(val);
        }
        
        return result;
    }
}