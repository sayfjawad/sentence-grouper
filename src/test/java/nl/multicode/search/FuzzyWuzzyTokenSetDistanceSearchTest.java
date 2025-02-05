package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FuzzyWuzzyTokenSetDistanceSearchTest {

    @Test
    void testAlbertHeijnSimilaritySearch() {
        FuzzyWuzzyTokenSetDistanceSearch search = new FuzzyWuzzyTokenSetDistanceSearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Heijn Albert"
        );

        // Choose a threshold based on expected similarity scores.
        double threshold = 0.5;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        // Expect that "Albört H ijn" and "Heijn Albert" are considered similar.
        assertThat(similarSentences)
                .contains("Albert Heijn store")
                .contains("Heijn Albert")
                .hasSize(2);
    }

    /**
     * A helper for approximate double comparison.
     */
    private static org.assertj.core.api.Condition<Double> within(double tolerance) {
        return new org.assertj.core.api.Condition<>(d -> Math.abs(d) < tolerance, "within " + tolerance);
    }
}

