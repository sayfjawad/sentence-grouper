package nl.multicode.search;

import nl.multicode.similarity.LevenshteinDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Zoekt vergelijkbare zinnen op basis van de Levenshtein-afstand.
 */
public class LevenshteinSimilaritySearch {

    private final LevenshteinDistance levenshteinDistance;

    /**
     * Constructor met een externe LevenshteinDistance-instantie (voor testbaarheid).
     */
    public LevenshteinSimilaritySearch(LevenshteinDistance levenshteinDistance) {
        this.levenshteinDistance = levenshteinDistance;
    }

    /**
     * Vindt zinnen in de lijst die vergelijkbaar zijn met de zoekzin.
     *
     * @param searchSentence De zin waarmee wordt vergeleken.
     * @param sentences      De lijst met zinnen om door te zoeken.
     * @param threshold      De maximale toegestane Levenshtein-afstand voor een match.
     * @return Een lijst met vergelijkbare zinnen.
     */
    public List<String> findSimilarSentences(final String searchSentence,
                                             final List<String> sentences,
                                             final int threshold) {
        return sentences.stream()
                .filter(sentence -> levenshteinDistance.dist(searchSentence, sentence) <= threshold)
                .collect(Collectors.toList());
    }
}
