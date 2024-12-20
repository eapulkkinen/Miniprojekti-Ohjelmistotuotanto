package com.projekti;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for fetching Bibtex-type data from a url.
 *
 * @author Juuso
 * @version 9.12.2024
 */
public class BibtexFetcher {
    
    /**
     * Fetches data.
     *
     * @param doi doi id
     * @return fetched bibtex string
     */
    public static String fetchBibtex(String doi) {
        // Examples:
        // https://jyu.finna.fi/PrimoRecord/pci.cdi_crossref_primary_10_33350_ka_117096
        // doi: 10.33350/ka.117096
        // https://jyu.finna.fi/PrimoRecord/pci.cdi_crossref_primary_10_30673_sja_119791
        // 10.30673/sja.119791

        // Use Crossref's REST API to get data
        // https://github.com/CrossRef/rest-api-doc/
        // https://www.crossref.org/documentation/

        // The part after doi extracts the data from the JSON in a decent bibtex format
        String apiUrl = "https://api.crossref.org/works/" + doi + "/transform/application/x-bibtex";

        try {
            URL url = new URL(apiUrl);

            // Set up a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                // Get the response
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
                
                // Reading the response by line
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine + System.getProperty("line.separator"));
                }
                in.close();
                System.out.println("Succesfully fetched data from:\n" + apiUrl);
                return response.toString();
            }
            //changed from else to if because else gives eclipse users an error
            if (responseCode != 200) {
                System.out.println("Error while fetching. Response code: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Formats fetched bibtex to a valid format.
     *
     * @param text text
     * @return formatted bibtex string
     */
    public static String formatBibtex(String text) {
        String ls = System.getProperty("line.separator");
        if (text == null) {
            return "";
        }
        String text2 = text.trim();

        int indexFirstComma = text2.indexOf(",") + 1;
        int indexLastCurly = text2.lastIndexOf("}");
        // EntryType and key
        String start = text2.substring(0, indexFirstComma).trim();
        // DataTypes
        String content = text2.substring(indexFirstComma, indexLastCurly).trim();

        // Regex that matches every comma that is preceded by a }
        String regex = "(?<=}),";
        // Split the content by commas not inside curly braces, (key-value pairs)
        String[] fields = content.split(regex);

        StringBuilder sb = new StringBuilder(start + ls);
        for (String field : fields) {
            field = field.trim();
            // Don't add unnecessary fields
            boolean matchesDataType = false;
            for (Citation.DataType dataType : Citation.DataType.values()) {
                if (field.startsWith(dataType.name().toLowerCase())) {
                    matchesDataType = true;
                    break;
                }
            }
            if (!matchesDataType) {
                continue;
            }
            StringBuilder sbb = new StringBuilder(field);
            // Add spacing
            int indexEqualSign = sbb.indexOf("=");
            sbb.insert(indexEqualSign + 1, " ");
            sbb.insert(indexEqualSign, " ");
            sb.append(sbb).append("," + ls);
        }
        sb.append("}");

        return sb.toString();
    }
    
    /**
     * Turns a bibtex string into a citation.
     *
     * @param bibtex a bibtex string
     * @param id id for citation
     * @return created citation object
     */
    public static Citation getCitationFromBibtex(String bibtex, int id) {
        String[] lines = bibtex.split(System.getProperty("line.separator"));
        Citation.EntryType entryType = Citation.EntryType.Inproceedings;
        if (lines[0].startsWith("@book")) {
            entryType = Citation.EntryType.Book;
        }
        if (lines[0].startsWith("@article")) {
            entryType = Citation.EntryType.Article;
        }
        String key = lines[0].substring(lines[0].lastIndexOf("{") + 1, lines[0].length() - 1);
        
        Map<Citation.DataType, String> map = new HashMap<Citation.DataType, String>();
        for (int i = 1; i < lines.length - 1; i++) {
            String data = lines[i].substring(0, lines[i].indexOf(" "));
            String value = lines[i].substring(lines[i].indexOf("{") + 1, lines [i].indexOf("}"));
            if (data.equals("author")) {
                map.put(Citation.DataType.Author, value);
            }
            if (data.equals("title")) {
                map.put(Citation.DataType.Title, value);
            }
            if (data.equals("year")) {
                map.put(Citation.DataType.Year, value);
            }
            if (data.equals("publisher")) {
                map.put(Citation.DataType.Publisher, value);
            }
            if (data.equals("journal")) {
                map.put(Citation.DataType.Journal, value);
            }
            if (data.equals("volume")) {
                map.put(Citation.DataType.Volume, value);
            }
            if (data.equals("pages")) {
                map.put(Citation.DataType.Pages, value);
            }
            if (data.equals("booktitle")) {
                map.put(Citation.DataType.BookTitle, value);
            }
        }

        Citation cit = new Citation(id, entryType, key, map);
        return cit;
    }
}
