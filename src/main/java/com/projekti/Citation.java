package com.projekti;

/**
 * @author pelti
 * @version 1.12.2024
 *
 */
public class Citation {
    private final int id;
    private int type; // I think this should be straight up enum instead of int /Pekka
    private String key;
    private String data;

    // All the different citation types
    protected enum Type {
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
    public Citation(int id, int type, String key, String data) {
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
            + "Type: " + Type.values()[type] + "\n"
            + "Key: " + key + "\n"
            + "Data: \n" + data);
        return sb.toString();
    } 
    
    
    /**
     * @return citation as BibTeX entry string
     */
    public String getBibTeXEntry() {
        StringBuilder sb = new StringBuilder();
        sb.append("@" + Type.values()[type] + "{" + key + ",\n");
        sb.append(data);
        sb.append("\n}");
        return sb.toString();
    }
}