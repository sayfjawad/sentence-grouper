package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DiceAsymmetricIDistanceSearchTest {


    @Test
    void testAlbertHeinSimilaritySearch() {
        DiceAsymmetricIDistanceSearch search = new DiceAsymmetricIDistanceSearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        // Choose a threshold based on expected Dice Asymmetric I similarity scores.
        double threshold = 0.5;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        // Expect that only "Albört H ijn", "Albert Heijn store" and "Alber Heijn" are considered similar.
        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Alber Heijn")
                .contains("Albert Heijn store")
                .hasSize(3);
    }

    /**
     * A helper for approximate double comparison.
     */
    private static org.assertj.core.api.Condition<Double> within(double tolerance) {
        return new org.assertj.core.api.Condition<>(d -> Math.abs(d) < tolerance, "within " + tolerance);
    }
}
