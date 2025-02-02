package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SSKDistanceSearchTest {

    @Test
    void testAlbertHeijnSimilaritySearch() {
        SSKDistanceSearch search = new SSKDistanceSearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        double threshold = 0.2;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Alber Heijn")
                .contains("Albert Heijn store")
                .hasSize(3);
    }
}
