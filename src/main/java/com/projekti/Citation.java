package com.projekti;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author pelti
 * @version 1.12.2024
 *
 */
public class Citation {
    private final int id;
    private int type; // I think this should be straight up enum instead of int /Pekka
    private String key;
    private Map<DataType, String> data;

    // All the different citation types
    protected enum EntryType {
        Inproceedings,
        Article,
        Book
    }
    

    /**
     * @param id unique to a single quote is not the same as a key.
     * @param type 3 type selection
     * @param key key of the citation to be searched
     * @param data contents
     */
    public Citation(int id, int type, String key, Map<DataType, String> data) {
        this.id = id;
        this.type = type; // either Inproceedings, article or book
        this.key = key;
        this.data = data;  // I think this should not be here (Should be author)
        //this.title = title; // should be added
        //this.year = year; // should be added
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id + "\n"
            + "Type: " + EntryType.values()[type] + "\n"
            + "Key: " + key + "\n"
            //+ "Data: \n" + data);
            );
        for (Entry<DataType, String> v : data.entrySet()) {
            sb.append(v.getKey() + ": " + v.getValue() + "\n");
        }
        return sb.toString();
    } 
    
    
    /**
     * @return citation as BibTeX entry string
     */
    public String toBibTeXEntry() {
        StringBuilder sb = new StringBuilder();
        sb.append("@" + EntryType.values()[type] + "{" + key + ",\n");
        for (Entry<DataType, String> v : data.entrySet()) {
            sb.append(v.getKey().toString().toLowerCase() + " = {" + v.getValue() + "},\n");
        }
        sb.append("}");
        return sb.toString();
    }
}