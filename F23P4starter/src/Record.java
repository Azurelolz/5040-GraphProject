/**
 * The Record class represents a record in the hash table, associating a unique
 * key(id) and a value(Handle).
 * 
 * @author Yu-Kai Lo
 * @version 1.0
 */
public class Record {
    private String key;
    private int value;

    /**
     * Create a new Record with the specified key and value.
     * 
     * @param key
     *            The unique key associated with the record.
     * @param value
     *            The value to be associated with the key.
     */
    public Record(String key, int value) {
        this.key = key;
        this.value = value;
    }


    /**
     * Gets the key of the specific record.
     *
     * @return The key of the record.
     */
    public String getKey() {
        return key;
    }


    /**
     * Gets the value of the specific record.
     *
     * @return The value of the record.
     */
    public int getValue() {
        return value;
    }

}
