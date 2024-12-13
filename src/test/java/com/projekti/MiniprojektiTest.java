package com.projekti;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the main application.
 */
public class MiniprojektiTest {
    String lineSep = System.getProperty("line.separator");
    String mainStart = "Give a command:"
            + lineSep + "q -> quit"
            + lineSep + "add -> add a citation"
            + lineSep + "add doi -> add a citation using doi"
            + lineSep + "remove -> remove a citation"
            + lineSep;

    String addStart = "Give a type:" + lineSep
            + "0: Inproceedings" + lineSep
            + "1: Article" + lineSep
            + "2: Book" + lineSep;

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(os);

    /**
     * Sets output to printStream before each test.
     */
    @BeforeEach
    public void setOutput() {
        System.setOut(printStream);
    }

    @Test
    public void testMainQuitImmediately() {
        String userInput = "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        String expected = mainStart
                + "Quitting!" + lineSep
                + lineSep
                + "No citations were added" + lineSep;
        assertEquals(expected, actual);
    }

    @Test
    public void testMainInvalidTypeInt() {
        String testNumber = "5";
        String userInput = "add" + lineSep + testNumber + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        String expected = mainStart
                + addStart
                + "Not a valid type! " + testNumber + lineSep
                + mainStart
                + "Quitting!" + lineSep
                + lineSep
                + "No citations were added" + lineSep;
        assertEquals(expected, actual);
    }

    @Test
    public void testMainInvalidTypeNonInt() {
        String testInput = "a";
        String userInput = "add" + lineSep + testInput + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        String expected = mainStart
                + addStart
                + "Wrong input format: java.util.InputMismatchException" + lineSep
                + mainStart
                + "Quitting!" + lineSep + lineSep
                + "No citations were added" + lineSep;
        assertEquals(expected, actual);
    }

    @Test
    public void testMainSuccessfullyAddCitationBook() {
        String userInput = "add" + lineSep + "2"
                + lineSep + "M00"
                + lineSep + "Matti Meikäläinen"
                + lineSep + "Koodauksen perusteet"
                + lineSep + "2000"
                + lineSep + "Otava"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Quitting!" + lineSep
                + lineSep
                + "Citations:" + lineSep
                + "-------------" + lineSep
                + "id: 0" + lineSep
                + "Type: book" + lineSep
                + "Key: M00" + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        if (!actual.contains("Author: Matti Meikäläinen")) {
            correct = false;
        }
        if (!actual.contains("Title: Koodauksen perusteet")) {
            correct = false;
        }
        if (!actual.contains("Year: 2000")) {
            correct = false;
        }
        if (!actual.contains("Publisher: Otava")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainSuccessfullyAddCitationArticle() {
        String userInput = "add" + lineSep + "1"
                + lineSep + "M24"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Quitting!" + lineSep
                + lineSep
                + "Citations:" + lineSep
                + "-------------" + lineSep
                + "id: 0" + lineSep
                + "Type: article" + lineSep
                + "Key: M24" + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        if (!actual.contains("Author: Maija Meikäläinen")) {
            correct = false;
        }
        if (!actual.contains("Title: AI ja koodaamisen tulevaisuus")) {
            correct = false;
        }
        if (!actual.contains("Journal: JYX")) {
            correct = false;
        }
        if (!actual.contains("Year: 2024")) {
            correct = false;
        }
        if (!actual.contains("Volume: 12")) {
            correct = false;
        }
        if (!actual.contains("Pages: 15-23")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainSuccessfullyAddCitationInproceedings() {
        String userInput = "add" + lineSep + "0"
                + lineSep + "PLJ23"
                + lineSep + "Pena Penala, Leevi Leevilä, Joni Jonila"
                + lineSep + "Suuret kielimallit yliopistoissa"
                + lineSep + "2023"
                + lineSep + "Tekoäly ja opiskelu"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Quitting!" + lineSep
                + lineSep
                + "Citations:" + lineSep
                + "-------------" + lineSep
                + "id: 0" + lineSep
                + "Type: inproceedings" + lineSep
                + "Key: PLJ23" + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        if (!actual.contains("Author: Pena Penala, Leevi Leevilä, Joni Jonila")) {
            correct = false;
        }
        if (!actual.contains("Title: Suuret kielimallit yliopistoissa")) {
            correct = false;
        }
        if (!actual.contains("Year: 2023")) {
            correct = false;
        }
        if (!actual.contains("BookTitle: Tekoäly ja opiskelu")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainSuccessfullyAddCitationViaDoi() {
        String userInput = "add doi" + lineSep + "10.30673/sja.119791"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Citations:" + lineSep
                + "-------------" + lineSep
                + "id: 0" + lineSep
                + "Type: article" + lineSep
                + "Key: Kuismin_2022" + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }      
        if (!actual.contains("Title: Palava rakkaus ja öljy pumpulissa: "
                + "Lemmenviestit, huumori ja kirjallistuminen "
                + "suomenkielisessä kirjallisuudessa 1880-luvulta 1900-luvun alkuun")) {
            correct = false;
        }
        if (!actual.contains("Volume: 64")) {
            correct = false;
        }
        if (!actual.contains("Journal: Sananjalka")) {
            correct = false;
        }
        if (!actual.contains("Publisher: Sananjalka")) {
            correct = false;
        }
        if (!actual.contains("Author: Kuismin, Anna")) {
            correct = false;
        }
        if (!actual.contains("Year: 2022")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainInvalidDoi() {
        String userInput = "add doi" + lineSep + "invalid"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Error while fetching. Response code: 404" + lineSep
                + mainStart
                + "Quitting!" + lineSep + lineSep
                + "No citations were added" + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainCommandRemoveInvalidKey() {
        String userInput = "remove" + lineSep  + "invalid" + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Couldn't find citation with key: invalid";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainCommandRemoveSuccessfully() {
        String userInput = "add doi" + lineSep + "10.30673/sja.119791"
                + lineSep + "remove" + lineSep  + "Kuismin_2022" + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Removed citation with key: Kuismin_2022";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainCommandInvalid() {
        String userInput = "invalid" + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Invalid command!" + lineSep + lineSep
                + mainStart
                + "Quitting!" + lineSep + lineSep
                + "No citations were added" + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainEmptyKey() {
        String userInput = "add" + lineSep + "1"
                + lineSep + ""
                + lineSep + "testArticle"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(null);
        String actual = os.toString();
        boolean correct = true;
        if (!actual.contains("Key cannot be empty!")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    // ... more test cases for different scenarios
}