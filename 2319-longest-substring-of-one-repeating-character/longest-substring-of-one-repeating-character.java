class Solution {
    class Node {
        int len, pref, suff, best;
        char leftChar, rightChar;
    }

    Node[] tree;
    char[] arr;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        arr = s.toCharArray();
        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = tree[1].best;
        }

        return ans;
    }

    private void build(int idx, int l, int r) {
        tree[idx] = new Node();

        if (l == r) {
            tree[idx].len = tree[idx].pref = tree[idx].suff = tree[idx].best = 1;
            tree[idx].leftChar = tree[idx].rightChar = arr[l];
            return;
        }

        int mid = (l + r) / 2;
        build(idx * 2, l, mid);
        build(idx * 2 + 1, mid + 1, r);

        pull(idx);
    }

    private void update(int idx, int l, int r, int pos, char c) {
        if (l == r) {
            arr[pos] = c;
            tree[idx].leftChar = tree[idx].rightChar = c;
            tree[idx].len = tree[idx].pref = tree[idx].suff = tree[idx].best = 1;
            return;
        }

        int mid = (l + r) / 2;

        if (pos <= mid) {
            update(idx * 2, l, mid, pos, c);
        } else {
            update(idx * 2 + 1, mid + 1, r, pos, c);
        }

        pull(idx);
    }

    private void pull(int idx) {
        Node left = tree[idx * 2];
        Node right = tree[idx * 2 + 1];
        Node cur = tree[idx];

        cur.len = left.len + right.len;
        cur.leftChar = left.leftChar;
        cur.rightChar = right.rightChar;

        cur.pref = left.pref;
        if (left.pref == left.len && left.rightChar == right.leftChar) {
            cur.pref = left.len + right.pref;
        }

        cur.suff = right.suff;
        if (right.suff == right.len && left.rightChar == right.leftChar) {
            cur.suff = right.len + left.suff;
        }

        cur.best = Math.max(left.best, right.best);

        if (left.rightChar == right.leftChar) {
            cur.best = Math.max(cur.best, left.suff + right.pref);
        }
    }
}