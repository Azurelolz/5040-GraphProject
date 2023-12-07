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
    private String[] nodeValues;
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
        nodeValues = new String[n];
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
    public String getValue(int v) {
        return nodeValues[v];
    }


    @Override
    public void setValue(int v, String val) {
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
            String[] newNodeValues = new String[capacity];
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


    /**
     * Print the graph.
     */
    public void printGraph() {
        ParPtrTree unionFind = new ParPtrTree(nodeCount());
        int[] componentSizes = new int[nodeCount()];

        for (int i = 0; i < nodeCount(); i++) {
            for (int j = 0; j < nodeCount(); j++) {
                if (hasEdge(i, j)) {
                    unionFind.union(i, j);
                }
            }
        }

        int componentCount = 0;
        boolean[] visited = new boolean[nodeCount()];

        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].next != null) {
                visited[i] = true;
            }
        }

        for (int i = 0; i < nodeArray.length; i++) {
            if (visited[i] && unionFind.find(i) == i) {
                componentCount++;
            }
            else if (isIsolatedNode(i)) {
                componentCount++;
            }
        }

        for (int i = 0; i < nodeCount(); i++) {
            if (nodeValues[i] != null) {
                int root = unionFind.find(i);
                componentSizes[root]++;
            }
        }

        int maxSize = 0;
        for (int i = 0; i < componentSizes.length; i++) {
            int currentSize = componentSizes[i];
            maxSize = Math.max(maxSize, currentSize);
        }

        int[][] d = new int[nodeCount()][nodeCount()];
        new Floyd(this, d);

        int diameter = 0;
        for (int i = 0; i < nodeCount(); i++) {
            for (int j = 0; j < nodeCount(); j++) {
                if (unionFind.find(i) == unionFind.find(j)
                    && d[i][j] < Integer.MAX_VALUE) {
                    diameter = Math.max(diameter, d[i][j]);
                }
            }
        }

        System.out.println("There are " + componentCount
            + " connected components");
        System.out.println("The largest connected component has " + maxSize
            + " elements");
        System.out.println("The diameter of the largest component is "
            + diameter);
    }


    /**
     * Check whether is isolated node.
     * 
     * @param nodeId
     *            The node to check.
     * @return Return true if it is isolated.
     */
    private boolean isIsolatedNode(int nodeId) {
        return nodeValues[nodeId] != null && (nodeArray[nodeId].next == null
            || nodeArray[nodeId].next.vertex == -1);
    }

}
