package com.projekti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class miniprojektiTest {

    @Test
    public void testMainQuitImmediately() {
        String userInput = "-1" + System.getProperty("line.separator");
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        Miniprojekti.main(null);
        String actual = os.toString();
        String expected = "-1 TO QUIT!" + System.getProperty("line.separator")
                + "Give a type:" + System.getProperty("line.separator")
                + "0: Inproceedings" + System.getProperty("line.separator")
                + "1: Article" + System.getProperty("line.separator")
                + "2: Book" + System.getProperty("line.separator")
                + "Quitting!" + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "No citations were added" + System.getProperty("line.separator");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testMainInvalidTypeNumber() {
    	String testNumber = "5";
    	String userInput = testNumber + System.getProperty("line.separator") + "-1" + System.getProperty("line.separator");
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        Miniprojekti.main(null);
        String actual = os.toString();
        String expected = "-1 TO QUIT!" + System.getProperty("line.separator")
                + "Give a type:" + System.getProperty("line.separator")
                + "0: Inproceedings" + System.getProperty("line.separator")
                + "1: Article" + System.getProperty("line.separator")
                + "2: Book" + System.getProperty("line.separator")
            	+ "Not a valid type! " + testNumber + System.getProperty("line.separator")
            	+ "-1 TO QUIT!" + System.getProperty("line.separator")
                + "Give a type:" + System.getProperty("line.separator")
                + "0: Inproceedings" + System.getProperty("line.separator")
                + "1: Article" + System.getProperty("line.separator")
                + "2: Book" + System.getProperty("line.separator")
                + "Quitting!" + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "No citations were added" + System.getProperty("line.separator");
        assertEquals(expected, actual);
    }
}