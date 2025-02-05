package nl.multicode.search;

import nl.multicode.similarity.IndelDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zoekt vergelijkbare zinnen op basis van de Indel Distance.
 */
public class IndelSimilaritySearch {

    private final IndelDistance indelDistance;

    /**
     * Constructor met een externe IndelDistance-instantie (voor testbaarheid).
     */
    public IndelSimilaritySearch(IndelDistance indelDistance) {
        this.indelDistance = indelDistance;
    }

    /**
     * Vindt zinnen in de lijst die vergelijkbaar zijn met de zoekzin.
     *
     * @param searchSentence De zin waarmee wordt vergeleken.
     * @param sentences      De lijst met zinnen om door te zoeken.
     * @param threshold      De maximale toegestane Indel Distance voor een match.
     * @return Een lijst met vergelijkbare zinnen.
     */
    public List<String> findSimilarSentences(final String searchSentence,
                                             final List<String> sentences,
                                             final int threshold) {
        return sentences.stream()
                .filter(sentence -> indelDistance.compute(searchSentence, sentence) <= threshold)
                .collect(Collectors.toList());
    }
}
