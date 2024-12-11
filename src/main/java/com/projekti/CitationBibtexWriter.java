package com.projekti;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Class for writing a BibTeX entry into a file.
 */
public class CitationBibtexWriter {

    /**
     * Writes given citations into a file.
     *
     * @param citations list of citations to write to a file
     * @param citationsDoi list of citations added via doi
     * @param filename name for the file where citations are written
     */
    public static void writeToFile(
        List<Citation> citations, List<String> citationsDoi, String filename) {
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            for (Citation c : citations) {
                writer.println(c.toBibtexEntry());
            }
            for (String s : citationsDoi) {
                writer.println(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }
}
