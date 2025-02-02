package nl.multicode.distance;

import java.util.HashMap;
import java.util.Map;

/**
 * TypoDistance implements a typo-aware edit distance based on keyboard proximity.
 * <p>
 * The similarity is computed by assigning lower costs for substitutions
 * between characters that are close on the keyboard.
 * </p>
 */
public class TypoDistance {

    private static final int MATCH_COST = 0;
    private static final int NEARBY_KEY_COST = 1;
    private static final int MISMATCH_COST = 2;
    private static final int INSERT_DELETE_COST = 1;

    private static final Map<Character, String> QWERTY_NEIGHBORS = new HashMap<>();

    static {
        QWERTY_NEIGHBORS.put('q', "wa");
        QWERTY_NEIGHBORS.put('w', "qesa");
        QWERTY_NEIGHBORS.put('e', "wsdr");
        QWERTY_NEIGHBORS.put('r', "edft");
        QWERTY_NEIGHBORS.put('t', "rfgy");
        QWERTY_NEIGHBORS.put('y', "tghu");
        QWERTY_NEIGHBORS.put('u', "yhj");
        QWERTY_NEIGHBORS.put('i', "ujk");
        QWERTY_NEIGHBORS.put('o', "ikl");
        QWERTY_NEIGHBORS.put('p', "ol");
        QWERTY_NEIGHBORS.put('a', "qwsz");
        QWERTY_NEIGHBORS.put('s', "awxd");
        QWERTY_NEIGHBORS.put('d', "esfc");
        QWERTY_NEIGHBORS.put('f', "drtg");
        QWERTY_NEIGHBORS.put('g', "ftyhb");
        QWERTY_NEIGHBORS.put('h', "gyujn");
        QWERTY_NEIGHBORS.put('j', "huikm");
        QWERTY_NEIGHBORS.put('k', "ijolm");
        QWERTY_NEIGHBORS.put('l', "ok");
        QWERTY_NEIGHBORS.put('z', "asx");
        QWERTY_NEIGHBORS.put('x', "zsdc");
        QWERTY_NEIGHBORS.put('c', "xdfv");
        QWERTY_NEIGHBORS.put('v', "cfgb");
        QWERTY_NEIGHBORS.put('b', "vghn");
        QWERTY_NEIGHBORS.put('n', "bhjm");
        QWERTY_NEIGHBORS.put('m', "njk");
    }

    /**
     * Computes the Typo similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }

        src = normalize(src);
        tar = normalize(tar);

        int srcLen = src.length();
        int tarLen = tar.length();

        int[][] dp = new int[srcLen + 1][tarLen + 1];

        for (int i = 0; i <= srcLen; i++) {
            for (int j = 0; j <= tarLen; j++) {
                if (i == 0) {
                    dp[i][j] = j * INSERT_DELETE_COST;
                } else if (j == 0) {
                    dp[i][j] = i * INSERT_DELETE_COST;
                } else {
                    int cost = typoCost(src.charAt(i - 1), tar.charAt(j - 1));
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + INSERT_DELETE_COST, dp[i][j - 1] + INSERT_DELETE_COST),
                            dp[i - 1][j - 1] + cost
                    );
                }
            }
        }

        int maxLen = Math.max(srcLen, tarLen) * INSERT_DELETE_COST;
        return 1.0 - (double) dp[srcLen][tarLen] / maxLen;
    }

    /**
     * Determines the substitution cost based on keyboard proximity.
     */
    private int typoCost(char c1, char c2) {
        if (c1 == c2) {
            return MATCH_COST;
        }
        if (isNearbyKey(c1, c2)) {
            return NEARBY_KEY_COST;
        }
        return MISMATCH_COST;
    }

    /**
     * Checks if two characters are close on the keyboard.
     */
    private boolean isNearbyKey(char c1, char c2) {
        String neighbors = QWERTY_NEIGHBORS.getOrDefault(c1, "");
        return neighbors.indexOf(c2) >= 0;
    }

    /**
     * Normalizes input strings (lowercase, remove punctuation).
     */
    private String normalize(String str) {
        return str.toLowerCase().replaceAll("[^a-z]", "");
    }
}
