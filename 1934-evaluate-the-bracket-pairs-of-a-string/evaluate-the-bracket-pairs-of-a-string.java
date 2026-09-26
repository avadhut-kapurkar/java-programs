import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        // Step 1: Store knowledge in a Map for O(1) lookups
        Map<String, String> dict = new HashMap<>();
        for (List<String> list : knowledge) {
            dict.put(list.get(0), list.get(1));
        }
        
        StringBuilder result = new StringBuilder();
        StringBuilder currentKey = new StringBuilder();
        boolean inBracket = false;
        
        // Step 2: Traverse the string
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                inBracket = true;
            } else if (c == ')') {
                // Bracket closes, evaluate the key
                String key = currentKey.toString();
                result.append(dict.getOrDefault(key, "?"));
                
                // Reset the key builder and flag for the next pair
                currentKey.setLength(0); 
                inBracket = false;
            } else {
                if (inBracket) {
                    // Collect characters for the key
                    currentKey.append(c);
                } else {
                    // Normal characters outside brackets
                    result.append(c);
                }
            }
        }
        
        return result.toString();
    }
}