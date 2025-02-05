package nl.multicode.search;

import nl.multicode.similarity.ClementDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClementSimilaritySearch uses Clement similarity to find similar sentences.
 */
public class ClementSimilaritySearch {
    private final ClementDistance clementSimilarity;

    /**
     * Constructs a ClementSimilaritySearch instance with default parameters.
     */
    public ClementSimilaritySearch() {
        this.clementSimilarity = new ClementDistance();
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
                .filter(sentence -> clementSimilarity.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
