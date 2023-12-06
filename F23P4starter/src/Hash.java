/**
 * The Hash table class stores records with keys and values.
 * It supports insertions, searches, deletions, printing, and resizing.
 * And it is based on the quadratic probe function.
 * 
 * @author yu-Kai Lo
 * @version 1.0
 */

public class Hash {
    private Record[] table;
    private int size;
    private int tableSize;
    private String type;

    /**
     * Constructs a new HashTable with the specified table size.
     * Use for the database initialization.
     *
     * @param inputTableSize
     *            The initial size of the hash table.
     * @param type
     *            The type of the hash table.
     */
    public Hash(int inputTableSize, String type) {
        this.tableSize = inputTableSize;
        table = new Record[tableSize];
        size = 0;
        this.type = type;

    }


    /**
     * Compute the hash function
     * 
     * @param s
     *            The string that we are hashing
     * @param length
     *            Length of the hash table (needed because this method is
     *            static)
     * @return
     *         The hash function value (the home slot in the table for this key)
     */
    public static int h(String s, int length) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % length);
    }


    /**
     * Inserts a record into the hash table.
     *
     * @param record
     *            The record to insert.
     * @throws Exception.
     */
    void hashInsert(Record record) throws Exception {
        String key = record.getKey();
        if (size >= table.length / 2) {
            resize();
        }
        int hashValue = h(key, tableSize);
        int home = hashValue;
        int pos = home;

        int probeCount = 0;
        while (table[pos] != null && probeCount < tableSize) {
            if (table[pos].getKey().equals(key)) {
                return;
            }
            if (table[pos].getKey().equals("TOMBSTONE")) {
                break;
            }
            pos = (home + probeCount * probeCount) % tableSize;
            probeCount++;
        }

        if (probeCount == tableSize) {
            resize();
            hashInsert(record);
            return;
        }

        table[pos] = record;
        size++;
        System.out.println("|" + key + "| is added to the " + type
            + " database.");
    }


    /**
     * Searches for a record with the given key in the hash table.
     *
     * @param key
     *            The key to search for.
     * @return The record with the matching key, or null if not found.
     */
    Record hashSearch(String key) {
        int hashValue = h(key, tableSize);
        int home = hashValue;
        int pos = home;
        int probeCount = 0;

        while (table[pos] != null && probeCount < tableSize) {
            if (table[pos].getKey().equals(key)) {
                return table[pos];
            }
            pos = (home + probeCount * probeCount) % tableSize;
            probeCount++;
        }

        return null;
    }


    /**
     * Deletes a record with the given key from the hash table.
     *
     * @param key
     *            The key of the record to delete.
     */
    void hashDelete(String key) {
        int hashValue = h(key, tableSize);
        int home = hashValue;
        int pos = home;
        int probeCount = 0;

        while (table[pos] != null && probeCount < tableSize) {
            if (table[pos].getKey().equals(key)) {
                table[pos] = new Record("TOMBSTONE", 0);
                size--;
                System.out.println("|" + key + "| is removed from the " + type
                    + " database.");
                return;
            }
            pos = (home + probeCount * probeCount) % tableSize;
            probeCount++;
        }

    }


    /**
     * Preserve all the records and resize the hash table to double size.
     * 
     * @throws Exception
     */
    private void resize() throws Exception {
        int oldSize = tableSize;
        tableSize = tableSize * 2;
        Record[] newTable = new Record[tableSize];
        int maxProbes = tableSize;

        for (int i = 0; i < oldSize; i++) {
            if (table[i] != null && !table[i].getKey().equals("TOMBSTONE")) {
                Record record = table[i];
                String key = record.getKey();
                int hashValue = h(key, tableSize);
                int home = hashValue;
                int pos = home;
                int probeCount = 0;
                while (newTable[pos] != null && probeCount < maxProbes) {
                    pos = (home + probeCount * probeCount) % tableSize;
                    probeCount++;
                }
                if (probeCount == maxProbes) {
                    resize();
                    return;
                }
                newTable[pos] = record;

            }
        }
        table = newTable;
        System.out.println(type + " hash table size doubled.");
    }


    /**
     * Prints the contents of the hash table, including tombStones.
     */
    void hashPrintHashtable() {

        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                if (table[i].getKey().equals("TOMBSTONE")) {
                    System.out.println(i + ": " + table[i].getKey());
                }
                else {
                    System.out.println(i + ": |" + table[i].getKey() + "|");
                }
            }
        }
        System.out.println("total " + type.toLowerCase() + "s: " + size);
    }
}
