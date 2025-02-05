package nl.multicode.search;

import nl.multicode.similarity.RatcliffObershelpDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RatcliffObershelpDistanceSearch uses Ratcliff-Obershelp similarity to find similar sentences.
 */
public class RatcliffObershelpDistanceSearch {
    private final RatcliffObershelpDistance ratcliffObershelpDistance;

    /**
     * Constructs a RatcliffObershelpDistanceSearch instance.
     */
    public RatcliffObershelpDistanceSearch() {
        this.ratcliffObershelpDistance = new RatcliffObershelpDistance();
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
                .filter(sentence -> ratcliffObershelpDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
