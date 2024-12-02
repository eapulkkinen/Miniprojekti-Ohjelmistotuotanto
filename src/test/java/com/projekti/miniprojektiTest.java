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
    public void testAddPositiveNumbers() {
        Miniprojekti calculator = new Miniprojekti();
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }

    @Test
    public void testSubtractPositiveNumbers() {
        Miniprojekti calculator = new Miniprojekti();
        int result = calculator.subtract(5, 2);
        assertEquals(3, result);
    }
    
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
        String expected = "-1 TO QUIT!\r\n"
                + "Give a type:\r\n"
                + "0: Inproceedings\r\n"
                + "1: Article\r\n"
                + "2: Book\r\n"
                + "Quitting!\n"
                + "\r\n"
                + "No citations were added\r\n";
        assertEquals(expected, actual);
    }

    // ... more test cases for different scenarios
}