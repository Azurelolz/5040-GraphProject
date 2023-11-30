import student.TestCase;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import org.junit.Test;

/**
 * This class was designed to test the GraphProject
 *
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class GraphProjectTest extends TestCase {
    private Record record1;
    private Record record2;
    private Record record3;
    private Record record4;

    // ----------------------------------------------------------
    /**
     * Read contents of a file into a string
     * 
     * @param path
     *            File name
     * @return the string
     * @throws IOException
     */
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }


    /**
     * Set up the tests that follow.
     */
    public void setUp() {
        record1 = new Record("Test", 0);
        record2 = new Record("Tets", 0);
        record3 = new Record("Ttes", 0);
        record4 = new Record("Test", 0);
    }


    /**
     * This method is simply to get code coverage of the class declaration.
     */
    public void testQInit() {
        GraphProject it = new GraphProject();
        assertNotNull(it);
    }


    /**
     * This test tries to input null value to the main method.
     * 
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMInitx() throws Exception {
        GraphProject it = new GraphProject();
        assertNotNull(it);
        it.main(null);
        fail();
    }


    /**
     * This test tries to insert records into hash table.
     * 
     * @throws Exception
     */
    public void testDatabaseInsert() throws Exception {
        Database database = new Database(10);
        database.insert("TestArtist", "TestSong");
        assertTrue(systemOut().getHistory().endsWith(
            "|TestArtist| is added to the Artist database.\n"
                + "|TestSong| is added to the Song database.\n"));
        database.insert("TestArtist", "TestSong");
    }


    /**
     * This test tries to remove records from the database.
     * 
     * @throws Exception
     */
    public void testDatabaseRemove() throws Exception {
        Database database = new Database(10);
        database.insert("TestArtist", "TestSong");
        database.insert("TestArtist2", "TestSong2");
        database.removeArtist("TestArtist2");
        assertTrue(systemOut().getHistory().endsWith(
            "|TestArtist2| is removed from the Artist database.\n"));
        database.removeSong("TestSong");
        assertTrue(systemOut().getHistory().endsWith(
            "|TestSong| is removed from the Song database.\n"));
        database.removeSong("TestSong");
        assertTrue(systemOut().getHistory().endsWith(
            "|TestSong| does not exist in the Song database.\n"));
    }


    /**
     * This test tries to print the records in both hash tables.
     * 
     * @throws Exception
     */
    public void testDatabasePrint() throws Exception {
        Database database = new Database(10);
        database.insert("TestArtist", "TestSong");
        database.insert("TestArtist2", "TestSong2");
        database.printSong();
        assertTrue(systemOut().getHistory().endsWith("3: |TestSong|\n"
            + "4: |TestSong2|\n" + "total songs: 2\n"));
        database.printArtist();
        assertTrue(systemOut().getHistory().endsWith("2: |TestArtist|\n"
            + "3: |TestArtist2|\n" + "total artists: 2\n"));
        database.printGraph();
        assertTrue(systemOut().getHistory().endsWith(
            "There are 0 connected components\n"
                + "The largest connected component has 0 elements\n"
                + "The diameter of the largest component is 0\n"));
    }


    /**
     * This test tries to test the input of the parser.
     * 
     * @throws Exception
     */
    public void testParser() throws Exception {
        String[] args = new String[2];
        args[0] = "10";
        args[1] = "P4testInput.txt";
        GraphProject.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P4testOutput.txt");
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * This test tries to test the debug mode of the parser.
     * 
     * @throws Exception
     */
    public void testParserDebug() throws Exception {
        String[] args = new String[2];
        args[0] = "10";
        args[1] = "P4invalidTestInput.txt";
        GraphProject.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P4invalidTestOutput.txt");
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * This test tries to input a non-existing file to the parser.
     * 
     * @throws Exception
     */
    @Test(expected = FileNotFoundException.class)
    public void testNullParser() throws Exception {
        String[] args = new String[2];
        args[0] = "10";
        args[1] = "P4NullInput.txt";
        GraphProject.main(args);
        fail();
    }


    /**
     * This test tries to process an invalid file through parser.
     * 
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testInvalidParser() throws Exception {
        String[] args = new String[2];
        args[0] = "10";
        args[1] = "P4errorInput.txt";
        GraphProject.main(args);
        fail();
    }
}
