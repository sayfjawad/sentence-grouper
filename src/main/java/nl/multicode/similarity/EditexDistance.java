package nl.multicode.similarity;

import java.util.*;

/**
 * EditexDistance implements Editex similarity, a phonetic edit distance.
 * <p>
 * Editex combines phonetic and edit distance calculations.
 * <p>
 * The similarity is computed by assigning lower costs for substitutions
 * between characters in the same phonetic group.
 * </p>
 */
public class EditexDistance {

    private static final int MATCH_COST = 0;
    private static final int SAME_GROUP_COST = 1;
    private static final int MISMATCH_COST = 2;

    private static final Set<Set<Character>> LETTER_GROUPS = new HashSet<>(Arrays.asList(
            Set.of('A', 'E', 'I', 'O', 'U', 'Y'),
            Set.of('B', 'P'),
            Set.of('C', 'K', 'Q'),
            Set.of('D', 'T'),
            Set.of('L', 'R'),
            Set.of('M', 'N'),
            Set.of('G', 'J'),
            Set.of('F', 'V'),
            Set.of('S', 'X', 'Z')
    ));

    private static final Set<Character> ALL_LETTERS = new HashSet<>();

    static {
        for (Set<Character> group : LETTER_GROUPS) {
            ALL_LETTERS.addAll(group);
        }
    }

    /**
     * Computes the Editex similarity between two strings.
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
                    dp[i][j] = j * MISMATCH_COST;
                } else if (j == 0) {
                    dp[i][j] = i * MISMATCH_COST;
                } else {
                    int cost = editexCost(src.charAt(i - 1), tar.charAt(j - 1));
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + MISMATCH_COST, dp[i][j - 1] + MISMATCH_COST),
                            dp[i - 1][j - 1] + cost
                    );
                }
            }
        }

        int maxLen = Math.max(srcLen, tarLen) * MISMATCH_COST;
        return 1.0 - (double) dp[srcLen][tarLen] / maxLen;
    }

    /**
     * Determines the substitution cost based on phonetic groups.
     */
    private int editexCost(char c1, char c2) {
        if (c1 == c2) {
            return MATCH_COST;
        }
        if (belongsToSameGroup(c1, c2)) {
            return SAME_GROUP_COST;
        }
        return MISMATCH_COST;
    }

    /**
     * Checks if two characters belong to the same phonetic group.
     */
    private boolean belongsToSameGroup(char c1, char c2) {
        for (Set<Character> group : LETTER_GROUPS) {
            if (group.contains(c1) && group.contains(c2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Normalizes input strings (uppercase, remove punctuation).
     */
    private String normalize(String str) {
        return str.toUpperCase().replaceAll("[^A-Z]", "");
    }
}
