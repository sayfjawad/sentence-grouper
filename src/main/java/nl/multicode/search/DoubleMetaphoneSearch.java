package nl.multicode.search;

import nl.multicode.similarity.DoubleMetaphoneEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Searches for similar sentences using Double Metaphone.
 */
public class DoubleMetaphoneSearch {

    private final DoubleMetaphoneEncoder encoder;

    /**
     * Constructs a DoubleMetaphoneSearch instance.
     */
    public DoubleMetaphoneSearch() {
        this.encoder = new DoubleMetaphoneEncoder();
    }

    /**
     * Finds sentences in the list with a matching Double Metaphone encoding.
     *
     * @param searchSentence The input sentence
     * @param sentences      The list of sentences to search in
     * @return The list of matching sentences
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences) {
        String searchCode = encoder.encode(searchSentence);
        String searchAltCode = encoder.encodeAlternate(searchSentence);

        return sentences.stream()
                .filter(sentence -> {
                    String sentenceCode = encoder.encode(sentence);
                    String sentenceAltCode = encoder.encodeAlternate(sentence);
                    return sentenceCode.equals(searchCode) || sentenceAltCode.equals(searchAltCode);
                })
                .collect(Collectors.toList());
    }
}
