package nl.multicode.search;

import nl.multicode.distance.LIG3Distance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * LIG3DistanceSearch finds similar sentences based on LIG3 similarity.
 */
public class LIG3DistanceSearch {
    private final LIG3Distance lig3Distance;

    /**
     * Constructs a LIG3DistanceSearch instance.
     */
    public LIG3DistanceSearch() {
        this.lig3Distance = new LIG3Distance();
    }

    /**
     * Finds sentences in the given list that have a similarity greater than or equal to the threshold.
     *
     * @param searchSentence the sentence to compare against
     * @param sentences      the list of sentences to search through
     * @param threshold      the minimum similarity score required (in the range [0,1])
     * @return a list of similar sentences
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> lig3Distance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
