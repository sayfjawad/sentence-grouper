package nl.multicode.distance;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * FuzzyWuzzyTokenSetDistance implements a tokenized set-based similarity search.
 * <p>
 * The similarity is computed by tokenizing the strings, sorting tokens, and comparing:
 * <pre>
 *     similarity = max(SeqMatch(src, intersection), SeqMatch(intersection, tar), SeqMatch(src, tar))
 * </pre>
 * where "intersection" refers to the common tokens between the two sets.
 * </p>
 */
public class FuzzyWuzzyTokenSetDistance {

    /**
     * Computes the FuzzyWuzzy Token Set similarity between two strings.
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

        Set<String> srcTokens = tokenize(src);
        Set<String> tarTokens = tokenize(tar);

        Set<String> intersection = new TreeSet<>(srcTokens);
        intersection.retainAll(tarTokens);

        srcTokens.removeAll(intersection);
        tarTokens.removeAll(intersection);

        String intersectionStr = String.join(" ", intersection) + " ";
        String sortedSrc = intersectionStr + String.join(" ", srcTokens);
        String sortedTar = intersectionStr + String.join(" ", tarTokens);

        return Math.max(
                sequenceSimilarity(sortedSrc, intersectionStr),
                Math.max(sequenceSimilarity(intersectionStr, sortedTar),
                        sequenceSimilarity(sortedSrc, sortedTar))
        );
    }

    /**
     * Tokenizes a string into a set of words.
     *
     * @param str The input string.
     * @return A set of unique, sorted tokens.
     */
    private Set<String> tokenize(String str) {
        return Arrays.stream(str.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(TreeSet::new));
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
