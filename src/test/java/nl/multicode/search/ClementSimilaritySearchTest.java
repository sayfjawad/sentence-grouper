package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClementSimilaritySearchTest {


    @Test
    void testAlbertHeinSimilaritySearch() {
        ClementSimilaritySearch search = new ClementSimilaritySearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        // Threshold chosen based on expected Clement similarity scores.
        double threshold = 0.07;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        // Expect that only "Albört H ijn" and "Alber Heijn" are considered similar.
        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Albert Heijn store")
                .hasSize(2);
    }
}
