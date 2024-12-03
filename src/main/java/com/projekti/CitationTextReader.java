package com.projekti;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class CitationTextReader{
    
    public static void ReadFromTextFile(){
        try (Scanner input = new Scanner(new File("UTF-8"))){
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