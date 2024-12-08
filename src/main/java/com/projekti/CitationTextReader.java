package com.projekti;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class for reading a citation from a file.
 */
public class CitationTextReader {
    
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
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }
}