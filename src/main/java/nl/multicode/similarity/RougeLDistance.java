package nl.multicode.similarity;

/**
 * RougeLDistance implements the Rouge-L similarity measure.
 * <p>
 * Rouge-L similarity is based on the Longest Common Subsequence (LCS) between two strings.
 * The formula is:
 * <pre>
 *     Rouge-L = (1 + beta^2) * R_LCS * P_LCS / (R_LCS + beta^2 * P_LCS)
 * </pre>
 * where:
 * - R_LCS = LCS(src, tar) / len(src)
 * - P_LCS = LCS(src, tar) / len(tar)
 * - beta is a weighting factor (default = 8)
 * </p>
 */
public class RougeLDistance {

    private final double beta;

    /**
     * Constructs a RougeLDistance instance with the default beta (8).
     */
    public RougeLDistance() {
        this.beta = 8.0;
    }

    /**
     * Constructs a RougeLDistance instance with a custom beta value.
     *
     * @param beta The weighting factor to bias similarity toward the source string.
     */
    public RougeLDistance(double beta) {
        this.beta = beta;
    }

    /**
     * Computes the Rouge-L similarity between two strings.
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

        int lcsLength = longestCommonSubsequence(src, tar);
        double rLcs = (double) lcsLength / src.length();
        double pLcs = (double) lcsLength / tar.length();
        double betaSq = beta * beta;

        if (rLcs > 0 && pLcs > 0) {
            return ((1 + betaSq) * rLcs * pLcs) / (rLcs + betaSq * pLcs);
        }
        return 0.0;
    }

    /**
     * Computes the Longest Common Subsequence (LCS) length between two strings.
     *
     * @param src The first string.
     * @param tar The second string.
     * @return The length of the LCS.
     */
    private int longestCommonSubsequence(String src, String tar) {
        int m = src.length();
        int n = tar.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (src.charAt(i - 1) == tar.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
