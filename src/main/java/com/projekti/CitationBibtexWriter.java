package com.projekti;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class for writing a BibTeX entry into a file.
 */
public class CitationBibtexWriter {

    /**
     * Writes given citations into a file to a bibtex format.
     *
     * @param citations list of the citations
     * @param filename  name for the file
     */
    public static void writeToFile(List<Citation> citations, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Citation c : citations) {
                writer.println(c.toBibtexEntry());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
