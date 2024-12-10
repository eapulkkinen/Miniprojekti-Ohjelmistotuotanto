package com.projekti;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

<<<<<<< HEAD:src/main/java/com/projekti/TextReader.java
public class TextReader{
=======
/**
 * Class for reading a citation from a file.
 */
public class CitationTextReader {
>>>>>>> 195cf8134f0a05cbb79cc984431ab6a2a3aed987:src/main/java/com/projekti/CitationTextReader.java
    
    /**
     * Reads all lines of text from given file and prints it out.
     *
     * @param filename name of file which is read
     */
    public static void readFromTextFile(String filename) {
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNextLine()) {
                System.out.println(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An error occurred while reading the file.");
            throw e;
        }
    }
}