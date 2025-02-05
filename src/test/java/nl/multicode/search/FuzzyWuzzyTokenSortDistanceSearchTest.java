package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FuzzyWuzzyTokenSortDistanceSearchTest {

    @Test
    void testAlbertHeijnSimilaritySearch() {
        FuzzyWuzzyTokenSortDistanceSearch search = new FuzzyWuzzyTokenSortDistanceSearch();

        String searchSentence = "Albert Heijn store";
        List<String> sentences = Arrays.asList(
                "store Albert Heijn",
                "Action",
                "Blokker",
                "H&M",
                "Albört H ijn",
                "Heijn store Albert"
        );

        // Choose a threshold based on expected similarity scores.
        double threshold = 0.5;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        // Expect that "store Albert Heijn" and "Heijn store Albert" are considered similar.
        assertThat(similarSentences)
                .contains("store Albert Heijn")
                .contains("Heijn store Albert")
                .hasSize(2);
    }

    /**
     * A helper for approximate double comparison.
     */
    private static org.assertj.core.api.Condition<Double> within(double tolerance) {
        return new org.assertj.core.api.Condition<>(d -> Math.abs(d) < tolerance, "within " + tolerance);
    }
}
