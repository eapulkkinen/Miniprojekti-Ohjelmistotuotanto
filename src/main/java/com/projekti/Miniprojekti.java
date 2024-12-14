package com.projekti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private int currentId = 0; // TODO: most likely will be removed, no need for id

    /**
     * Starts the program and reads user input. Constructs citations based on input.
     * Writes to the 2 files using writer classes.
     *
     * @param args args
     */
    public static void main(String[] args) {
        String bibtexFile;
        if (args.length == 0) { 
            bibtexFile = "entries.bib";
        } else {
            bibtexFile = args[0];
        }

        Miniprojekti mini = new Miniprojekti();
        if (new File(bibtexFile).isFile()) {
            mini.getCitationsFromBibtexFile();
        }
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String lineSep = System.getProperty("line.separator");
                String command = mini.getCommand(scanner);
                if (command.matches("q")) {
                    System.out.println("Quitting!" + lineSep);
                    break;
                } else if (command.matches("add")) {
                    mini.addCitation(scanner);
                } else if (command.matches("add doi")) {
                    mini.addCitationDoi(scanner);
                } else if (command.matches("remove")) {
                    mini.removeCitation(scanner);
                } else if (command.matches("list")) {
                    if (mini.citations.size() != 0) {
                        System.out.println("");
                        mini.printCitationKeys();
                        mini.printCitations();
                    } else {
                        System.out.println("No citations to list!");
                    }
                } else {
                    System.out.println("Invalid command!" + lineSep);
                }
            }
        }
        if (mini.citations.size() == 0) {
            System.out.println("No citations were added!");
            return;
        }
        mini.printCitations();
        mini.writeToFiles();
    }
    
    /**
     * Writes to files.
     */
    private void writeToFiles() {
        CitationPlainTextWriter.writeToFile(this.citations, this.plaintTextFileName);
        CitationBibtexWriter.writeToFile(this.citations, this.bibFileName);
    }

    /**
     * Prints all the citations.
     */
    private void printCitations() {
        System.out.println("Citations:");
        System.out.println("-------------");
        for (Citation c : this.citations) {
            System.out.println(c);
            System.out.println("---");
        }
    }

    /**
     * Prints all the keys of the current citations.
     */
    private void printCitationKeys() {
        System.out.println("Citation keys:");
        for (Citation c : this.citations) {
            System.out.println(c.getKey());
        }
        System.out.println("");
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
        System.out.println("list -> list all citations");
        return scanner.nextLine().trim().toLowerCase();
    }

    /**
     * Adds a citation.
     *
     * @param scanner scanner
     */
    private void addCitation(Scanner scanner) {
        String type = null;
        int typeInt = -1;
        while (type == null || (!(0 <= typeInt && typeInt <= 2))) {
            if (typeInt != -1) {
                System.out.println("Not a valid type! " + type);
            }
            type = this.getInputType(scanner);
            try {
                typeInt = Integer.parseInt(type);
            } catch (NumberFormatException e) {
                String[] lines = e.getMessage().split("\"");
                System.out.println("Wrong input format: " + lines[1]);
                typeInt = -1;
            }
        }
        System.out.println("You gave the number: " + typeInt);

        String key = null;
        while (key == null || key.isEmpty()) {
            if (key != null) {
                System.out.println("Key cannot be empty!");
            }
            key = this.getInputKey(scanner);
            if (isDuplicateCitationKey(key)) {
                System.out.println("Key cannot be a duplicate: " + key);
                key = null;
            }
        }
        System.out.println("You gave the key: " + key + "\n");

        Map<Citation.DataType, String> data = null;
        Citation.EntryType entryType = Citation.EntryType.Inproceedings;
        while (data == null) {
            if (typeInt == 0) {
                data = this.getInProceedingsData(scanner);
            } else if (typeInt == 1) {
                data = this.getArticleData(scanner);
                entryType = Citation.EntryType.Article;
            } else if (typeInt == 2) {
                data = this.getBookData(scanner);
                entryType = Citation.EntryType.Book;
            }
        }
        System.out.println("You gave the data: " + data + "\n");

        Citation cit = new Citation(this.currentId++, entryType, key, data);
        this.citations.add(cit);
        System.out.println("Added citation:\n" + cit);
    }

    /**
     * Adds a citation using doi.
     *
     * @param scanner scanner
     */
    private void addCitationDoi(Scanner scanner) {
        System.out.println("Input doi:");
        String doi = scanner.nextLine().trim();
        String result = BibtexFetcher.fetchBibtex(doi);
        if (result == null) {
            return;
        }
        result = BibtexFetcher.formatBibtex(result);
        Citation cit = BibtexFetcher.getCitationFromBibtex(result, this.currentId++);
        String key = cit.getKey();
        if (isDuplicateCitationKey(key)) {
            System.out.println("Couldn't add citation via doi: " + doi);
            System.out.println("Duplicate key! " + key);
            return;
        }
        this.citations.add(cit);
        System.out.println("Added citation via doi:\n" + cit);
    }
    
    /**
     * Removes citation with given key.
     *
     * @param scanner scanner
     */
    private void removeCitation(Scanner scanner) {
        System.out.println("Input key of citation to be removed:");
        String key = scanner.nextLine().trim();
        for (int i = 0; i < this.citations.size(); i++) {
            if (this.citations.get(i).getKey().equals(key)) {
                this.citations.remove(i);
                System.out.println("Removed citation with key: " + key + "\n");
                return;
            }
        }
        System.out.println("Couldn't find citation with key: " + key + "\n");
    }

    /**
     * Function for modifying a citation.
     */
    private void modifyCitation() {
        // TODO:
        return;
    }

    /**
     * Checks wheter an existing citation already has the same key.
     *
     * @param newKey the new key
     * @return true if duplicate exists, otherwise false
     */
    private boolean isDuplicateCitationKey(String newKey) {
        for (Citation c : this.citations) {
            if (c.getKey().equals(newKey)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Constructs data for the type inproceedings.
     *
     * @param scanner scanner
     * @return Map of datatype -> string
     */
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
     * Constructs data for the type article.
     *
     * @param scanner scanner
     * @return Map of datatype -> string
     */
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

    /**
     * Constructs data for the type book.
     *
     * @param scanner scanner
     * @return Map of datatype -> string
     */
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

    /**
     * Handles the user input for the citation type.
     *
     * @param scanner Scanner object
     * @return trimmed string
     */
    public String getInputType(Scanner scanner) {
        System.out.println("Give a type:");
        System.out.println("0: " + Citation.EntryType.Inproceedings);
        System.out.println("1: " + Citation.EntryType.Article);
        System.out.println("2: " + Citation.EntryType.Book);
        return scanner.nextLine().trim();
    }

    /**
     * Handles the user input for the citation key.
     *
     * @param scanner Scanner object
     * @return trimmed string
     */
    public String getInputKey(Scanner scanner) {
        System.out.println("Give a key for the citation:");
        return scanner.nextLine().trim();
    }

    /**
     * Reads bibtex entries from entries.bib and adds them to citations list.
     */
    public void getCitationsFromBibtexFile() {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader; 
        try {
            reader = new BufferedReader(new FileReader(this.bibFileName));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("}")) {
                        sb.append(line);
                        Citation cit = BibtexFetcher.getCitationFromBibtex(
                            sb.toString(), currentId++);
                        this.citations.add(cit);
                        //System.out.print(sb.toString());
                        sb.setLength(0);
                    } else {
                        sb.append(line + System.getProperty("line.separator"));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }          
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}