package com.projekti;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class to read from a file.
 */
public class TextReader {

    /**
     * Reads the content of a file and prints it.
     *
     * @param filename the name of the file
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