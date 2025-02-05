package nl.multicode.similarity;

import java.util.HashSet;
import java.util.Set;

/**
 * KuhnsIIIDistance implements Kuhns III correlation.
 * <p>
 * The formula follows:
 * <pre>
 *     corr_KuhnsIII(X, Y) =
 *     δ(X, Y) / ((1 - |X ∩ Y| / (|X| + |Y|)) * (|X| + |Y| - |X|*|Y| / |N|))
 * </pre>
 */
public class KuhnsIIIDistance {

    /**
     * Returns the Kuhns III correlation between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the correlation score
     */
    public double corr(String src, String tar) {
        Set<Character> srcSet = tokenize(src);
        Set<Character> tarSet = tokenize(tar);
        Set<Character> population = new HashSet<>(srcSet);
        population.addAll(tarSet); // N is the unique character set from both strings.

        int a = intersectionSize(srcSet, tarSet);
        int b = srcSet.size() - a;
        int c = tarSet.size() - a;
        int n = population.size();

        double delta_ab = a - ((double) (a + b) * (a + c) / n);
        if (delta_ab == 0) {
            return 0.0;
        }

        double denominator = (1 - (double) a / (2 * a + b + c)) * (2 * a + b + c - ((double) (a + b) * (a + c) / n));
        return delta_ab / denominator;
    }

    /**
     * Returns the Kuhns III similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        return (1.0 / 3.0 + corr(src, tar)) / (4.0 / 3.0);
    }

    /**
     * Tokenizes a string into a set of unique characters.
     *
     * @param str the input string
     * @return a set of unique characters
     */
    private Set<Character> tokenize(String str) {
        Set<Character> tokens = new HashSet<>();
        for (char c : str.toCharArray()) {
            tokens.add(c);
        }
        return tokens;
    }

    /**
     * Computes the intersection size of two sets.
     *
     * @param set1 the first set
     * @param set2 the second set
     * @return the number of elements in the intersection
     */
    private int intersectionSize(Set<Character> set1, Set<Character> set2) {
        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection.size();
    }
}
