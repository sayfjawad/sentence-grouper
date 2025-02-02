package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OverlapDistanceSearchTest {

    @Test
    void testAlbertHeinSimilaritySearch() {
        OverlapDistanceSearch search = new OverlapDistanceSearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        // Kies een drempelwaarde gebaseerd op verwachte Overlap-similariteitsscores.
        double threshold = 0.7;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        // Verwacht dat alleen "Albört H ijn" en "Alber Heijn" als vergelijkbaar worden beschouwd.
        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Albert Heijn store")
                .contains("Alber Heijn")
                .hasSize(3);
    }

    /**
     * Een helper voor het vergelijken van double-waarden met een tolerantiedrempel.
     */
    private static org.assertj.core.api.Condition<Double> within(double tolerance) {
        return new org.assertj.core.api.Condition<>(d -> Math.abs(d) < tolerance, "within " + tolerance);
    }
}
