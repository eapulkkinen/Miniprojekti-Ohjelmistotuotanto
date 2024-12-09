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
 * @version 11.12.2024
 *
 */
public class Miniprojekti {
    private final String plaintTextFileName = "entries.txt";
    private final String bibFileName = "entries.bib";

    // A list for manually added citations
    private final List<Citation> citations = new ArrayList<Citation>();
    // A list for citations added via doi
    private final List<String> citationsDoi = new ArrayList<String>();
    private int currentId = 0; // TODO: most likely will be removed, no need for id

    /**
     * Starts the program and reads user input. Constructs citations based on input.
     * Writes to the 2 files using writer classes.
     *
     * @param args args
     */
    public static void main(String[] args) {
        Miniprojekti mini = new Miniprojekti();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    String lineSep = System.getProperty("line.separator");
                    String command = mini.getCommand(scanner);
                    boolean invalidCommand = false;
                    if (command.matches("q")) {
                        System.out.println("Quitting!" + lineSep);
                        break;
                    } else if (command.matches("add")) {
                        invalidCommand = mini.addCitation(scanner);
                    } else if (command.matches("add doi")) {
                        invalidCommand = mini.addCitationDoi(scanner);
                    } else if (command.matches("remove")) {
                        System.out.println("Removing not implemented yet!" + lineSep);
                        invalidCommand = true;
                    } else {
                        System.out.println("Invalid command!" + lineSep);
                        invalidCommand = true;
                    }

                    if (invalidCommand) {
                        continue;
                    }
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // Infinite loop without
                    System.out.println("Wrong input format: " + e);
                }
            }
        }
        if (mini.citations.size() == 0 && mini.citationsDoi.size() == 0) {
            System.out.println("No citations were added");
            return;
        }
        mini.printCitations();
        CitationPlainTextWriter.writeToFile(mini.citations, mini.plaintTextFileName);
        CitationBibtexWriter.writeToFile(mini.citations, mini.citationsDoi, mini.bibFileName);
    }

    /**
     * Prints added citations. TODO: remove?
     */
    private void printCitations() {
        System.out.println("Citations:");
        System.out.println("-------------");
        for (int i = 0; i < this.citations.size(); i++) {
            System.out.println(this.citations.get(i));
            System.out.println("---");
        }
        if (this.citationsDoi.size() != 0) {
            System.out.println("Bibtex via doi:");
            for (int i = 0; i < this.citationsDoi.size(); i++) {
                System.out.println(this.citationsDoi.get(i));
                System.out.println("---");
            }
        }
    }

    /**
     * Reads the given command and returns it.
     *
     * @param scanner scanner
     * @return trimmed and lower-cased command
     */
    private String getCommand(Scanner scanner) {
        System.out.println("Give a command:");
        System.out.println("q -> quit");
        System.out.println("add -> add a citation");
        System.out.println("add doi -> add a citation using doi");
        System.out.println("remove -> remove a citation");
        return scanner.nextLine().trim().toLowerCase();
    }

    /**
     * Adds a citation.
     *
     * @param scanner scanner
     * @return true if succesful, otherwise false
     */
    private boolean addCitation(Scanner scanner) {
        int type = this.getType(scanner);
        scanner.nextLine();
        if (!(0 <= type && type <= 2)) {
            System.out.println("Not a valid type! " + type);
            return false;
        }
        System.out.println("You gave the number: " + type);

        String key = null;
        while (key == null || key.isEmpty()) {
            if (key != null) {
                System.out.println("Key cannot be empty!");
            }
            key = getKey(scanner);
        }
        System.out.println("You gave the key: " + key + "\n");

        Map<Citation.DataType, String> data = null;
        Citation.EntryType entryType = Citation.EntryType.Inproceedings;
        while (data == null) {
            if (type == 0) {
                data = this.getInProceedingsData(scanner);
            }
            if (type == 1) {
                data = this.getArticleData(scanner);
                entryType = Citation.EntryType.Article;
            }
            if (type == 2) {
                data = this.getBookData(scanner);
                entryType = Citation.EntryType.Book;
            }
        }
        System.out.println("You gave the data: " + data + "\n");

        // Now keeps track of IDs
        Citation cit = new Citation(this.currentId++, entryType, key, data);
        this.citations.add(cit);
        System.out.println("Added citation:\n" + cit);
        return true;
    }

    /**
     * Adds a citation using doi.
     *
     * @param scanner scanner
     * @return true if succesful, otherwise false
     */
    private boolean addCitationDoi(Scanner scanner) {
        System.out.println("Input doi:");
        String doi = scanner.next().trim();
        scanner.nextLine();
        String result = BibtexFetcher.fetchBibtex(doi);
        if (result == null) {
            return false;
        }
        result = BibtexFetcher.formatBibtex(result);
        this.citationsDoi.add(result);
        System.out.println("Added citation via doi: " + doi + "\n");
        return true;
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
}