package com.projekti;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CitationTextReader{
    
    public static void ReadFromTextFile(String filename){
        try (Scanner input = new Scanner(new File(filename))){
            while (input.hasNextLine()){
                System.out.println(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }
}