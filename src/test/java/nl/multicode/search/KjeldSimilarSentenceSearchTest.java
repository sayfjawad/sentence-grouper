package nl.multicode.search;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KjeldSimilarSentenceSearchTest {

    @Test
    void testAlbertHein() {
        KjeldSimilarSentenceSearch autoGrouper = new KjeldSimilarSentenceSearch();

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

        assertThat(similarSentences).contains("Albört H ijn")
                .contains("Alber Heijn");
    }

}