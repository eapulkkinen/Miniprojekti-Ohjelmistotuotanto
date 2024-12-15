package com.projekti;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TextReaderTest {
    @Test
    void testReadFromTextFile_NormalCase() throws IOException {
        File testFile = File.createTempFile("testFile", ".txt");
        testFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, World!\n");
            writer.write("This is a test file.");
        }

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        TextReader.readFromTextFile(testFile.getAbsolutePath());
        String expectedOutput = "Hello, World!\nThis is a test file.\n";
        assertEquals(expectedOutput, outContent.toString());
        System.setOut(System.out);
    }

    @Test
    void testReadFromTextFile_FileNotFound() {
        java.io.ByteArrayOutputStream errContent = new java.io.ByteArrayOutputStream();
        System.setErr(new java.io.PrintStream(errContent));
        String nonExistentFile = "non_existent_file.txt";
        assertThrows(FileNotFoundException.class, () -> {
            TextReader.readFromTextFile(nonExistentFile);
        });
        assertTrue(errContent.toString().contains("File not found: " + nonExistentFile));
        System.setErr(System.err);
    }

    @Test
    void testReadFromTextFile_GenericException() {
        assertThrows(NullPointerException.class, () -> {
            TextReader.readFromTextFile(null);
        });
    }
}

