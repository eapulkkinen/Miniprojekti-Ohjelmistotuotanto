package com.projekti;

import java.util.Scanner;
import java.io.File;

public class CitationTextReader{
    
    public static void ReadFromTextFile(){
        Scanner input = new Scanner(new File("UTF-8"));
        while (input.hasNextLine()){
            System.out.println(input.nextLine());
        }
        

    }
}