import student.TestCase;
import static org.junit.Assert.assertArrayEquals;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

/**
 * This class was designed to test the GraphProject
 *
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class GraphProjectTest extends TestCase {

    private Graph graph;

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
        graph = new GraphL();
        graph.init(10);
    }


    /**
     * This method is simply to get code coverage of the class declaration.
     */
    @Test
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
    @Test
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
    @Test
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
// assertTrue(systemOut().getHistory().endsWith(
// "|TestSong| does not exist in the Song database.\n"));
    }


    /**
     * This test tries to print the records in both hash tables.
     * 
     * @throws Exception
     */
    @Test
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
            "There are 2 connected components\n"
                + "The largest connected component has 2 elements\n"
                + "The diameter of the largest component is 1\n"));
    }


    /**
     * This test tries to test the input of the parser.
     * 
     * @throws Exception
     */
    @Test
    public void testParser() throws Exception {
        String[] args = new String[2];
        args[0] = "10";
        args[1] = "P4sampleInput.txt";
        GraphProject.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P4sampleOutput.txt");
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * This test tries to test the debug mode of the parser.
     * 
     * @throws Exception
     */
    @Test
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


    /**
     * This test tries to validate the initial count of the graph.
     */
    @Test
    public void testGraphInit() {
        assertEquals(10, graph.nodeCount());
    }


    /**
     * This test tries to add edge between two nodes.
     */
    @Test
    public void testGraphEdge() {
        graph.addEdge(0, 1, 10);
        assertTrue(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(0, 2));
    }


    /**
     * This test tries to validate the weight between two nodes.
     */
    @Test
    public void testGraphWeight() {
        graph.addEdge(0, 1, 1);
        assertEquals(1, graph.weight(0, 1));
    }


    /**
     * This test tries to remove node from the graph.
     */
    @Test
    public void testRemoveEdge() {
        graph.addEdge(0, 1, 20);
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }


    /**
     * This test tries to print all nodes in graph.
     */
    @Test
    public void testNeighbors() {
        graph.addEdge(0, 1, 25);
        graph.addEdge(0, 2, 30);
        int[] neighbors = graph.neighbors(0);
        int[] expectedOutput = { 1, 2 };
        assertArrayEquals(expectedOutput, neighbors);
    }


    /**
     * This test tries to remove existing edges.
     */
    @Test
    public void testRemoveExistingEdge() {
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        assertTrue(graph.hasEdge(0, 1));
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }


    /**
     * This test tries to remove non existing edges.
     */
    @Test
    public void testRemoveNonExistentEdge() {
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        assertFalse(graph.hasEdge(0, 3));
        graph.removeEdge(0, 3);
        assertFalse(graph.hasEdge(0, 3));
    }


    /**
     * This test tries to validate the graph integrity after removal.
     */
    @Test
    public void testGraphIntegrityAfterEdgeRemoval() {
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.removeEdge(0, 1);
        assertTrue(graph.hasEdge(1, 2));
    }


    /**
     * This test tries to do the complex remove test.
     */
    @Test
    public void testMultipleRemovalsAndAdditions() {
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.removeEdge(1, 2);
        graph.removeEdge(2, 3);
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 3));
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 3));
    }
    
    public void testComplex() throws Exception {
        String[] args = new String[2];
        args[0] = "10";
        args[1] = "P4sampleInput2.txt";
        GraphProject.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P4sampleOutput2.txt");
        assertFuzzyEquals(referenceOutput, output);
    }

}
