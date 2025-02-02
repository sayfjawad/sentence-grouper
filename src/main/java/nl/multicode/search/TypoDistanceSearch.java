package nl.multicode.search;

import nl.multicode.distance.TypoDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TypoDistanceSearch finds similar sentences based on typo-aware edit distance.
 */
public class TypoDistanceSearch {
    private final TypoDistance typoDistance;

    /**
     * Constructs a TypoDistanceSearch instance.
     */
    public TypoDistanceSearch() {
        this.typoDistance = new TypoDistance();
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
                .filter(sentence -> typoDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
