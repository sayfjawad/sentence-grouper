package nl.multicode.search;

import nl.multicode.distance.RougeLDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RougeLDistanceSearch uses Rouge-L similarity to find similar sentences.
 */
public class RougeLDistanceSearch {
    private final RougeLDistance rougeLDistance;

    /**
     * Constructs a RougeLDistanceSearch instance with the default beta (8).
     */
    public RougeLDistanceSearch() {
        this.rougeLDistance = new RougeLDistance();
    }

    /**
     * Constructs a RougeLDistanceSearch instance with a custom beta value.
     *
     * @param beta The weighting factor to bias similarity toward the source string.
     */
    public RougeLDistanceSearch(double beta) {
        this.rougeLDistance = new RougeLDistance(beta);
    }

    /**
     * Finds sentences in the given list that have a similarity greater than or equal to the threshold.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentences      The list of sentences to search through.
     * @param threshold      The minimum similarity score required (in the range [0,1]).
     * @return A list of similar sentences.
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> rougeLDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
