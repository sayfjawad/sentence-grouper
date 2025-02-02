package nl.multicode.search;

import nl.multicode.distance.SSKDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SSKDistanceSearch finds similar sentences using SSK similarity.
 */
public class SSKDistanceSearch {
    private final SSKDistance sskDistance;

    /**
     * Constructs an SSKDistanceSearch instance.
     */
    public SSKDistanceSearch() {
        this.sskDistance = new SSKDistance();
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
                .filter(sentence -> sskDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
