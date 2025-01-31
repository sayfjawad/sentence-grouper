package nl.multicode.search;

import java.util.*;
import java.util.stream.Collectors;

public class KjeldSimilarSentenceSearch {

    private static final int MIN_DENOMINATOR_LENGTH = 4;

    /**
     * Finds sentences in the list that are very close to the search sentence.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentences      The list of sentences to search through.
     * @param threshold      The maximum allowed Levenshtein distance for a match.
     * @return A list of sentences that are close to the search sentence.
     */
    public List<String> findSimilarSentences(String searchSentence, List<String> sentences, int threshold) {
        return sentences.stream()
                .filter(sentence -> isSentenceSimilar(searchSentence, sentence, threshold))
                .collect(Collectors.toList());
    }

    /**
     * Checks if a sentence is similar to the search sentence based on the Levenshtein distance.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentence       The sentence to check.
     * @param threshold      The maximum allowed Levenshtein distance.
     * @return True if the sentence is similar, false otherwise.
     */
    private boolean isSentenceSimilar(String searchSentence, String sentence, int threshold) {
        return calculateLevenshteinDistance(searchSentence, sentence) <= threshold;
    }

    /**
     * Calculates the Levenshtein Distance between two strings.
     *
     * @param firstString  The first string.
     * @param secondString The second string.
     * @return The Levenshtein Distance between the two strings.
     */
    private int calculateLevenshteinDistance(String firstString, String secondString) {
        int[][] distanceMatrix = new int[firstString.length() + 1][secondString.length() + 1];

        initializeDistanceMatrix(firstString, secondString, distanceMatrix);
        fillDistanceMatrix(firstString, secondString, distanceMatrix);

        return distanceMatrix[firstString.length()][secondString.length()];
    }

    /**
     * Initializes the distance matrix for the Levenshtein Distance calculation.
     *
     * @param firstString    The first string.
     * @param secondString   The second string.
     * @param distanceMatrix The distance matrix to initialize.
     */
    private void initializeDistanceMatrix(String firstString, String secondString, int[][] distanceMatrix) {
        for (int i = 0; i <= firstString.length(); i++) {
            distanceMatrix[i][0] = i;
        }
        for (int j = 0; j <= secondString.length(); j++) {
            distanceMatrix[0][j] = j;
        }
    }

    /**
     * Fills the distance matrix for the Levenshtein Distance calculation.
     *
     * @param firstString    The first string.
     * @param secondString   The second string.
     * @param distanceMatrix The distance matrix to fill.
     */
    private void fillDistanceMatrix(String firstString, String secondString, int[][] distanceMatrix) {
        for (int i = 1; i <= firstString.length(); i++) {
            for (int j = 1; j <= secondString.length(); j++) {
                int substitutionCost = firstString.charAt(i - 1) == secondString.charAt(j - 1) ? 0 : 1;
                distanceMatrix[i][j] = min(
                        distanceMatrix[i - 1][j - 1] + substitutionCost, // substitution
                        distanceMatrix[i - 1][j] + 1, // deletion
                        distanceMatrix[i][j - 1] + 1  // insertion
                );
            }
        }
    }

    /**
     * Helper method to find the minimum of three integers.
     *
     * @param a The first integer.
     * @param b The second integer.
     * @param c The third integer.
     * @return The minimum of the three integers.
     */
    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
}