package nl.multicode.distance;


/**
 * TichyDistance implements Tichy’s edit distance.
 * <p>
 * Tichy’s algorithm locates substrings (blocks) in a source string that can be copied
 * to reconstruct a target string. Only block moves and add operations are allowed.
 * The absolute distance is computed as:
 *
 * <pre>
 *     distance = (# block moves) * moveCost + (# add operations) * addCost
 * </pre>
 *
 * The normalized distance divides the absolute score by (target length × max(cost)).
 */
public class TichyDistance {
    private final int moveCost;
    private final int addCost;

    /**
     * Creates a TichyDistance instance with default costs: moveCost = 1, addCost = 1.
     */
    public TichyDistance() {
        this(1, 1);
    }

    /**
     * Creates a TichyDistance instance with specified costs.
     *
     * @param moveCost the cost for a block move operation
     * @param addCost  the cost for an add operation
     */
    public TichyDistance(int moveCost, int addCost) {
        this.moveCost = moveCost;
        this.addCost = addCost;
    }

    /**
     * Returns the absolute Tichy distance between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the absolute Tichy distance
     */
    public double distAbs(String src, String tar) {
        if (src.equals(tar)) {
            return 0;
        }

        int moves = 0;
        int adds = 0;
        final int tarLen = tar.length();
        int qPos = 0;

        while (qPos < tarLen) {
            int bestLength = findMaxBlock(src, tar, qPos);
            if (bestLength > 0) {
                moves++;
            } else {
                adds++;
            }
            // Advance at least one position
            qPos += Math.max(1, bestLength);
        }
        return moves * moveCost + adds * addCost;
    }

    /**
     * Returns the normalized Tichy distance between two strings.
     * The normalization is done by dividing the absolute distance by (target length * max(cost)).
     *
     * @param src the source string
     * @param tar the target string
     * @return the normalized Tichy distance
     */
    public double dist(String src, String tar) {
        if (src.equals(tar)) {
            return 0.0;
        }
        double score = distAbs(src, tar);
        int maxCost = Math.max(moveCost, addCost);
        return score / (tar.length() * maxCost);
    }

    /**
     * Helper method that finds the maximum block length matching between src and tar,
     * starting from position qPos in tar.
     *
     * @param src  the source string
     * @param tar  the target string
     * @param qPos the current starting index in tar
     * @return the length of the longest matching block found
     */
    private int findMaxBlock(String src, String tar, int qPos) {
        int bestLength = 0;
        final int srcLen = src.length();
        final int tarLen = tar.length();
        // Try every possible starting position in src
        for (int pCur = 0; pCur < srcLen; pCur++) {
            int lengthCur = 0;
            // Extend the matching block as far as possible.
            while (pCur + lengthCur < srcLen &&
                    qPos + lengthCur < tarLen &&
                    src.charAt(pCur + lengthCur) == tar.charAt(qPos + lengthCur)) {
                lengthCur++;
            }
            if (lengthCur > bestLength) {
                bestLength = lengthCur;
            }
        }
        return bestLength;
    }
}
