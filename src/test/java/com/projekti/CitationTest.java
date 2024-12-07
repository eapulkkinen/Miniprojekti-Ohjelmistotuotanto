package com.projekti;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;


/**
 * Test class for the Citation.java class.
 */
public class CitationTest {

    String lineSep = System.getProperty("line.separator");

    @Test
    public void testToStringBook() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "Kimmo Kirjailija, Anne Authori");
        data.put(DataType.Title, "Kukkia ja muita kasveja");
        data.put(DataType.Year, "2020");
        data.put(DataType.Publisher, "Like");
        
        Citation cit = new Citation(0, 2, "testBook", data);
        String citString = cit.toString();
        boolean correct = true;
        if (!citString.startsWith("id: 0" + lineSep
                + "Type: Book" + lineSep
                + "Key: testBook" + lineSep)) {
            correct = false;
        }
        if (!citString.contains("Author: Kimmo Kirjailija, Anne Authori")) {
            correct = false;
        }
        if (!citString.contains("Title: Kukkia ja muita kasveja")) {
            correct = false;
        }
        if (!citString.contains("Publisher: Like")) {
            correct = false;
        }
        if (!citString.contains("Year: 2020")) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testToStringInproceedings() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "Kile Kirjanen");
        data.put(DataType.Title, "Kilen kaikki");
        data.put(DataType.Year, "2005");
        data.put(DataType.BookTitle, "Kilen kirja");
        
        Citation cit = new Citation(1, 0, "testInproceeding", data);
        String citString = cit.toString();
        boolean correct = true;
        if (!citString.startsWith("id: 1" + lineSep
                + "Type: Inproceedings" + lineSep
                + "Key: testInproceeding" + lineSep)) {
            correct = false;
        }
        if (!citString.contains("Author: Kile Kirjanen")) {
            correct = false;
        }
        if (!citString.contains("Title: Kilen kaikki")) {
            correct = false;
        }
        if (!citString.contains("Year: 2005")) {
            correct = false;
        }
        if (!citString.contains("BookTitle: Kilen kirja")) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testToBibtexEntryBook() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "Kimmo Kirjailija, Anne Authori");
        data.put(DataType.Title, "Kukkia ja muita kasveja");
        data.put(DataType.Year, "2020");
        data.put(DataType.Publisher, "Like");
        
        Citation cit = new Citation(0, 2, "bibtexBook", data);
        String citBibTeX = cit.toBibtexEntry();
        boolean correct = true;
  
        if (!citBibTeX.startsWith("@Book{bibtexBook,\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("author = {Kimmo Kirjailija, Anne Authori},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("title = {Kukkia ja muita kasveja},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("year = {2020},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("publisher = {Like},\n")) {
            correct = false;
        }
        if (!citBibTeX.endsWith("}")) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testToBibtexEntryInproceedings() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "Kile Kirjanen");
        data.put(DataType.Title, "Kilen kaikki");
        data.put(DataType.Year, "2005");
        data.put(DataType.BookTitle, "Kilen kirja");
        
        Citation cit = new Citation(1, 0, "bibtexInproceeding", data);
        String citBibTeX = cit.toBibtexEntry();
        boolean correct = true;
  
        if (!citBibTeX.startsWith("@Inproceedings{bibtexInproceeding,\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("author = {Kile Kirjanen},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("title = {Kilen kaikki},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("year = {2005},\n")) {
            correct = false;
        }
        if (!citBibTeX.contains("booktitle = {Kilen kirja},\n")) {
            correct = false;
        }
        if (!citBibTeX.endsWith("}")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    // ... more test cases for different scenarios
}
