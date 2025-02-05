package nl.multicode.search;

import nl.multicode.similarity.EditexDistance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EditexDistanceSearch finds similar sentences based on phonetic edit distance.
 */
public class EditexDistanceSearch {
    private final EditexDistance editexDistance;

    /**
     * Constructs an EditexDistanceSearch instance.
     */
    public EditexDistanceSearch() {
        this.editexDistance = new EditexDistance();
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
                .filter(sentence -> editexDistance.sim(searchSentence, sentence) >= threshold)
                .collect(Collectors.toList());
    }
}
