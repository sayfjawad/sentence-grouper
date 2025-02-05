package nl.multicode.similarity;

/**
 * LevenshteinDistance calculates the edit distance between two strings.
 */
public class LevenshteinDistance {

    /**
     * Computes the Levenshtein distance between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the number of edits required to transform src into tar
     */
    public int dist(String src, String tar) {
        int srcLen = src.length();
        int tarLen = tar.length();
        int[][] dp = new int[srcLen + 1][tarLen + 1];

        for (int i = 0; i <= srcLen; i++) {
            for (int j = 0; j <= tarLen; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (src.charAt(i - 1) == tar.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + cost
                    );
                }
            }
        }
        return dp[srcLen][tarLen];
    }
}
