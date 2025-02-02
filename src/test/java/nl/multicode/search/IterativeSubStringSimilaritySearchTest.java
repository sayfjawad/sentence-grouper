package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IterativeSubStringSimilaritySearchTest {


    @Test
    void testAlbertHeinSimilaritySearch() {
        IterativeSubStringSimilaritySearch search = new IterativeSubStringSimilaritySearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        // Choose a threshold for similarity.
        // (Since I-Sub similarity values are computed via (corr+1)/2,
        //  a perfect match is 1.0 and a very dissimilar pair is near 0.0.)
        double threshold = 0.5;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        // For this test we expect that the sentences that are truly similar to "Albert Heijn"
        // (for example "Albört H ijn" and "Alber Heijn") are returned.
        assertThat(similarSentences)
                .hasSize(3)
                .contains("Albert Heijn store")
                .contains("Albört H ijn")
                .contains("Alber Heijn");
    }

    /**
     * A helper for approximate double comparison.
     */
    private static org.assertj.core.api.Condition<Double> within(double tolerance) {
        return new org.assertj.core.api.Condition<>(d -> Math.abs(d) < tolerance, "within " + tolerance);
    }
}
