package com.projekti;

public class Citation {
    private final int id;
    private int type;
    private String key;
    private String data;

    // All the different citation types
    protected enum Type {
        Inproceedings,
        Article,
        Book,
    }

    public Citation(int id, int type, String key, String data) {
        this.id = id;
        this.type = type;
        this.key = key;
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id + "\n"
            + "Type: " + Type.values()[type] + "\n"
            + "Key: " + key + "\n"
            + "Data: " + data);
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