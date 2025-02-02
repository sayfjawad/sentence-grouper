package nl.multicode.search;

import nl.multicode.distance.OverlapDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OverlapDistanceSearch gebruikt de Overlap-coëfficiënt om vergelijkbare zinnen te vinden.
 */
public class OverlapDistanceSearch {
    private final OverlapDistance overlapDistance;

    /**
     * Construeert een OverlapDistanceSearch-instantie met standaardparameters.
     */
    public OverlapDistanceSearch() {
        this.overlapDistance = new OverlapDistance();
    }

    /**
     * Vindt zinnen in de lijst die een gelijkenisscore hebben die groter is dan of gelijk is aan de drempel.
     *
     * @param searchSentence de zin om te vergelijken
     * @param sentences      de lijst met zinnen om door te zoeken
     * @param threshold      de minimale gelijkenisscore vereist (in het bereik [0,1])
     * @return een lijst met vergelijkbare zinnen
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> overlapDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
