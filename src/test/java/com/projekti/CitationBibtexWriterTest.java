package com.projekti;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test class for CitationBibtexWriter class.
 */
public class CitationBibtexWriterTest {

    @Test
    public void testFileNotFoundException() throws FileNotFoundException {
        List<Citation> citations = new ArrayList<Citation>();
        List<String> citationsDoi = new ArrayList<String>();
        String invalidFilePath = "invalid/path/to/file.txt";
        CitationBibtexWriter.writeToFile(citations, citationsDoi, invalidFilePath);
    }

    @SuppressWarnings("resource")
    @Test
    public void testUnsupportedEncodingException() {
        try {
            new PrintWriter("test.txt", "invalid-charset");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Caught UnsupportedEncodingException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testiPoikkeus() {
        List<Citation> citations = new ArrayList<Citation>();
        List<String> citationsDoi = new ArrayList<String>();
        String invalidFilePath = null;
        assertThrows(NullPointerException.class, () -> {
            CitationBibtexWriter.writeToFile(citations, citationsDoi, invalidFilePath);
        });
    }
}
