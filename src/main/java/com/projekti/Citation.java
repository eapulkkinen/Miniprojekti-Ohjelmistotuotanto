package com.projekti;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Class for Citations.
 *
 * @author Juuso
 * @version 9.12.2024
 *
 */
public class Citation {
    private final int id;
    private EntryType type;
    private String key;
    private Map<DataType, String> data;

    /**
     * All the different citation types.
     */
    protected enum EntryType {
        Inproceedings,
        Article,
        Book
    }

    /**
     * All the data types for a citation.
     */
    protected enum DataType {
        Author,
        Title,
        Year,
        BookTitle,
        Journal,
        Volume,
        Pages,
        Publisher
    }

    /**
     * Constructor for a citation.
     *
     * @param id unique to a single quote is not the same as a key
     * @param type one of the EntryTypes
     * @param key key of the citation to be searched
     * @param data contents
     */
    public Citation(int id, EntryType type, String key, Map<DataType, String> data) {
        this.id = id;
        this.type = type;
        this.key = key;
        this.data = data;
    }

    /**
     * Returns the citation in decent string format.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id + System.getProperty("line.separator")
            + "Type: " + type.name().toLowerCase() + System.getProperty("line.separator")
            + "Key: " + key + System.getProperty("line.separator")
        );
        for (Entry<DataType, String> v : data.entrySet()) {
            sb.append(v.getKey() + ": " + v.getValue() + System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    /**
     * Creates a BibTeX string out of the citation.
     *
     * @return citation as BibTeX entry string
     */
    public String toBibtexEntry() {
        StringBuilder sb = new StringBuilder();
        sb.append("@" + type.name().toLowerCase() + "{" + key + ",\n");
        for (Entry<DataType, String> v : data.entrySet()) {
            sb.append(v.getKey().toString().toLowerCase() + " = {" + v.getValue() + "},\n");
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Returns the key of the citation.
     *
     * @return the key of the citation
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Returns the data of the citation.
     *
     * @return the data of the citation
     */
    public Map<DataType, String> getData() {
        return this.data;
    }
}