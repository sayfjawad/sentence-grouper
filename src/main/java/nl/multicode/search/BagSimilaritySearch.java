package nl.multicode.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BagSimilaritySearch {

    /**
     * Finds sentences in the list that are very close to the search sentence.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentences      The list of sentences to search through.
     * @param threshold      The maximum allowed Bag distance for a match.
     * @return A list of sentences that are close to the search sentence.
     */
    public List<String> findSimilarSentences(final String searchSentence,
                                             final List<String> sentences,
                                             final int threshold) {
        return sentences.stream()
                .filter(sentence -> isSentenceSimilar(searchSentence, sentence, threshold))
                .collect(Collectors.toList());
    }

    /**
     * Checks if a sentence is similar to the search sentence based on the Bag distance.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentence       The sentence to check.
     * @param threshold      The maximum allowed Bag distance.
     * @return True if the sentence is similar, false otherwise.
     */
    private boolean isSentenceSimilar(final String searchSentence,
                                      final String sentence,
                                      final int threshold) {
        int bagDistance = calculateBagDistance(searchSentence, sentence);
        return bagDistance <= threshold;
    }

    /**
     * Calculates the Bag distance between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The Bag distance between the two strings.
     */
    private int calculateBagDistance(final String src, final String tar) {
        if (tar.equals(src)) {
            return 0;
        } else if (src.isEmpty()) {
            return tar.length();
        } else if (tar.isEmpty()) {
            return src.length();
        }

        // Create multisets (bags) for the source and target strings
        Map<Character, Integer> srcBag = createMultiset(src);
        Map<Character, Integer> tarBag = createMultiset(tar);

        // Calculate the multiset differences
        int srcOnlyCard = multisetDifferenceCardinality(srcBag, tarBag);
        int tarOnlyCard = multisetDifferenceCardinality(tarBag, srcBag);

        // Bag distance is the maximum of the two differences
        return Math.max(srcOnlyCard, tarOnlyCard);
    }

    /**
     * Creates a multiset (bag) from a string.
     *
     * @param str The input string.
     * @return A map representing the multiset.
     */
    private Map<Character, Integer> createMultiset(final String str) {
        Map<Character, Integer> multiset = new HashMap<>();
        for (char c : str.toCharArray()) {
            multiset.put(c, multiset.getOrDefault(c, 0) + 1);
        }
        return multiset;
    }

    /**
     * Calculates the cardinality of the multiset difference (A - B).
     *
     * @param multisetA The first multiset.
     * @param multisetB The second multiset.
     * @return The cardinality of the multiset difference.
     */
    private int multisetDifferenceCardinality(final Map<Character, Integer> multisetA,
                                              final Map<Character, Integer> multisetB) {
        int cardinality = 0;
        for (Map.Entry<Character, Integer> entry : multisetA.entrySet()) {
            char key = entry.getKey();
            int countA = entry.getValue();
            int countB = multisetB.getOrDefault(key, 0);
            cardinality += Math.max(0, countA - countB);
        }
        return cardinality;
    }

    public static void main(String[] args) {
        BagSimilaritySearch searcher = new BagSimilaritySearch();

        String searchSentence = "Albert Heijn";
        List<String> sentences = List.of(
                "Albört H ijn",
                "Action",
                "Blokker",
                "H&M",
                "Albert Heijn store",
                "Alber Heijn"
        );

        int threshold = 2; // Maximum allowed Bag distance
        List<String> similarSentences = searcher.findSimilarSentences(searchSentence, sentences, threshold);

        System.out.println("Similar sentences: " + similarSentences);
    }
}