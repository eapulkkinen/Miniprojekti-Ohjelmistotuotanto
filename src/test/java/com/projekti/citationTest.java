package com.projekti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class citationTest {

    @Test
    public void testToString() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "Author1, Author2");
        data.put(DataType.Title, "someTitle");
        data.put(DataType.Year, "2020");
        data.put(DataType.Publisher, "somePublisher");
        
        Citation cit = new Citation(0, 2, "TEST1", data);
        String result = "id: 0\n"
                + "Type: Book\n"
                + "Key: TEST1\n"     
                + "Author: Author1, Author2\n"
                + "Title: someTitle\n"
                + "Publisher: somePublisher\n"
                + "Year: 2020\n";
        assertEquals(cit.toString(), result);
        
        Map<DataType, String> data2 = new HashMap<DataType, String>();
        data2.put(DataType.Author, "someAuthor");
        data2.put(DataType.Title, "aTitle");
        data2.put(DataType.Year, "2005");
        data2.put(DataType.BookTitle, "aBook");;
        
        Citation cit2 = new Citation(1, 0, "TEST2", data2);
        String result2 = "id: 1\n"
                + "Type: Inproceedings\n"
                + "Key: TEST2\n"
                + "Author: someAuthor\n"
                + "Title: aTitle\n"
                + "Year: 2005\n"
                + "BookTitle: aBook\n";
        assertEquals(cit2.toString(), result2);
    }
    
    @Test
    public void testToBibTeXEntry() {
        Map<DataType, String> data = new HashMap<DataType, String>();
        data.put(DataType.Author, "Author1, Author2");
        data.put(DataType.Title, "someTitle");
        data.put(DataType.Year, "2020");
        data.put(DataType.Publisher, "somePublisher");
        
        Citation cit = new Citation(0, 2, "TEST1", data);
        String result = "@Book{TEST1,\n"
                + "author = {Author1, Author2},\n"
                + "title = {someTitle},\n"
                + "publisher = {somePublisher},\n"
                + "year = {2020},\n"
                + "}";
        assertEquals(cit.toBibTeXEntry(), result);
        
        Map<DataType, String> data2 = new HashMap<DataType, String>();
        data2.put(DataType.Author, "someAuthor");
        data2.put(DataType.Title, "aTitle");
        data2.put(DataType.Year, "2005");
        data2.put(DataType.BookTitle, "aBook");;
        
        Citation cit2 = new Citation(1, 0, "TEST2", data2);
        String result2 = "@Inproceedings{TEST2,\n"
                + "author = {someAuthor},\n"
                + "title = {aTitle},\n"
                + "year = {2005},\n"
                + "booktitle = {aBook},\n"
                + "}";
        assertEquals(cit2.toBibTeXEntry(), result2);
    }

    // ... more test cases for different scenarios
}
