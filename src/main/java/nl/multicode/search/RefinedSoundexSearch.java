package nl.multicode.search;

import nl.multicode.similarity.RefinedSoundex;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Searches for similar sentences using Refined Soundex.
 */
public class RefinedSoundexSearch {

    private final RefinedSoundex soundex;

    /**
     * Constructs a RefinedSoundexSearch instance.
     */
    public RefinedSoundexSearch() {
        this.soundex = new RefinedSoundex();
    }

    /**
     * Finds sentences in the list with the same Refined Soundex code.
     *
     * @param searchSentence The input sentence
     * @param sentences      The list of sentences to search in
     * @param threshold      The minimum similarity score
     * @return The list of matching sentences
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, double threshold) {
        String searchCode = soundex.encode(searchSentence);
        return sentences.stream()
                .filter(sentence -> getSimilarityScore(searchCode, soundex.encode(sentence)) >= threshold)
                .collect(Collectors.toList());
    }

    /**
     * Computes a similarity score between two Soundex codes.
     *
     * @param code1 First code
     * @param code2 Second code
     * @return Similarity score (0 to 1)
     */
    private double getSimilarityScore(String code1, String code2) {
        int minLength = Math.min(code1.length(), code2.length());
        int common = 0;
        for (int i = 0; i < minLength; i++) {
            if (code1.charAt(i) == code2.charAt(i)) {
                common++;
            }
        }
        return (double) common / Math.max(code1.length(), code2.length());
    }
}
