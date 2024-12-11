package com.projekti;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test class for CitationPlainTextWriter class.
 */
public class CitationPlainTextWriterTest {

    @Test
    public void testFileNotFoundException() throws FileNotFoundException {
        List<Citation> citations = new ArrayList<>();
        String invalidFilePath = "invalid/path/to/file.txt";
        CitationPlainTextWriter.writeToFile(citations, invalidFilePath);
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
        List<Citation> citations = new ArrayList<>();
        String invalidFilePath = null;  
        assertThrows(NullPointerException.class, () -> {
            CitationPlainTextWriter.writeToFile(citations, invalidFilePath);
        });
    }
}
