package nl.multicode.similarity;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * FuzzyWuzzyTokenSortDistance implements token sort similarity.
 * <p>
 * It tokenizes the input, sorts the tokens alphabetically, and then compares the sorted strings.
 * <p>
 * The similarity is computed as:
 * <pre>
 *     similarity = SequenceMatcher(sorted(src), sorted(tar))
 * </pre>
 * where "sorted(src)" refers to the source string's tokens sorted alphabetically.
 * </p>
 */
public class FuzzyWuzzyTokenSortDistance {

    /**
     * Computes the FuzzyWuzzy Token Sort similarity between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The similarity score in the range [0,1].
     */
    public double sim(String src, String tar) {
        if (src.equals(tar)) {
            return 1.0;
        }
        if (src.isEmpty() || tar.isEmpty()) {
            return 0.0;
        }

        String sortedSrc = tokenizeAndSort(src);
        String sortedTar = tokenizeAndSort(tar);

        return sequenceSimilarity(sortedSrc, sortedTar);
    }

    /**
     * Tokenizes a string, removes punctuation, and sorts words alphabetically.
     *
     * @param str The input string.
     * @return A normalized and sorted string.
     */
    private String tokenizeAndSort(String str) {
        return Arrays.stream(str.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+"))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    /**
     * Computes the similarity between two strings using character sequence matching.
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return The similarity score.
     */
    private double sequenceSimilarity(String s1, String s2) {
        int matchCount = 0;
        int length = Math.max(s1.length(), s2.length());

        for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                matchCount++;
            }
        }
        return (double) matchCount / length;
    }
}
