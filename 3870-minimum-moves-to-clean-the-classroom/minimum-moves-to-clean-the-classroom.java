import java.util.*;

class Solution {

    static class State {
        int r, c, energy, mask, moves;

        State(int r, int c, int energy, int mask, int moves) {
            this.r = r;
            this.c = c;
            this.energy = energy;
            this.mask = mask;
            this.moves = moves;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startR = -1;
        int startC = -1;

        // Store index of each litter
        int[][] litterIndex = new int[m][n];

        for (int i = 0; i < m; i++) {
            Arrays.fill(litterIndex[i], -1);
        }

        int litterCount = 0;

        // Find S and all L
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                }

                if (ch == 'L') {
                    litterIndex[i][j] = litterCount;
                    litterCount++;
                }
            }
        }

        // If there is no litter
        if (litterCount == 0) {
            return 0;
        }

        // Mask when all litter is collected
        int allMask = (1 << litterCount) - 1;

        /*
         * visited[row][col][mask][energy]
         */
        boolean[][][][] visited =
                new boolean[m][n][1 << litterCount][energy + 1];

        Queue<State> queue = new LinkedList<>();

        // Starting state
        queue.offer(new State(startR, startC, energy, 0, 0));
        visited[startR][startC][0][energy] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            State cur = queue.poll();

            // All litter collected
            if (cur.mask == allMask) {
                return cur.moves;
            }

            // No energy left -> cannot make another move
            if (cur.energy == 0) {
                continue;
            }

            // Try all 4 directions
            for (int d = 0; d < 4; d++) {

                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // One move costs 1 energy
                int newEnergy = cur.energy - 1;

                char cell = classroom[nr].charAt(nc);

                // Reset energy if we reach R
                if (cell == 'R') {
                    newEnergy = energy;
                }

                // Update litter mask
                int newMask = cur.mask;

                if (cell == 'L') {
                    int index = litterIndex[nr][nc];
                    newMask = newMask | (1 << index);
                }

                // If this state was not visited
                if (!visited[nr][nc][newMask][newEnergy]) {

                    visited[nr][nc][newMask][newEnergy] = true;

                    queue.offer(
                        new State(
                            nr,
                            nc,
                            newEnergy,
                            newMask,
                            cur.moves + 1
                        )
                    );
                }
            }
        }

        // Impossible to collect all litter
        return -1;
    }
}