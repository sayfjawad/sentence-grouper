package nl.multicode.search;

import nl.multicode.distance.TichyDistance;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TichySimilaritySearch uses Tichy’s edit distance to find similar sentences.
 */
public class TichySimilaritySearch {
    private final TichyDistance tichyDistance;

    /**
     * Constructs a TichySimilaritySearch instance with default cost parameters.
     */
    public TichySimilaritySearch() {
        this.tichyDistance = new TichyDistance();
    }

    /**
     * Constructs a TichySimilaritySearch instance with custom cost parameters.
     *
     * @param moveCost the cost for a block move operation
     * @param addCost  the cost for an add operation
     */
    public TichySimilaritySearch(int moveCost, int addCost) {
        this.tichyDistance = new TichyDistance(moveCost, addCost);
    }

    /**
     * Finds sentences in the list that are similar to the search sentence
     * based on the Tichy distance.
     *
     * @param searchSentence the sentence to compare against
     * @param sentences      the list of sentences to search through
     * @param threshold      the maximum allowed absolute Tichy distance for a match
     * @return a list of sentences that are similar to the search sentence
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        return sentences.stream()
                .filter(sentence -> isSentenceSimilar(searchSentence, sentence, threshold))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether a sentence is similar to the search sentence by comparing their Tichy distance.
     *
     * @param searchSentence the sentence to compare against
     * @param sentence       the sentence to check
     * @param threshold      the maximum allowed Tichy distance
     * @return true if the distance is within the threshold; false otherwise
     */
    private boolean isSentenceSimilar(String searchSentence, String sentence, double threshold) {
        double distance = tichyDistance.distAbs(searchSentence, sentence);
        return distance <= threshold;
    }
}
