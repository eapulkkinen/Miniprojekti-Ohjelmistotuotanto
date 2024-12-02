package com.projekti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class citationTest {

    @Test
    public void testToStringBook() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "Author1, Author2");
        data.put(DataType.Title, "someTitle");
        data.put(DataType.Year, "2020");
        data.put(DataType.Publisher, "somePublisher");
        
        Citation cit = new Citation(0, 2, "TEST1", data);
        String citString = cit.toString();
        boolean correct = true;
        if (!citString.startsWith("id: 0\n"
                + "Type: Book\n"
                + "Key: TEST1\n")) {
            correct = false;
        }
        if (!citString.contains("Author: Author1, Author2\n")) {
            correct = false;
        }
        if (!citString.contains("Title: someTitle\n")) {
            correct = false;
        }
        if (!citString.contains("Publisher: somePublisher\n")) {
            correct = false;
        }
        if (!citString.contains("Year: 2020\n")) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testToStringInproceedings() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "someAuthor");
        data.put(DataType.Title, "aTitle");
        data.put(DataType.Year, "2005");
        data.put(DataType.BookTitle, "aBook");
        
        Citation cit = new Citation(1, 0, "TEST2", data);
        String citString = cit.toString();
        boolean correct = true;
        if (!citString.startsWith("id: 1\n"
                + "Type: Inproceedings\n"
                + "Key: TEST2\n")) {
            correct = false;
        }
        if (!citString.contains("Author: someAuthor\n")) {
            correct = false;
        }
        if (!citString.contains("Title: aTitle\n")) {
            correct = false;
        }
        if (!citString.contains("Year: 2005\n")) {
            correct = false;
        }
        if (!citString.contains("BookTitle: aBook\n")) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testToBibTeXEntryBook() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "Author1, Author2");
        data.put(DataType.Title, "someTitle");
        data.put(DataType.Year, "2020");
        data.put(DataType.Publisher, "somePublisher");
        
        Citation cit = new Citation(0, 2, "TEST1", data);
        String citBibTeX = cit.toBibTeXEntry();
        boolean correct = true;
  
        if (!citBibTeX.startsWith("@Book{TEST1,\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("author = {Author1, Author2},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("title = {someTitle},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("publisher = {somePublisher},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("year = {2020},\n")) {
            correct = false;
        }
        if (!citBibTeX.endsWith("}")) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testToBibTeXEntryInproceedings() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "someAuthor");
        data.put(DataType.Title, "aTitle");
        data.put(DataType.Year, "2005");
        data.put(DataType.BookTitle, "aBook");
        
        Citation cit = new Citation(1, 0, "TEST2", data);
        String citBibTeX = cit.toBibTeXEntry();
        boolean correct = true;
  
        if (!citBibTeX.startsWith("@Inproceedings{TEST2,\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("author = {someAuthor},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("title = {aTitle},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("year = {2005},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("booktitle = {aBook},\n")) {
            correct = false;
        }
        if (!citBibTeX.endsWith("}")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    // ... more test cases for different scenarios
}
