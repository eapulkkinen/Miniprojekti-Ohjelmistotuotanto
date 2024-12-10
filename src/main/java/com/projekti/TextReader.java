package com.projekti;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TextReader{
    
    public static void ReadFromTextFile(String filename){
        try (Scanner input = new Scanner(new File(filename))){
            while (input.hasNextLine()){
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