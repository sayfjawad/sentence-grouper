package nl.multicode.search;

import nl.multicode.distance.LIG3Distance;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class LIG3DistanceSearchTest {

    @Test
    void testExamplePairs() {
        LIG3Distance lig3 = new LIG3Distance();

        assertThat(lig3.sim("cat", "hat")).isCloseTo(0.8, within(0.01));
        assertThat(lig3.sim("Niall", "Neil")).isCloseTo(0.57, within(0.01));
        assertThat(lig3.sim("aluminum", "Catalan")).isCloseTo(0.0, within(0.01));
        assertThat(lig3.sim("ATCG", "TAGC")).isCloseTo(0.0, within(0.01));
    }

    @Test
    void testAlbertHeijnSimilaritySearch() {
        LIG3DistanceSearch search = new LIG3DistanceSearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = Arrays.asList(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        double threshold = 0.7;
        List<String> similarSentences = search.findSimilarSentences(searchSentence, sentences, threshold);

        assertThat(similarSentences)
                .contains("Albört H ijn")
                .contains("Albert Heijn store")
                .contains("Alber Heijn")
                .hasSize(3);
    }
}
