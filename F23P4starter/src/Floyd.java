/**
 * The class for the Floyd algorithm.
 * 
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class Floyd {
    /**
     * Method to compute all-pairs shortest paths and return the distance
     * matrix.
     * 
     * @param g
     *            The adjacent graph.
     * @return The distance matrix.
     */
    public static int[][] computeShortestPaths(GraphL g) {
        int n = g.nodeCount();
        int[][] d = new int[n][n];

        // Initialize
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    d[i][j] = 0;
                }
                else {
                    d[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        // Set initial distances
        for (int i = 0; i < n; i++) {
            int[] neighbors = g.neighbors(i);
            for (int j : neighbors) {
                d[i][j] = g.weight(i, j);
            }
        }

        // Apply Floyd
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (d[i][k] != Integer.MAX_VALUE
                        && d[k][j] != Integer.MAX_VALUE && d[i][j] > d[i][k]
                            + d[k][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                    }
                }
            }
        }

        return d;
    }


    /**
     * Method to calculate the diameter of the graph using the distance matrix
     * 
     * @param distanceMatrix
     *            The distance matrix.
     * @return The diameter of the graph.
     */
//    public static int calculateDiameter(int[][] distanceMatrix) {
//        int diameter = 0;
//        for (int i = 0; i < distanceMatrix.length; i++) {
//            for (int j = 0; j < distanceMatrix.length; j++) {
//                if (distanceMatrix[i][j] != Integer.MAX_VALUE
//                    && distanceMatrix[i][j] > diameter) {
//                    diameter = distanceMatrix[i][j];
//                }
//            }
//        }
//        return diameter;
//    }


    public static int calculateDiameter(int[][] distanceMatrix) {
        int diameter = 0;
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix.length; j++) {
                if (i != j && distanceMatrix[i][j] != Integer.MAX_VALUE
                    && distanceMatrix[i][j] > diameter) {
                    diameter = distanceMatrix[i][j];
                }
            }
        }
        return diameter;
    }

}
