/**
 * The interface of the Graph
 *
 * @author Yu-Kai Lo
 * @version 1.0
 */
public interface Graph {
    /**
     * Initialize the graph with some number of vertices.
     * 
     * @param n
     *            The size of the graph.
     */
    void init(int n);


    /**
     * Return the number of vertices.
     * 
     * @return The number of vertices.
     */
    int nodeCount();


    /**
     * Return the current number of edges.
     * 
     * @return The current number of edges.
     */
    int edgeCount();


    /**
     * Get the value of node with index v.
     * 
     * @param v
     *            The node index.
     * @return The value of the node.
     */
    String getValue(int v);


    /**
     * Set the value of node with index v.
     * 
     * @param v
     *            The node index.
     * @param val
     *            The value of the node.
     */
    void setValue(int v, String val);


    /**
     * Adds a new edge from node v to node w with weight wgt.
     * 
     * @param v
     *            The origin node.
     * @param w
     *            The adjacent node.
     * @param wgt
     *            The weight between two nodes.
     */
    void addEdge(int v, int w, int wgt);


    /**
     * Get the weight value for an edge.
     * 
     * @param v
     *            The origin node.
     * @param w
     *            The adjacent node.
     * @return The weight value between two nodes.
     */
    int weight(int v, int w);


    /**
     * Removes the edge from the graph.
     * 
     * @param v
     *            The origin node.
     * @param w
     *            The target node.
     */
    void removeEdge(int v, int w);


    /**
     * Returns true if the graph has the edge.
     * 
     * @param v
     *            The first node.
     * @param w
     *            The second node.
     * @return If two nodes have edge.
     */
    boolean hasEdge(int v, int w);


    /**
     * Returns an array containing the indices of the neighbors of v.
     * 
     * @param v
     *            The node.
     * @return The neighbors of the node.
     */
    int[] neighbors(int v);

}
