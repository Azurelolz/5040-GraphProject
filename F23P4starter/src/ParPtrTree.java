
/**
 * General Tree implementation for UNION/FIND
 * 
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class ParPtrTree {
    private int[] array; // Node array
    private int[] size;

    /**
     * Initialize the parent pointer tree.
     * 
     * @param size
     *            The size of the parent pointer tree.
     */
    ParPtrTree(int size) {
        array = new int[size]; // Create node array
        this.size = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = -1; // Each node is its own root to start
            this.size[i] = 1;
        }
    }


    /**
     * Merge two subtrees if they are different.
     * 
     * @param a
     *            The first node.
     * @param b
     *            The second node.
     */
    public void union(int a, int b) {
        int root1 = find(a); // Find root of node a
        int root2 = find(b); // Find root of node b
        if (root1 != root2) { // Merge two trees
            array[root1] = root2;
            size[root2] += size[root1];
        }
    }


    /**
     * Return the root of curr's tree.
     * 
     * @param curr
     *            The current node.
     * @return The root of current node's tree.
     */
    public int find(int curr) {
        while (array[curr] != -1) {
            curr = array[curr];
        }
        return curr; // Now at root
    }

}
