package com.projekti;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class CitationBibTeXWriter {

    public void WriteToFile(List<Citation> citations, String filename) {
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            for(Citation c : citations) {
                writer.println(c.toBibTeXEntry());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }
}
