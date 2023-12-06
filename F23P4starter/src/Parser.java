import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Parser class is for parsing commands and data from a given input file and
 * executing corresponding actions on the provided Database.
 * 
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class Parser {

    private Database database;

    /**
     * Create a new Parser with the specified Database.
     *
     * @param database
     *            The Database to perform actions on.
     */
    public Parser(Database database) {
        this.database = database;
    }


    /**
     * Parses commands and data from the input file and perform actions on the
     * Database.
     *
     * @param inputFile
     *            The input file to be parsed.
     * @throws FileNotFoundException
     *             If the file doesn't exist.
     */
    public void fileparser(String inputFile) throws Exception {
        try {
            Scanner sc = new Scanner(new File(inputFile));
            while (sc.hasNext()) {
                String cmd = sc.next();
                String artist;
                String song;
                String item;
                String type;

                switch (cmd) {
                    case "debug":
                        System.out.println("debug cmd activated");
                        break;

                    case "insert":
                        String[] s = sc.nextLine().split("<SEP>");
                        artist = s[0].trim();
                        song = s[1].trim();

                        database.insert(artist, song);
                        break;

                    case "remove":
                        String[] parts = sc.nextLine().trim().split(" ", 2);
                        if (parts.length < 2) {
                            System.out.println("Unrecognized input remove");
                            break;
                        }
                        type = parts[0];
                        item = parts[1];

                        if (type.equals("song")) {
                            database.removeSong(item);
                        }
                        else if (type.equals("artist")) {
                            database.removeArtist(item);
                        }
                        else {
                            System.out.println("Unrecognized input " + cmd);
                        }
                        break;

                    case "print":
                        type = sc.next();
                        if (type.equals("song")) {
                            database.printSong();
                        }
                        else if (type.equals("artist")) {
                            database.printArtist();
                        }
                        else if (type.equals("graph")) {
                            database.printGraph();
                        }
                        else {
                            System.out.println("Unrecognized input " + cmd);
                        }
                        break;

                    default:
                        System.out.println("Unrecognized input " + cmd);
                        break;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("No such file.");
            throw e;
        }
        catch (Exception e) {
            System.out.println("An error occurs while reading the file.");
            throw e;
        }

    }

}
