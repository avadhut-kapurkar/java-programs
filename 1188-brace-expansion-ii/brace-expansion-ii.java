class Solution {
    int i = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression);
        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);
        return ans;
    }

    private Set<String> parse(String s) {
        Set<String> result = new HashSet<>();
        result.add("");

        while (i < s.length() && s.charAt(i) != '}') {
            char ch = s.charAt(i);

            if (ch == ',') {
                i++;
                result.addAll(parse(s));
            } else {
                Set<String> next;

                if (ch == '{') {
                    i++;
                    next = parse(s);
                    i++;
                } else {
                    next = new HashSet<>();
                    next.add(String.valueOf(ch));
                    i++;
                }

                result = multiply(result, next);
            }
        }

        return result;
    }

    private Set<String> multiply(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}