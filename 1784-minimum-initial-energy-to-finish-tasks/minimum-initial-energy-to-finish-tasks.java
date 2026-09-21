import java.util.Arrays;

class Solution {
    public int minimumEffort(int[][] tasks) {
        // Sort tasks based on the difference (minimum - actual) in descending order.
        // We want to prioritize tasks that leave us with the most "buffer" energy.
        Arrays.sort(tasks, (a, b) -> (b[1] - b[0]) - (a[1] - a[0]));
        
        int initialEnergy = 0;
        int currentEnergy = 0;
        
        for (int[] task : tasks) {
            int actual = task[0];
            int minimum = task[1];
            
            // If we don't have enough energy to start this task, 
            // we must have started with more energy initially.
            if (currentEnergy < minimum) {
                initialEnergy += (minimum - currentEnergy);
                currentEnergy = minimum; // Top up the current energy
            }
            
            // Deduct the actual energy used to finish the task
            currentEnergy -= actual;
        }
        
        return initialEnergy;
    }
}