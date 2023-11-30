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
        Record artistRecord = new Record(artist, 0);
        Record songRecord = new Record(song, 0);
        Record checkArtist = artistHashTable.hashSearch(artist);
        Record checkSong = songHashTable.hashSearch(song);

        if (checkArtist == null) {
            artistHashTable.hashInsert(artistRecord);
        }

        if (checkSong == null) {
            songHashTable.hashInsert(songRecord);
        }

    }


    /**
     * Remove a specific artist record from hash table.
     * 
     * @param artist
     *            The artist name to remove.
     */
    public void removeArtist(String artist) {
        artistHashTable.hashDelete(artist);
    }


    /**
     * Remove a specific song record from hash table.
     * 
     * @param song
     *            The song name to remove.
     */
    public void removeSong(String song) {
        songHashTable.hashDelete(song);
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
        System.out.println("There are 0 connected components");
        System.out.println("The largest connected component has 0 elements");
        System.out.println("The diameter of the largest component is 0");
    }

}
