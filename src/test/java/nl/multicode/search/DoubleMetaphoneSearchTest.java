package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DoubleMetaphoneSearchTest {

    @Test
    void testAlbertHeijnSimilaritySearch() {
        DoubleMetaphoneSearch search = new DoubleMetaphoneSearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences);

        // Expect that "Albört H ijn", "Albert Heijn store", and "Alber Heijn" are considered similar.
        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Alber Heijn")
                .contains("Albert Heijn store")
                .hasSize(3);
    }
}
