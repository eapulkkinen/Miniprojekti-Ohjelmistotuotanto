package com.projekti;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class for writing a text file based on given citations.
 */
public class CitationPlainTextWriter {

    /**
     * Writes given citations into a file to plain string format.
     *
     * @param citations list of the citations
     * @param filename  name for the file
     */
    public static void writeToFile(List<Citation> citations, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Citation c : citations) {
                writer.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
