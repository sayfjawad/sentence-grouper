package nl.multicode.distance;

import java.util.HashMap;
import java.util.Map;

/**
 * SSKDistance implements the String Subsequence Kernel similarity.
 */
public class SSKDistance {

    private final double sskLambda;

    /**
     * Constructs an SSKDistance instance with default lambda = 0.9.
     */
    public SSKDistance() {
        this.sskLambda = 0.9;
    }

    /**
     * Computes the SSK similarity between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the similarity score in the range [0,1]
     */
    public double sim(String src, String tar) {
        if (src.equalsIgnoreCase(tar)) {
            return 1.0;
        }

        Map<String, Integer> srcTokens = tokenize(src);
        Map<String, Integer> tarTokens = tokenize(tar);

        double score = 0.0;
        for (String token : srcTokens.keySet()) {
            if (tarTokens.containsKey(token)) {
                score += srcTokens.get(token) * tarTokens.get(token);
            }
        }

        double srcNorm = Math.sqrt(srcTokens.values().stream().mapToDouble(v -> v * v).sum());
        double tarNorm = Math.sqrt(tarTokens.values().stream().mapToDouble(v -> v * v).sum());

        return (srcNorm == 0 || tarNorm == 0) ? 0.0 : score / (srcNorm * tarNorm);
    }

    /**
     * Tokenizes a string into subsequences.
     *
     * @param str the input string
     * @return a map of subsequence counts
     */
    private Map<String, Integer> tokenize(String str) {
        Map<String, Integer> tokens = new HashMap<>();
        int len = str.length();

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {
                String sub = str.substring(i, j);
                tokens.put(sub, tokens.getOrDefault(sub, 0) + 1);
            }
        }
        return tokens;
    }
}
