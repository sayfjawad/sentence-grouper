package nl.multicode.search;

import nl.multicode.similarity.BagDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zoekt vergelijkbare zinnen op basis van de Bag Distance.
 */
public class BagSimilaritySearch {

    private final BagDistance bagDistance;

    /**
     * Constructor met een externe BagDistance-instantie (voor testbaarheid).
     */
    public BagSimilaritySearch(BagDistance bagDistance) {
        this.bagDistance = bagDistance;
    }

    /**
     * Vindt zinnen in de lijst die vergelijkbaar zijn met de zoekzin.
     *
     * @param searchSentence De zin waarmee wordt vergeleken.
     * @param sentences      De lijst met zinnen om door te zoeken.
     * @param threshold      De maximale toegestane Bag Distance voor een match.
     * @return Een lijst met vergelijkbare zinnen.
     */
    public List<String> findSimilarSentences(final String searchSentence,
                                             final List<String> sentences,
                                             final int threshold) {
        return sentences.stream()
                .filter(sentence -> bagDistance.compute(searchSentence, sentence) <= threshold)
                .collect(Collectors.toList());
    }
}
