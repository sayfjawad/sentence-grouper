package nl.multicode.search;

import nl.multicode.similarity.FuzzyWuzzyTokenSortDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FuzzyWuzzyTokenSortDistanceSearch finds similar sentences based on token-sorted fuzzy matching.
 */
public class FuzzyWuzzyTokenSortDistanceSearch {
    private final FuzzyWuzzyTokenSortDistance fuzzyTokenSortDistance;

    /**
     * Constructs a FuzzyWuzzyTokenSortDistanceSearch instance.
     */
    public FuzzyWuzzyTokenSortDistanceSearch() {
        this.fuzzyTokenSortDistance = new FuzzyWuzzyTokenSortDistance();
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
                .filter(sentence -> fuzzyTokenSortDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
