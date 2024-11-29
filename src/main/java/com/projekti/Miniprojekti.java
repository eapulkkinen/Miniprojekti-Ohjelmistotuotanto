package com.projekti;

import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Miniprojekti {
    final private List<Citation> citations = new ArrayList<Citation>();
    
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) {
        Miniprojekti mini = new Miniprojekti();
        
        try (
        Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    int type = mini.getType(scanner);
                    scanner.nextLine();
                    if (!(0 <= type && type <= 2)) {
                        if (type == -1) {
                            System.out.println("Quitting!\n");
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

                    String data = null;
                    while (data == null) {
                        data = mini.getData(scanner);
                    }
                    System.out.println("You gave the data: " + data + "\n");

                    // Add citation, automatically calculate id 
                    // TODO: change, removing citations could cause duplicates
                    Citation cit = new Citation(mini.citations.size(), type, key, data);
                    mini.citations.add(cit);
                    System.out.println("Added citation:\n" + cit + "\n");
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // Infinite loop without
                    System.out.println("Wrong input format: " + e + "\n");
                } catch (NumberFormatException e) {
                    scanner.nextLine();
                    System.out.println("Wrong number format: " + e + "\n");
                } finally {
                    //System.out.println("Finally");
                    //scanner.close();
                }
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
    }

    /**
     * Handles the user input for the citation type
     * @param scanner Scanner object
     * @return integer
     * @throws InputMisMatchException if the input is not an integer
     */
    public int getType(Scanner scanner) {
        System.out.println("-1 TO QUIT!");
        System.out.println("Give a type:");
        System.out.println("0: " + Citation.Type.Inproceedings);
        System.out.println("1: " + Citation.Type.Article);
        System.out.println("2: " + Citation.Type.Book);
        return scanner.nextInt();
    }

    /**
     * Handles the user input for the citation key
     * @param scanner Scanner object
     * @return trimmed string
     */
    public String getKey(Scanner scanner) {
        System.out.println("Give a key for the citation:");
        return scanner.nextLine().trim();
    }

    /**
     * Handles the user input for the citation data
     * @param scanner Scanner object
     * @return trimmed string
     */
    public String getData(Scanner scanner) {
        System.out.println("Give data for the citation");
        return scanner.nextLine().trim();
    }
}