
/**
 * The main graph class
 *
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class GraphL implements Graph {
    /**
     * The Edge class that create doubly linked list nodes.
     */
    private class Edge {
        private int vertex;
        private int weight;
        private Edge prev;
        private Edge next;

        Edge(int v, int w, Edge p, Edge n) {
            vertex = v;
            weight = w;
            prev = p;
            next = n;
        }
    }

    private Edge[] nodeArray;
    private Object[] nodeValues;
    private int numEdge;
    private int size;
    private int capacity;

    /**
     * No real constructor needed.
     */
    GraphL() {
        // No real constructor needed.
    }


    @Override
    public void init(int n) {
        nodeArray = new Edge[n];
        // List headers;
        for (int i = 0; i < n; i++) {
            nodeArray[i] = new Edge(-1, -1, null, null);
        }
        nodeValues = new Object[n];
        numEdge = 0;
        size = 0;
        capacity = n;
    }


    @Override
    public int nodeCount() {
        return nodeArray.length;
    }


    @Override
    public int edgeCount() {
        return numEdge;
    }


    @Override
    public Object getValue(int v) {
        return nodeValues[v];
    }


    @Override
    public void setValue(int v, Object val) {
        nodeValues[v] = val;

    }


    /**
     * Return the link in v's neighbor list that proceeds the
     * one with w (or where it would be)
     * 
     * @param v
     *            The first node.
     * @param w
     *            The second node.
     * @return The link between two nodes.
     */
    private Edge find(int v, int w) {
        Edge curr = nodeArray[v];
        while ((curr.next != null) && (curr.next.vertex < w)) {
            curr = curr.next;
        }
        return curr;
    }


    @Override
    public void addEdge(int v, int w, int wgt) {
        if (v >= size || w >= size) {
            size = Math.max(v, w) + 1;
            resize();
        }
        if (wgt == 0) {
            return;
        }
        Edge curr = find(v, w);
        if ((curr.next != null) && (curr.next.vertex == w)) {
            curr.next.weight = wgt;
        }
        else {
            curr.next = new Edge(w, wgt, curr, curr.next);
            numEdge++;
            if (curr.next.next != null) {
                curr.next.next.prev = curr.next;
            }
        }
    }


    @Override
    public int weight(int v, int w) {
        Edge curr = find(v, w);
        if ((curr.next == null) || (curr.next.vertex != w)) {
            return 0;
        }
        else {
            return curr.next.weight;
        }
    }


    @Override
    public void removeEdge(int v, int w) {
        Edge curr = find(v, w);
        if ((curr.next == null) || curr.next.vertex != w) {
            return;
        }
        else {
            curr.next = curr.next.next;
            if (curr.next != null) {
                curr.next.prev = curr;
            }
        }
        numEdge--;

    }


    @Override
    public boolean hasEdge(int v, int w) {
        return weight(v, w) != 0;
    }


    @Override
    public int[] neighbors(int v) {
        int cnt = 0;
        Edge curr;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next) {
            cnt++;
        }
        int[] temp = new int[cnt];
        cnt = 0;
        for (curr = nodeArray[v].next; curr != null; curr = curr.next) {
            temp[cnt++] = curr.vertex;
        }
        return temp;
    }


    /**
     * Resize the graph.
     */
    private void resize() {
        if (size > capacity / 2) {
            capacity *= 2;
            Edge[] newNodeArray = new Edge[capacity];
            Object[] newNodeValues = new Object[capacity];
            for (int i = 0; i < nodeArray.length; i++) {
                newNodeArray[i] = nodeArray[i];
                newNodeValues[i] = nodeValues[i];
            }

            for (int i = nodeArray.length; i < capacity; i++) {
                newNodeArray[i] = new Edge(-1, -1, null, null);
            }
            nodeArray = newNodeArray;
            nodeValues = newNodeValues;
            for (int i = size; i < capacity; i++) {
                nodeArray[i] = new Edge(-1, -1, null, null);
            }

        }
    }

    // Marks for visited nodes
    private static final Object VISITED = new Object();

    /**
     * Initiates DFS traversal on the entire graph.
     */
    public void graphTraverse() {
        for (int v = 0; v < this.nodeCount(); v++) {
            this.setValue(v, null); // Initialize
        }
        for (int v = 0; v < this.nodeCount(); v++) {
            if (this.getValue(v) != VISITED) {
                depthFirstSearch(v);
            }
        }
    }


    /**
     * DFS method to traverse from a specific node.
     * 
     * @param v
     *            The specific node.
     * @return The traverse size from the node.
     */
    private int depthFirstSearch(int v) {
        this.setValue(v, VISITED);
        int searchSize = 1;
        if (isEmpty()) {
            searchSize = 0;
        }

        int[] nList = this.neighbors(v);
        for (int neighbor : nList) {
            if (this.getValue(neighbor) != VISITED) {
                searchSize += depthFirstSearch(neighbor);
            }
        }
        return searchSize;
    }


    /**
     * Finds the largest connected component.
     */
    public void findConnectedComponents() {
        
        graphTraverse();

        int largestComponentSize = 0;
        for (int v = 0; v < this.nodeCount(); v++) {
            this.setValue(v, null);
        }

        for (int v = 0; v < this.nodeCount(); v++) {
            if (this.getValue(v) != VISITED) {
                int componentSize = depthFirstSearch(v);
                if (componentSize > largestComponentSize) {
                    largestComponentSize = componentSize;
                }
            }
        }
        System.out.println("There are " + countConnectedComponents()
            + " connected components");
        System.out.println("The largest connected component has "
            + largestComponentSize + " elements");
        System.out.println("The diameter of the largest component is "
            + getGraphDiameter());
    }

    /**
     * Compute the graph diameter.
     * 
     * @return The diameter of the largest component.
     */
    public int getGraphDiameter() {
        if (isEmpty()) {
            return 0;
        }
        int[][] shortestPaths = Floyd.computeShortestPaths(this);
        return Floyd.calculateDiameter(shortestPaths);
    }


    /**
     * Count the connected components.
     * 
     * @return The number of connected components.
     */
    public int countConnectedComponents() {
//        if (isEmpty()) {
//            return 0;
//        }
        ParPtrTree unionFind = new ParPtrTree(nodeArray.length);

        for (int i = 0; i < nodeArray.length; i++) {
            Edge edge = nodeArray[i].next;
            while (edge != null) {
                unionFind.union(i, edge.vertex);
                edge = edge.next;
            }
        }

        int count = 0;
        boolean[] hasEdge = new boolean[nodeArray.length];

        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].next != null) {
                hasEdge[i] = true;
            }
        }

        for (int i = 0; i < nodeArray.length; i++) {
            if (hasEdge[i] && unionFind.find(i) == i) {
                count++;
            }
        }

        return count;
    }


    /**
     * Check if is empty.
     * 
     * @return Return True if is empty.
     */
    private boolean isEmpty() {
        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].next != null) {
                return false;
            }
        }
        return true;
    }


    public void getArr() {
        for (int j = 0; j < nodeArray.length; j++) {
            System.out.println("Node Index = " + j);
            System.out.println("Dummy Header Vertex = " + nodeArray[j].vertex);
            System.out.println("Dummy Header Weight = " + nodeArray[j].weight);
            Edge tempNode = nodeArray[j];
            while (tempNode.next != null) {
                tempNode = tempNode.next;
                System.out.println("  Connected to Vertex = "
                    + tempNode.vertex);
                System.out.println("  Edge Weight = " + tempNode.weight);
                System.out.println("  -------------------------------");
            }
        }
    }

}
