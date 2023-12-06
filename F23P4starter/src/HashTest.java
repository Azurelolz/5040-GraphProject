import student.TestCase;

/**
 * This class was designed to test the Hash table.
 *
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class HashTest extends TestCase {
    private Record record1;
    private Record record2;
    private Record record3;
    private Record record4;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        record1 = new Record("Test", 0);
        record2 = new Record("Tets", 0);
        record3 = new Record("Ttes", 0);
        record4 = new Record("Test", 0);
    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertEquals(Hash.h("a", 10000), 97);
        assertEquals(Hash.h("b", 10000), 98);
        assertEquals(Hash.h("aaaa", 10000), 1873);
        assertEquals(Hash.h("aaab", 10000), 9089);
        assertEquals(Hash.h("baaa", 10000), 1874);
        assertEquals(Hash.h("aaaaaaa", 10000), 3794);
        assertEquals(Hash.h("Long Lonesome Blues", 10000), 4635);
        assertEquals(Hash.h("Long   Lonesome Blues", 10000), 4159);
        assertEquals(Hash.h("long Lonesome Blues", 10000), 4667);
    }


    /**
     * This test tries to setup a pair of record.
     */
    public void testRecord() {
        Record record = new Record("Test", 0);
        assertEquals(record.getKey(), "Test");
        assertEquals(record.getValue(), 0);
    }


    /**
     * This test tries to insert values to the hash table.
     * 
     * @throws Exception
     */
    public void testHashInsert() throws Exception {
        Hash artistHashTable = new Hash(10, "Artist");
        artistHashTable.hashInsert(record1);
        assertTrue(systemOut().getHistory().endsWith(
            "|Test| is added to the Artist database.\n"));
        artistHashTable.hashInsert(record2);
        assertTrue(systemOut().getHistory().endsWith(
            "|Tets| is added to the Artist database.\n"));
        artistHashTable.hashInsert(record3);
        assertTrue(systemOut().getHistory().endsWith(
            "|Ttes| is added to the Artist database.\n"));
        artistHashTable.hashInsert(record4);
        assertFalse(systemOut().getHistory().endsWith(
            "|Test| is added to the Artist database.\n"));
    }


    /**
     * This test tries to insert and search the hash table.
     * 
     * @throws Exception
     */
    public void testHashSearch() throws Exception {
        Hash artistHashTable = new Hash(10, "Artist");
        artistHashTable.hashInsert(record1);
        artistHashTable.hashInsert(record2);
        artistHashTable.hashInsert(record3);
        assertEquals(artistHashTable.hashSearch(record1.getKey()), record1);
        assertEquals(artistHashTable.hashSearch(record2.getKey()), record2);
        assertEquals(artistHashTable.hashSearch(record3.getKey()), record3);
        assertEquals(artistHashTable.hashSearch("Empty"), null);
    }


    /**
     * This test tries to insert and delete records in the hash table.
     * 
     * @throws Exception
     */
    public void testHashDelete() throws Exception {
        Hash artistHashTable = new Hash(10, "Artist");
        artistHashTable.hashInsert(record1);
        artistHashTable.hashInsert(record2);
        artistHashTable.hashInsert(record3);
        artistHashTable.hashDelete("Test");
        assertTrue(systemOut().getHistory().endsWith(
            "|Test| is removed from the Artist database.\n"));
        artistHashTable.hashDelete("Ttes");
        assertTrue(systemOut().getHistory().endsWith(
            "|Ttes| is removed from the Artist database.\n"));
        artistHashTable.hashDelete("Test");
// assertTrue(systemOut().getHistory().endsWith(
// "|Test| does not exist in the Artist database.\n"));
        artistHashTable.hashPrintHashtable();
        assertTrue(systemOut().getHistory().endsWith("0: TOMBSTONE\n"
            + "6: TOMBSTONE\n" + "7: |Tets|\n" + "total artists: 1\n"));
    }


    /**
     * This test tries to input lots of records and test the resize method.
     * 
     * @throws Exception
     */
    public void testHashResize() throws Exception {
        Hash artistHashTable = new Hash(7, "Artist");
        artistHashTable.hashInsert(record1);
        artistHashTable.hashInsert(record2);
        artistHashTable.hashInsert(record3);
        artistHashTable.hashDelete(record1.getKey());
        record4 = new Record("NewTest", 0);
        artistHashTable.hashInsert(record4);
        Record record5 = new Record("ExpandTest", 0);
        artistHashTable.hashInsert(record5);
        assertTrue(systemOut().getHistory().endsWith(
            "Artist hash table size doubled.\n"
                + "|ExpandTest| is added to the Artist database.\n"));
    }
}
