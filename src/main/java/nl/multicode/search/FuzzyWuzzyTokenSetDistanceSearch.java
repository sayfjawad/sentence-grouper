package nl.multicode.search;

import nl.multicode.distance.FuzzyWuzzyTokenSetDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FuzzyWuzzyTokenSetDistanceSearch finds similar sentences based on tokenized fuzzy matching.
 */
public class FuzzyWuzzyTokenSetDistanceSearch {
    private final FuzzyWuzzyTokenSetDistance fuzzyTokenSetDistance;

    /**
     * Constructs a FuzzyWuzzyTokenSetDistanceSearch instance.
     */
    public FuzzyWuzzyTokenSetDistanceSearch() {
        this.fuzzyTokenSetDistance = new FuzzyWuzzyTokenSetDistance();
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
                .filter(sentence -> fuzzyTokenSetDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
