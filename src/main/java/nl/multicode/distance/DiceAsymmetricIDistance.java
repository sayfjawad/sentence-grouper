package nl.multicode.distance;

import java.util.HashSet;
import java.util.Set;

/**
 * DiceAsymmetricIDistance implements Dice's Asymmetric I distance.
 * <p>
 * This similarity measure is defined as:
 * <pre>
 *     sim_DiceAsymmetricI(X, Y) = |X ∩ Y| / |X|
 * </pre>
 * where X and Y are the tokenized sets of characters.
 * </p>
 */
public class DiceAsymmetricIDistance {

    /**
     * Returns the Dice Asymmetric I similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        if (src.equals(tar)) {
            return 1.0;
        }

        Set<Character> srcSet = tokenize(src);
        Set<Character> tarSet = tokenize(tar);

        int intersection = intersectionSize(srcSet, tarSet);
        int srcSize = srcSet.size();

        if (intersection == 0) {
            return 0.0;
        }

        return (double) intersection / srcSize;
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
