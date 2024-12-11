package com.projekti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for the application.
 *
 * @author developers
 * @version 8.12.2024
 *
 */
public class Miniprojekti {
    private final String plaintTextFileName = "entries.txt";
    private final String bibFileName = "entries.bib";

    private final List<Citation> citations = new ArrayList<Citation>();
    private int currentId = 0;


    /**
     * Starts the program and reads user input. Constructs citations based on input.
     * Writes to the 2 files.
     *
     * @param args args
     */
    public static void main(String[] args) {
        Miniprojekti mini = new Miniprojekti();
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    int type = mini.getType(scanner);
                    
                    scanner.nextLine();
                    if (!(0 <= type && type <= 2)) {
                        if (type == -1) {
                            System.out.println("Quitting!" + System.getProperty("line.separator"));
                            break;
                        }
                        System.out.println("Not a valid type! " + type);
                        continue;
                    }
                    System.out.println("You gave the number: " + type);

                    String key = null;
                    while (key == null || key.isEmpty()) {
                        if (key != null) {
                            System.out.println("Key cannot be empty!");
                        }
                        key = mini.getKey(scanner);
                    }
                    System.out.println("You gave the key: " + key + "\n");

                    Map<Citation.DataType, String> data = null;
                    Citation.EntryType entryType = Citation.EntryType.Inproceedings;
                    while (data == null) {
                        if (type == 0) {
                            data = mini.getInProceedingsData(scanner);
                        }
                        if (type == 1) {
                            data = mini.getArticleData(scanner);
                            entryType = Citation.EntryType.Article;
                        }
                        if (type == 2) {
                            data = mini.getBookData(scanner);
                            entryType = Citation.EntryType.Book;
                        }
                    }
                    System.out.println("You gave the data: " + data + "\n");

                    // Now keeps track of IDs
                    Citation cit = new Citation(mini.currentId++, entryType, key, data);
                    mini.citations.add(cit);
                    System.out.println("Added citation:\n" + cit + "\n");
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // Infinite loop without
                    System.out.println("Wrong input format: " + e);
                } // finally {
                    // System.out.println("Input something valid");
                    // scanner.close();
                // }
            }
        }
        if (mini.citations.size() == 0) {
            System.out.println("No citations were added");
            return;
        }
        System.out.println("Citations");
        System.out.println("-------------");
        for (int i = 0; i < mini.citations.size(); i++) {
            System.out.println(mini.citations.get(i));
            System.out.println("---");
        }
        CitationPlainTextWriter.writeToFile(mini.citations, mini.plaintTextFileName);
        CitationBibtexWriter.writeToFile(mini.citations, mini.bibFileName);

    }

    private Map<Citation.DataType, String> getArticleData(Scanner scanner) {
        Map<Citation.DataType, String> map = new HashMap<Citation.DataType, String>();
        
        System.out.println("Please input author:");
        map.put(Citation.DataType.Author, scanner.nextLine().trim());

        System.out.println("Please input title:");
        map.put(Citation.DataType.Title, scanner.nextLine().trim());

        System.out.println("Please input journal:");
        map.put(Citation.DataType.Journal, scanner.nextLine().trim());

        System.out.println("Please input year:");
        map.put(Citation.DataType.Year, scanner.nextLine().trim());

        System.out.println("Please input volume:");
        map.put(Citation.DataType.Volume, scanner.nextLine().trim());

        System.out.println("Please input pages:");
        map.put(Citation.DataType.Pages, scanner.nextLine().trim());

        return map;
    }

    private Map<Citation.DataType, String> getBookData(Scanner scanner) {
        Map<Citation.DataType, String> map = new HashMap<Citation.DataType, String>();
        
        System.out.println("Please input author:");
        map.put(Citation.DataType.Author, scanner.nextLine().trim());

        System.out.println("Please input title:");
        map.put(Citation.DataType.Title, scanner.nextLine().trim());

        System.out.println("Please input year:");
        map.put(Citation.DataType.Year, scanner.nextLine().trim());

        System.out.println("Please input publisher:");
        map.put(Citation.DataType.Publisher, scanner.nextLine().trim());
        
        return map;
    }

    private Map<Citation.DataType, String> getInProceedingsData(Scanner scanner) {
        Map<Citation.DataType, String> map = new HashMap<Citation.DataType, String>();
        
        System.out.println("Please input author:");
        map.put(Citation.DataType.Author, scanner.nextLine().trim());

        System.out.println("Please input title:");
        map.put(Citation.DataType.Title, scanner.nextLine().trim());

        System.out.println("Please input year:");
        map.put(Citation.DataType.Year, scanner.nextLine().trim());

        System.out.println("Please input book title:");
        map.put(Citation.DataType.BookTitle, scanner.nextLine().trim());
        
        return map;
    }

    /**
     * Handles the user input for the citation type.
     *
     * @param scanner Scanner object
     * @return integer matching Citation.EntryType
     * @throws InputMismatchException if the input is not an integer
     */
    public int getType(Scanner scanner) {
        System.out.println("-1 TO QUIT!");
        System.out.println("Give a type:");
        System.out.println("0: " + Citation.EntryType.Inproceedings);
        System.out.println("1: " + Citation.EntryType.Article);
        System.out.println("2: " + Citation.EntryType.Book);
        return scanner.nextInt();
    }

    /**
     * Handles the user input for the citation key.
     *
     * @param scanner Scanner object
     * @return trimmed string
     */
    public String getKey(Scanner scanner) {
        System.out.println("Give a key for the citation:");
        return scanner.nextLine().trim();
    }

    // TODO:
    //public String getData(Scanner scanner) {
    //    System.out.println("Give data for the citation");
    //    return scanner.nextLine().trim();
    //}
}