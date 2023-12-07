/**
 * The class for the Floyd algorithm.
 * 
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class Floyd {
    /**
     * Main Floyd algorithm.
     * 
     * @param g
     *            The adjacent graph.
     * @param d
     *            The distance array.
     */
    public Floyd(GraphL g, int[][] d) {
        int n = g.nodeCount();

        // Initialize the distance matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    d[i][j] = 0;
                }
                else if (g.hasEdge(i, j)) {
                    d[i][j] = g.weight(i, j);
                }
                else {
                    d[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        // Compute shortest paths
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (d[i][k] != Integer.MAX_VALUE
                        && d[k][j] != Integer.MAX_VALUE) {
                        d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                    }
                }
            }
        }
    }
}
