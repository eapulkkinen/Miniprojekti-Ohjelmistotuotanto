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
    public void testMainSuccessfullyAddCitation() {
        String userInput = "2" + System.getProperty("line.separator") + "TEST3"
                + System.getProperty("line.separator") + "testAuthor" +
                System.getProperty("line.separator") + "testTitle" +
                System.getProperty("line.separator") + "2000" +
                System.getProperty("line.separator") + "testPublisher" +
                System.getProperty("line.separator") + "-1" + System.getProperty("line.separator");
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(os);
        System.setOut(printStream);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedBeginning = "Quitting!" + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "Citations" + System.getProperty("line.separator")
                + "-------------" + System.getProperty("line.separator")
                + "id: 0" + System.getProperty("line.separator")
                + "Type: Book" + System.getProperty("line.separator")
                + "Key: TEST3" + System.getProperty("line.separator");
        if (!actual.contains(expectedBeginning)) {
            correct = false;
        }
        if (!actual.contains("Publisher: testPublisher")) {
            correct = false;
        }
        if (!actual.contains("Author: testAuthor")) {
            correct = false;
        }
        if (!actual.contains("Title: testTitle")) {
            correct = false;
        }
        if (!actual.contains("Year: 2000")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    // ... more test cases for different scenarios
}