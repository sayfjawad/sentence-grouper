package nl.multicode.search;

import java.util.List;
import java.util.stream.Collectors;

public class IndelSimilaritySearch {

    /**
     * Finds sentences in the list that are very close to the search sentence using the indel distance.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentences      The list of sentences to search through.
     * @param threshold      The maximum allowed indel distance for a match.
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
     * Checks if a sentence is similar to the search sentence based on the indel distance.
     *
     * Only insertions and deletions are allowed, so if characters at a given position do not match,
     * the algorithm will only count an insertion or deletion.
     *
     * @param searchSentence The sentence to compare against.
     * @param sentence       The sentence to check.
     * @param threshold      The maximum allowed indel distance.
     * @return True if the sentence is similar, false otherwise.
     */
    private boolean isSentenceSimilar(final String searchSentence,
                                      final String sentence,
                                      final int threshold) {
        final int[][] distanceMatrix = new int[searchSentence.length() + 1][sentence.length() + 1];
        initializeDistanceMatrix(searchSentence, sentence, distanceMatrix);
        fillDistanceMatrixFunctionally(searchSentence, sentence, distanceMatrix, 1, 1);
        return distanceMatrix[searchSentence.length()][sentence.length()] <= threshold;
    }

    /**
     * Initializes the distance matrix for the indel distance calculation.
     *
     * @param firstString    The first string.
     * @param secondString   The second string.
     * @param distanceMatrix The distance matrix to initialize.
     */
    private void initializeDistanceMatrix(final String firstString,
                                          final String secondString,
                                          final int[][] distanceMatrix) {
        for (int i = 0; i <= firstString.length(); i++) {
            distanceMatrix[i][0] = i;
        }
        for (int j = 0; j <= secondString.length(); j++) {
            distanceMatrix[0][j] = j;
        }
    }

    /**
     * Fills the distance matrix for the indel distance calculation using a recursive approach.
     *
     * Note: Only insertions and deletions are allowed. When characters match, no cost is incurred.
     * When they differ, the substitution (diagonal) move is not allowed.
     *
     * @param firstString    The first string.
     * @param secondString   The second string.
     * @param distanceMatrix The distance matrix to fill.
     * @param currentRow     The current row index.
     * @param currentColumn  The current column index.
     */
    private void fillDistanceMatrixFunctionally(final String firstString,
                                                final String secondString,
                                                final int[][] distanceMatrix,
                                                final int currentRow,
                                                final int currentColumn) {
        if (currentRow > firstString.length()) {
            return; // Base case: all rows filled
        }
        if (currentColumn > secondString.length()) {
            // Move to next row; reset column index to 1 (since index 0 is already initialized)
            fillDistanceMatrixFunctionally(firstString, secondString, distanceMatrix, currentRow + 1, 1);
            return;
        }

        // If the characters match, no cost is incurred.
        if (firstString.charAt(currentRow - 1) == secondString.charAt(currentColumn - 1)) {
            distanceMatrix[currentRow][currentColumn] = distanceMatrix[currentRow - 1][currentColumn - 1];
        } else {
            // Only deletion and insertion are allowed.
            final int deletionCost = distanceMatrix[currentRow - 1][currentColumn] + 1;
            final int insertionCost = distanceMatrix[currentRow][currentColumn - 1] + 1;
            distanceMatrix[currentRow][currentColumn] = Math.min(deletionCost, insertionCost);
        }

        // Recurse for the next column.
        fillDistanceMatrixFunctionally(firstString, secondString, distanceMatrix, currentRow, currentColumn + 1);
    }
}
