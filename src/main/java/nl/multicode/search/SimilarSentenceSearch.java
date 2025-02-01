package nl.multicode.search;

import java.util.List;
import java.util.stream.Collectors;

public class SimilarSentenceSearch {

    /**
     * Finds sentences in the list that are very close to the search sentence.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentences      The list of sentences to search through.
     * @param threshold      The maximum allowed Levenshtein distance for a match.
     * @return A list of sentences that are close to the search sentence.
     */
    public List<String> findSimilarSentences(final String searchSentence,
                                             final List<String> sentences,
                                             final int threshold) {
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
    private boolean isSentenceSimilar(final String searchSentence,
                                      final String sentence,
                                      final int threshold) {
        final int[][] similarityScores = new int[searchSentence.length() + 1][sentence.length() + 1];
        final int[][] initializedDistanceMatrix = initializeDistanceMatrix(searchSentence, sentence, similarityScores);
        final int[][] filledDistanceMatrix = fillDistanceMatrixFunctionally(searchSentence, sentence, initializedDistanceMatrix, 1, 1);
        return filledDistanceMatrix[searchSentence.length()][sentence.length()] <= threshold;
    }

    /**
     * Initializes the distance matrix for the Levenshtein Distance calculation.
     *
     * @param firstString    The first string.
     * @param secondString   The second string.
     * @param distanceMatrix The distance matrix to initialize.
     * @return The initialized distance matrix.
     */
    private int[][] initializeDistanceMatrix(final String firstString,
                                             final String secondString,
                                             final int[][] distanceMatrix) {
        for (int i = 0; i <= firstString.length(); i++) {
            distanceMatrix[i][0] = i;
        }
        for (int j = 0; j <= secondString.length(); j++) {
            distanceMatrix[0][j] = j;
        }
        return distanceMatrix;
    }

    /**
     * Fills the distance matrix for the Levenshtein Distance calculation using a recursive approach.
     *
     * @param firstString    The first string.
     * @param secondString   The second string.
     * @param distanceMatrix The distance matrix to fill.
     * @param currentRow     The current row index.
     * @param currentColumn  The current column index.
     * @return The filled distance matrix.
     */
    private int[][] fillDistanceMatrixFunctionally(final String firstString,
                                                   final String secondString,
                                                   final int[][] distanceMatrix,
                                                   final int currentRow,
                                                   final int currentColumn) {
        if (currentRow > firstString.length()) {
            return distanceMatrix; // Base case: stop recursion
        }

        if (currentColumn > secondString.length()) {
            return fillDistanceMatrixFunctionally(firstString, secondString, distanceMatrix, currentRow + 1, 1); // Move to next row
        }

        int substitutionCost = firstString.charAt(currentRow - 1) == secondString.charAt(currentColumn - 1) ? 0 : 1;
        distanceMatrix[currentRow][currentColumn] = min(
                distanceMatrix[currentRow - 1][currentColumn - 1] + substitutionCost, // substitution
                distanceMatrix[currentRow - 1][currentColumn] + 1, // deletion
                distanceMatrix[currentRow][currentColumn - 1] + 1  // insertion
        );

        return fillDistanceMatrixFunctionally(firstString, secondString, distanceMatrix, currentRow, currentColumn + 1); // Move to next column
    }

    /**
     * Helper method to find the minimum of three integers.
     *
     * @param a The first integer.
     * @param b The second integer.
     * @param c The third integer.
     * @return The minimum of the three integers.
     */
    private int min(final int a, final int b, final int c) {
        return Math.min(a, Math.min(b, c));
    }
}