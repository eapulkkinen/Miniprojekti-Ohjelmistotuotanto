package com.projekti;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class for fetching Bibtex-type data from a url.
 *
 * @author Juuso
 * @version 9.12.2024
 */
public class BibtexFetcher {
    /**
     * Main program to test fetching and formatting.
     *
     * @param args args
     */
    public static void main(String[] args) {
        // Examples:
        // https://jyu.finna.fi/PrimoRecord/pci.cdi_crossref_primary_10_33350_ka_117096
        // doi: 10.33350/ka.117096
        // https://jyu.finna.fi/PrimoRecord/pci.cdi_crossref_primary_10_30673_sja_119791
        // 10.30673/sja.119791

        String doi = "10.30673/sja.119791";
        String bibtex = fetchBibtex(doi);
        bibtex = formatBibtex(bibtex);

        try (PrintWriter writer = new PrintWriter("fetched_bibtex.bib", "UTF-8")) {
            writer.println(bibtex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Fetches data.
     *
     * @param doi doi id
     * @return fetched bibtex string
     */
    public static String fetchBibtex(String doi) {
        // Use Crossref's REST API to get data
        // https://github.com/CrossRef/rest-api-doc/
        // https://www.crossref.org/documentation/
        // TODO: use other options?

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
}
