package nl.multicode.search;

import nl.multicode.similarity.BaulieuXIIIDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zoekt vergelijkbare zinnen op basis van de Baulieu XIII Distance.
 */
public class BaulieuXIIISimilaritySearch {

    private final BaulieuXIIIDistance baulieuXIIIDistance;

    /**
     * Constructor met een externe BaulieuXIIIDistance-instantie (voor testbaarheid).
     */
    public BaulieuXIIISimilaritySearch(BaulieuXIIIDistance baulieuXIIIDistance) {
        this.baulieuXIIIDistance = baulieuXIIIDistance;
    }

    /**
     * Vindt zinnen in de lijst die vergelijkbaar zijn met de zoekzin.
     *
     * @param searchSentence De zin waarmee wordt vergeleken.
     * @param sentences      De lijst met zinnen om door te zoeken.
     * @param threshold      De maximale toegestane Baulieu XIII Distance voor een match.
     * @return Een lijst met vergelijkbare zinnen.
     */
    public List<String> findSimilarSentences(final String searchSentence,
                                             final List<String> sentences,
                                             final double threshold) {
        return sentences.stream()
                .filter(sentence -> baulieuXIIIDistance.compute(searchSentence, sentence) <= threshold)
                .collect(Collectors.toList());
    }
}
