package com.projekti;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for BibtexFetcher class.
 */
public class BibtexFetcherTest {
    
    String lineSep = System.getProperty("line.separator");
    
    @Test
    public void testFetchBibtexValidDoi() {
        String doi = "10.30673/sja.119791";
        String bibtex = BibtexFetcher.fetchBibtex(doi);
        String expected = " @article{Kuismin_2022, "
            + "title={Palava rakkaus ja öljy pumpulissa: "
            + "Lemmenviestit, huumori ja kirjallistuminen "
            + "suomenkielisessä kirjallisuudessa 1880-luvulta 1900-luvun alkuun}, "
            + "volume={64}, "
            + "ISSN={0558-4639}, "
            + "url={http://dx.doi.org/10.30673/sja.119791}, "
            + "DOI={10.30673/sja.119791}, "
            + "number={64}, "
            + "journal={Sananjalka}, "
            + "publisher={Sananjalka}, "
            + "author={Kuismin, Anna}, "
            + "year={2022}, "
            + "month=dec }"
            + lineSep;
        assertEquals(expected, bibtex);
    }
    
    @Test
    public void testFetchBibtexInvalidDoi() {
        String doi = "1";
        String bibtex = BibtexFetcher.fetchBibtex(doi);
        String expected = null;
        assertEquals(expected, bibtex);
    }
    
    @Test
    public void testFormatBibtexValidDoi() {
        String bibtex = " @article{Kuismin_2022,"
            + "title={Palava rakkaus ja öljy pumpulissa: "
            + "Lemmenviestit, huumori ja kirjallistuminen "
            + "suomenkielisessä kirjallisuudessa 1880-luvulta 1900-luvun alkuun},"
            + "volume={64},"
            //+ "ISSN={0558-4639}, "
            + "url={http://dx.doi.org/10.30673/sja.119791}, "
            //+ "DOI={10.30673/sja.119791}, "
            + "number={64}, "
            + "journal={Sananjalka}, "
            + "publisher={Sananjalka}, "
            + "author={Kuismin, Anna}, "
            + "year={2022}, "
            //+ "month=dec }"
            + lineSep;
        String formatted = bibtex = BibtexFetcher.formatBibtex(bibtex);
        String expected = "@article{Kuismin_2022," + lineSep
            + "title = {Palava rakkaus ja öljy pumpulissa: "
            + "Lemmenviestit, huumori ja kirjallistuminen "
            + "suomenkielisessä kirjallisuudessa 1880-luvulta 1900-luvun alkuun}," + lineSep
            + "volume = {64}," + lineSep
            //+ "ISSN = {0558-4639}," + lineSep
            //+ "url = {http://dx.doi.org/10.30673/sja.119791}," + lineSep
            //+ "DOI = {10.30673/sja.119791}," + lineSep
            //+ "number = {64}," + lineSep
            + "journal = {Sananjalka}," + lineSep
            + "publisher = {Sananjalka}," + lineSep
            + "author = {Kuismin, Anna}," + lineSep
            + "year = {2022," + lineSep
            //+ "month = dec," + lineSep
            + "}";
        assertEquals(expected, formatted);
    }

    @Test
    public void testFormatBibtexInvalidDoi() {
        String bibtex = null;
        String formatted = bibtex = BibtexFetcher.formatBibtex(bibtex);
        String expected = "";
        assertEquals(expected, formatted);
    }
}
