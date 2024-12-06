package com.projekti;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Class for writing a text file based on given citations.
 */
public class CitationPlainTextWriter {

  /**
  * Writes citations given by the user to a text file.

  * @param citations a list of citations
  * @param filename name for the new file
  */
  public static void writeToFile(final List<Citation> citations, final String filename) {
    try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
      for (Citation c : citations) {
        writer.println(c.toString());
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
