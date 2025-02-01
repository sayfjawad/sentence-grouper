package nl.multicode;

import java.util.Arrays;
import java.util.List;

import nl.multicode.search.SimilarSentenceSearch;

public class MainApp {

    public static void main(String[] args) {

        SimilarSentenceSearch autoGrouper = new SimilarSentenceSearch();

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
