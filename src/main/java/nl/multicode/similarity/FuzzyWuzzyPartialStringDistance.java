package nl.multicode.similarity;

import java.util.stream.IntStream;

/**
 * FuzzyWuzzyPartialStringDistance implements a substring similarity search.
 * <p>
 * It slides the smaller string over the larger one and finds the best possible match.
 * <p>
 * The similarity is computed as:
 * <pre>
 *     similarity = max( (matching characters) / max(len(src), len(substring)) )
 * </pre>
 * where "substring" refers to all possible substrings of the larger string that match the length of the smaller one.
 * </p>
 */
public class FuzzyWuzzyPartialStringDistance {

    /**
     * Computes the FuzzyWuzzy Partial String similarity between two strings.
     *
     * @param src The source string (typically the shorter one).
     * @param tar The target string (typically the longer one).
     * @return The similarity score in the range [0,1].
     */
    public double sim(String src, String tar) {
        if (src.equals(tar)) {
            return 1.0;
        }
        if (src.isEmpty() || tar.isEmpty()) {
            return 0.0;
        }

        if (src.length() > tar.length()) {
            String temp = src;
            src = tar;
            tar = temp;
        }

        final int maxLen = src.length();
        final var srcString = src;
        final var tarString = tar;


        return IntStream.rangeClosed(0, tar.length() - maxLen)
                .mapToDouble(start -> computeSimilarity(srcString, tarString.substring(start, start + maxLen)))
                .max()
                .orElse(0.0);
    }

    /**
     * Computes the similarity between two strings using character matches.
     *
     * @param s1 The first string.
     * @param s2 The second string (should be of the same length as s1).
     * @return The similarity score.
     */
    private double computeSimilarity(String s1, String s2) {
        int matches = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                matches++;
            }
        }
        return (double) matches / s1.length();
    }
}
