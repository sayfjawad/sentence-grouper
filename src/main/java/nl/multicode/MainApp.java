package nl.multicode;

import java.util.Arrays;
import java.util.List;

import nl.multicode.search.LevenshteinSimilaritySearch;

public class MainApp {

    public static void main(String[] args) {

        LevenshteinSimilaritySearch autoGrouper = new LevenshteinSimilaritySearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        int threshold = 3;
        List<String> similarSentences = autoGrouper.findSimilarSentences(searchSentence, sentences, threshold);

        System.out.println("Similar sentences: " + similarSentences);
    }
}
