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
            + lineSep + "modify -> modify a citation"
            + lineSep + "remove -> remove a citation"
            + lineSep + "list -> list citations"
            + lineSep;

    String addStart = "Give a type:" + lineSep
            + "0: Inproceedings" + lineSep
            + "1: Article" + lineSep
            + "2: Book" + lineSep;

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(os);
    String[] args = {"test"};

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
        Miniprojekti.main(args);
        String actual = os.toString();
        String expected = mainStart
                + "Quitting!" + lineSep
                + lineSep
                + "No citations were added!" + lineSep;
        assertEquals(expected, actual);
    }

    @Test
    public void testMainInvalidTypeInt() {
        String testNumber = "5";
        String userInput = "add" + lineSep + testNumber + lineSep + "2" 
                + lineSep + "M00"
                + lineSep + "Matti Meikäläinen"
                + lineSep + "Koodauksen perusteet"
                + lineSep + "2000"
                + lineSep + "Otava"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = mainStart
                + addStart
                + "Not a valid type! " + testNumber + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainInvalidTypeNonInt() {
        String testInput = "a";
        String userInput = "add" + lineSep + testInput + lineSep + "2"
                + lineSep + "M00"
                + lineSep + "Matti Meikäläinen"
                + lineSep + "Koodauksen perusteet"
                + lineSep + "2000"
                + lineSep + "Otava"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = mainStart
                + addStart
                + "Wrong input format: " + testInput + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
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
        Miniprojekti.main(args);
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
        Miniprojekti.main(args);
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
        Miniprojekti.main(args);
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
        Miniprojekti.main(args);
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
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Error while fetching. Response code: 404" + lineSep
                + mainStart
                + "Quitting!" + lineSep + lineSep
                + "No citations were added!" + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainDuplicateDoiCitationKey() {
        String doi = "10.30673/sja.119791";
        String key = "Kuismin_2022";
        String userInput = "add doi" + lineSep + doi
                + lineSep + "add doi" + lineSep + doi 
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Couldn't add citation via doi: " + doi
                + lineSep + "Duplicate key! " + key + lineSep;
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
        Miniprojekti.main(args);
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
        Miniprojekti.main(args);
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
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Invalid command!" + lineSep + lineSep
                + mainStart
                + "Quitting!" + lineSep + lineSep
                + "No citations were added!" + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainEmptyKey() {
        String userInput = "add" + lineSep + "1"
                + lineSep + ""
                + lineSep + "articleAI"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        if (!actual.contains("Key cannot be empty!")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainDuplicateKey() {
        String duplicateKey = "M24";
        String userInput = "add" + lineSep + "1"
                + lineSep + duplicateKey
                + lineSep + "articleAI"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep
                + "add"
                + lineSep + "1"
                + lineSep + duplicateKey
                + lineSep + "M24b" // New key
                + lineSep + "articleAI"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep + "q" + lineSep;
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        if (!actual.contains("Key cannot be a duplicate: " + duplicateKey)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainListCitationsNoCitations() {
        String userInput =  "list" + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        if (!actual.contains("No citations to list!")) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainListAllCitations() {
        String userInput = "add" + lineSep + "1"
                + lineSep + "M24"
                + lineSep + "articleAI"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep + "add" 
                + lineSep + "0"
                + lineSep + "P30"
                + lineSep + "inproAI"
                + lineSep + "Matti Meikänen"
                + lineSep + "2024"
                + lineSep + "AI Lehti"
                + lineSep + "list"
                + lineSep + "all"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Citation keys:" + lineSep
            + "M24" + lineSep + "P30";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainListAllInproceedings() {
        String userInput = "add" 
                + lineSep + "0"
                + lineSep + "P30"
                + lineSep + "inproAI"
                + lineSep + "Matti Meikänen"
                + lineSep + "2024"
                + lineSep + "AI Lehti"
                + lineSep + "list"
                + lineSep + "0"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Key: P30";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainListAllArticles() {
        String userInput = "add" + lineSep + "1"
                + lineSep + "M24"
                + lineSep + "articleAI"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep + "list"
                + lineSep + "1"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Key: M24";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainListAllBooks() {
        String userInput = "add" 
                + lineSep + "2"
                + lineSep + "U689"
                + lineSep + "Pena Kirjoittaja"
                + lineSep + "Minä kirjoitin kirjan"
                + lineSep + "2024"
                + lineSep + "Otava"
                + lineSep + "list"
                + lineSep + "2"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Key: U689";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainListInvalidType() {
        String userInput = "add" 
                + lineSep + "2"
                + lineSep + "U689"
                + lineSep + "Pena Kirjoittaja"
                + lineSep + "Minä kirjoitin kirjan"
                + lineSep + "2024"
                + lineSep + "Otava"
                + lineSep + "list"
                + lineSep + "9"
                + lineSep + "2"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Not a valid type! 9";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainListInvalidInput() {
        String userInput = "add" 
                + lineSep + "2"
                + lineSep + "U689"
                + lineSep + "Pena Kirjoittaja"
                + lineSep + "Minä kirjoitin kirjan"
                + lineSep + "2024"
                + lineSep + "Otava"
                + lineSep + "list"
                + lineSep + "moka"
                + lineSep + "2"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Wrong input format: moka";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainListAttributeNotFound() {
        String userInput = "add" 
                + lineSep + "2"
                + lineSep + "U689"
                + lineSep + "Pena Kirjoittaja"
                + lineSep + "Minä kirjoitin kirjan"
                + lineSep + "2024"
                + lineSep + "Otava"
                + lineSep + "list"
                + lineSep + "3"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Nothing found with the search term Maija Meikäläinen";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainListAttributeIdFound() {
        String userInput = "add" 
                + lineSep + "2"
                + lineSep + "U689"
                + lineSep + "Pena Kirjoittaja"
                + lineSep + "Minä kirjoitin kirjan"
                + lineSep + "2024"
                + lineSep + "Otava"               
                + lineSep + "list"
                + lineSep + "3"
                + lineSep + "0"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "id: 0";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainListAttributeKeyFound() {
        String userInput = "add" 
                + lineSep + "2"
                + lineSep + "U689"
                + lineSep + "Pena Kirjoittaja"
                + lineSep + "Minä kirjoitin kirjan"
                + lineSep + "2024"
                + lineSep + "Otava"
                + lineSep + "list"
                + lineSep + "3"
                + lineSep + "U689"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Key: U689";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainListAttributeFound() {
        String userInput = "add" 
                + lineSep + "2"
                + lineSep + "U689"
                + lineSep + "Pena Kirjoittaja"
                + lineSep + "Minä kirjoitin kirjan"
                + lineSep + "2024"
                + lineSep + "Otava"
                + lineSep + "list"
                + lineSep + "3"
                + lineSep + "Pena Kirjoittaja"
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Author: Pena Kirjoittaja";
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testMainModifyCitationInvalidKey() {
        String invalidKey = "M12";
        String userInput = "add" + lineSep + "1"
                + lineSep + "M24"
                + lineSep + "articleAI"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep
                + "modify"
                + lineSep + invalidKey
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        if (!actual.contains("Input the key of the citation:" + lineSep
                + "Couldn't find citation with key: " + invalidKey)) {
            correct = false;
        }
        assertEquals(true, correct);
    }

    @Test
    public void testMainModifyCitationInvalidAndValidField() {
        String validKey = "M24";
        String validField = "author";
        String invalidField = "autor1";
        String validValue = "Matti Muukalainen";
        String userInput = "add" + lineSep + "1"
                + lineSep + validKey
                + lineSep + "articleAI"
                + lineSep + "Maija Meikäläinen"
                + lineSep + "AI ja koodaamisen tulevaisuus"
                + lineSep + "JYX"
                + lineSep + "2024"
                + lineSep + "12"
                + lineSep + "15-23"
                + lineSep
                + "modify"
                + lineSep + validKey
                + lineSep + invalidField
                + lineSep + validField
                + lineSep + validValue
                + lineSep + "q";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Miniprojekti.main(args);
        String actual = os.toString();
        boolean correct = true;
        String expectedEnding = "Input the key of the citation:" + lineSep
                + "Modifying citation with key: " + validKey + lineSep
                + "Input the field to be modified:" + lineSep
                + "Invalid field: " + invalidField + lineSep
                + "Input the field to be modified:" + lineSep
                + "Input new value:" + lineSep
                + "Updated citation!" + lineSep + lineSep;
        if (!actual.contains(expectedEnding)) {
            correct = false;
        }
        assertEquals(true, correct);
    }
    
    @Test
    public void testGetCitationsFromBibtexFile() {
        Miniprojekti mini = new Miniprojekti();
        mini.getCitationsFromBibtexFile("testBibtex.bib");
        Citation cit = mini.getCitation(0);
        assertEquals("M00", cit.getKey());
    }

    // ... more test cases for different scenarios
}