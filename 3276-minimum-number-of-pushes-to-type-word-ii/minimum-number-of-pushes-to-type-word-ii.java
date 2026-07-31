import java.util.PriorityQueue;

class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];
        
        // Count frequency of each letter
        for (char ch : word.toCharArray()) {
            freq[ch - 'a']++;
        }
        
        // Use a Max Heap to store and retrieve frequencies in descending order
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        // Only add non-zero frequencies to the heap
        for (int count : freq) {
            if (count > 0) {
                maxHeap.offer(count);
            }
        }
        
        int pushes = 0;
        int position = 0;
        
        // Extract the highest frequency and calculate pushes
        while (!maxHeap.isEmpty()) {
            pushes += maxHeap.poll() * (position / 8 + 1);
            position++;
        }
        
        return pushes;
    }
}