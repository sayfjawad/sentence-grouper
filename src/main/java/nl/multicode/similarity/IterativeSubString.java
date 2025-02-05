package nl.multicode.similarity;

/**
 * IterativeSubString implements the Iterative-SubString (I-Sub) correlation.
 * <p>
 * The I-Sub correlation is based on the algorithm described by Stoilos (2005)
 * and is a port of the original Java implementation.
 * </p>
 *
 * <p>
 * If <code>normalizeStrings</code> is enabled then the input strings are converted
 * to lower-case and the characters '.', '_' and space are removed.
 * </p>
 *
 * <p>
 * The correlation is computed as:
 * <br>
 *   corr(src, tar) = commonality - dissimilarity + winklerImprovement
 * <br>
 * where:
 * <ul>
 *   <li>commonality = 2 * common / (|src| + |tar|)</li>
 *   <li>winklerImprovement is a bonus based on the common prefix</li>
 *   <li>dissimilarity is computed via a Hamacher product of the unmatched proportions.</li>
 * </ul>
 * Finally, similarity is computed as: <code>sim(src, tar) = (corr(src, tar) + 1) / 2</code>
 * so that similarity lies in the range [0, 1].
 * </p>
 */
public class IterativeSubString {

    private final double hamacher;
    private final boolean normalizeStrings;

    /**
     * Constructs an IterativeSubString instance with default parameters:
     * hamacher = 0.6 and normalization disabled.
     */
    public IterativeSubString() {
        this(0.6, false);
    }

    /**
     * Constructs an IterativeSubString instance with the given parameters.
     *
     * @param hamacher         the constant factor for the Hamacher product
     * @param normalizeStrings if true, the input strings are normalized (lower-cased and stripped of "._ ")
     */
    public IterativeSubString(double hamacher, boolean normalizeStrings) {
        this.hamacher = hamacher;
        this.normalizeStrings = normalizeStrings;
    }

    /**
     * Returns the I-Sub correlation between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the correlation value (which may be negative)
     */
    public double corr(String src, String tar) {
        // Keep original inputs for winkler improvement.
        String inputSrc = src;
        String inputTar = tar;

        if (normalizeStrings) {
            src = src.toLowerCase();
            tar = tar.toLowerCase();
            for (char ch : "._ ".toCharArray()) {
                src = src.replace(String.valueOf(ch), "");
                tar = tar.replace(String.valueOf(ch), "");
            }
        }

        int srcLen = src.length();
        int tarLen = tar.length();

        if (srcLen == 0 && tarLen == 0) {
            return 1.0;
        }
        if (srcLen == 0 || tarLen == 0) {
            return -1.0;
        }

        int common = 0;
        int best = 2; // initial threshold for a match in the inner loop

        // Work on mutable copies of the strings.
        String currentSrc = src;
        String currentTar = tar;

        // Loop until one string is exhausted or no block of length > 2 can be found.
        while (!currentSrc.isEmpty() && !currentTar.isEmpty() && best != 0) {
            best = 0;
            int ls = currentSrc.length();
            int lt = currentTar.length();
            int startSrc = 0, endSrc = 0, startTar = 0, endTar = 0;

            // For each starting index i in currentSrc...
            for (int i = 0; i < ls && ls - i > best; i++) {
                int j = 0;
                // While there is room in currentTar.
                while (j < lt && lt - j > best) {
                    int k = i;
                    // Advance j until a matching character is found.
                    while (j < lt && currentSrc.charAt(k) != currentTar.charAt(j)) {
                        j++;
                    }
                    if (j < lt) {
                        int p = j; // record the start in tar
                        j++;
                        k++;
                        // Extend the matching block as long as characters match.
                        while (j < lt && k < ls && currentSrc.charAt(k) == currentTar.charAt(j)) {
                            j++;
                            k++;
                        }
                        if (k - i > best) {
                            best = k - i;
                            startSrc = i;
                            endSrc = k;
                            startTar = p;
                            endTar = j;
                        }
                    } else {
                        break;
                    }
                }
            }
            // Remove the matching block from both strings.
            currentSrc = currentSrc.substring(0, startSrc) + currentSrc.substring(endSrc);
            currentTar = currentTar.substring(0, startTar) + currentTar.substring(endTar);
            if (best > 2) {
                common += best;
            } else {
                best = 0;
            }
        }

        double commonality = 2.0 * common / (srcLen + tarLen);
        double winklerImprovement = computeWinklerImprovement(inputSrc, inputTar, commonality);
        double unmatchedSrc = Math.max(srcLen - common, 0) / (double) srcLen;
        double unmatchedTar = Math.max(tarLen - common, 0) / (double) tarLen;
        double unmatchedProd = unmatchedSrc * unmatchedTar;
        double dissimilarity = unmatchedProd /
                (hamacher + (1 - hamacher) * (unmatchedSrc + unmatchedTar - unmatchedProd));

        return commonality - dissimilarity + winklerImprovement;
    }

    /**
     * Returns the I-Sub similarity between two strings.
     * The similarity is defined as (corr(src, tar) + 1) / 2.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0, 1]
     */
    public double sim(String src, String tar) {
        return (corr(src, tar) + 1.0) / 2.0;
    }

    /**
     * Computes the Winkler improvement bonus.
     *
     * @param src         the original source string
     * @param tar         the original target string
     * @param commonality the computed commonality value
     * @return the Winkler improvement value
     */
    private double computeWinklerImprovement(String src, String tar, double commonality) {
        int prefix = 0;
        int minLen = Math.min(src.length(), tar.length());
        for (int i = 0; i < minLen; i++) {
            if (src.charAt(i) != tar.charAt(i)) {
                break;
            }
            prefix++;
        }
        // Only up to 4 characters are considered.
        double bonus = Math.min(4.0, prefix) * 0.1 * (1.0 - commonality);
        return bonus;
    }
}
