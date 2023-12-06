/**
 * This is the controller of the program.
 * It uses a Graph and two HashTables for storage and retrieval of records.
 * 
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class Database {
    private Hash artistHashTable;
    private Hash songHashTable;
    private GraphL graph;
    private int nextNodeId;

    /**
     * Create a new database with the specified sizes for the hash table and
     * graph.
     * 
     * @param hashTableSize
     *            The size of the HashTable.
     */
    public Database(int hashTableSize) {
        artistHashTable = new Hash(hashTableSize, "Artist");
        songHashTable = new Hash(hashTableSize, "Song");
        graph = new GraphL();
        graph.init(hashTableSize);
        nextNodeId = 0;
    }


    /**
     * Insert a pair of song and artist into hash table.
     * 
     * @param artist
     *            The input artist name.
     * @param song
     *            The input song name.
     * @throws Exception
     */
    public void insert(String artist, String song) throws Exception {
        int artistId;
        int songId;

        Record artistRecord = artistHashTable.hashSearch(artist);
        Record songRecord = songHashTable.hashSearch(song);

        boolean artistExists = artistRecord != null;
        boolean songExists = songRecord != null;

        if (artistExists) {
            artistId = artistRecord.getValue();
        }
        else {
            artistId = nextNodeId++;
            artistRecord = new Record(artist, artistId);
            artistHashTable.hashInsert(artistRecord);
            graph.addEdge(artistId, artistId, 1);
        }

        if (songExists) {
            songId = songRecord.getValue();
        }
        else {
            songId = nextNodeId++;
            songRecord = new Record(song, songId);
            songHashTable.hashInsert(songRecord);
            graph.addEdge(songId, songId, 1);
        }

        if (artistExists && songExists) {
            if (graph.hasEdge(artistId, songId)) {
                System.out.println("|" + artist + "<SEP>" + song
                    + "| duplicates a record already in the database.");
                return; // Skip adding edge as it's a duplicate
            }
        }

        graph.addEdge(artistId, songId, 1);
        graph.addEdge(songId, artistId, 1);
    }


    /**
     * Remove a specific artist record from hash table.
     * 
     * @param artist
     *            The artist name to remove.
     */
    public void removeArtist(String artist) {
        Record artistRecord = artistHashTable.hashSearch(artist);
        if (artistRecord != null) {
            artistHashTable.hashDelete(artist);
            removeNodeAndEdges(artistRecord.getValue());
//            graph.removeNode(artistRecord.getValue());
        }
        else {
            System.out.println("|" + artist
                + "| does not exist in the Artist database.");
        }
    }


    /**
     * Remove a specific song record from hash table.
     * 
     * @param song
     *            The song name to remove.
     */
    public void removeSong(String song) {
        Record songRecord = songHashTable.hashSearch(song);
        if (songRecord != null) {
            songHashTable.hashDelete(song);
            removeNodeAndEdges(songRecord.getValue());
//            graph.removeNode(songRecord.getValue());
        }
        else {
            System.out.println("|" + song
                + "| does not exist in the Song database.");
        }
    }


    /**
     * Remove node and edges from graph.
     * 
     * @param nodeId
     *            The targeted node id.
     */
    private void removeNodeAndEdges(int nodeId) {
        for (int i = 0; i < graph.nodeCount(); i++) {
            if (graph.hasEdge(nodeId, i)) {
//                graph.getArr();
                graph.removeEdge(nodeId, i);
//                System.out.println("== After first remove ==");
//                graph.getArr();
            }
            
            if (graph.hasEdge(i, nodeId)) {
                graph.removeEdge(i, nodeId);
//                System.out.println("== After second remove ==");
//                graph.getArr();
            }
        }
    }


    /**
     * Print all artist in the hash table.
     */
    public void printSong() {
        songHashTable.hashPrintHashtable();
    }


    /**
     * Print all song in the hash table.
     */
    public void printArtist() {
        artistHashTable.hashPrintHashtable();
    }


    /**
     * Print the details of the graph.
     */
    public void printGraph() {
//        Traversal traversal = new Traversal();
//        traversal.graphTraverse(graph);
//        traversal.findConnectedComponents();
        graph.findConnectedComponents();
    }

}
