package nl.multicode.distance;

/**
 * RatcliffObershelpDistance implements the Ratcliff-Obershelp similarity measure.
 * <p>
 * The similarity is computed as:
 * <pre>
 *     similarity = (2 * sum of matching substrings) / (length(src) + length(tar))
 * </pre>
 * where the longest common substring (LCS) is found and then applied recursively
 * on the remaining left and right substrings.
 * </p>
 */
public class RatcliffObershelpDistance {

    /**
     * Computes the Ratcliff-Obershelp similarity between two strings.
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

        int matchScore = substringMatches(src, tar);
        return (2.0 * matchScore) / (src.length() + tar.length());
    }

    /**
     * Finds the sum of all matching substring lengths recursively.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The sum of substring match lengths.
     */
    private int substringMatches(String src, String tar) {
        LcsMatch lcsMatch = findLcs(src, tar);
        if (lcsMatch.length == 0) {
            return 0;
        }

        return lcsMatch.length
                + substringMatches(src.substring(0, lcsMatch.srcStart), tar.substring(0, lcsMatch.tarStart))
                + substringMatches(src.substring(lcsMatch.srcStart + lcsMatch.length), tar.substring(lcsMatch.tarStart + lcsMatch.length));
    }

    /**
     * Finds the longest common substring (LCS) between two strings.
     *
     * @param src The source string.
     * @param tar The target string.
     * @return The LCS match data (start positions and length).
     */
    private LcsMatch findLcs(String src, String tar) {
        int[][] dp = new int[src.length() + 1][tar.length() + 1];
        int maxLength = 0;
        int srcStart = 0;
        int tarStart = 0;

        for (int i = 1; i <= src.length(); i++) {
            for (int j = 1; j <= tar.length(); j++) {
                if (src.charAt(i - 1) == tar.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        srcStart = i - maxLength;
                        tarStart = j - maxLength;
                    }
                }
            }
        }
        return new LcsMatch(srcStart, tarStart, maxLength);
    }

    /**
     * Helper class to store LCS match data.
     */
    private static class LcsMatch {
        final int srcStart;
        final int tarStart;
        final int length;

        LcsMatch(int srcStart, int tarStart, int length) {
            this.srcStart = srcStart;
            this.tarStart = tarStart;
            this.length = length;
        }
    }
}
