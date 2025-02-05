package nl.multicode.similarity;

/**
 * CormodeLZDistance implements Cormode's LZ distance.
 *
 * <p>
 * The algorithm processes the source string in blocks by checking whether an expanding
 * substring (from a current position) is present in the target string or in the portion
 * of the source already seen. When the substring is no longer found, an edit is recorded,
 * the position is advanced, and the span is reset.
 * </p>
 *
 * <p>
 * The absolute distance is given by: <br>
 *     distance = 1 + (# edits)
 * </p>
 *
 * <p>
 * The normalized distance subtracts 1 from the absolute distance and divides by the length
 * of the source string.
 * </p>
 */
public class CormodeLZDistance {

    /**
     * Returns the absolute Cormode LZ distance between two strings.
     *
     * @param src the source string
     * @param tar the target string
     * @return the absolute distance as defined by Cormode's LZ algorithm
     */
    public double distAbs(String src, String tar) {
        int edits = 0;
        int pos = 0;
        int span = 1;
        int srcLen = src.length();

        // Continue while the substring starting at pos with length span is within bounds.
        while (Math.max(pos + 1, pos + span) <= srcLen) {
            String substring = src.substring(pos, pos + span);
            // Check if the substring exists in tar or in the already processed part of src.
            if (tar.contains(substring) || (pos > 0 && src.substring(0, pos).contains(substring))) {
                span++;
                // If extending the span goes out of bounds, break out of the loop.
                if (pos + span > srcLen) {
                    break;
                }
            } else {
                edits++;
                pos += Math.max(1, span - 1);
                span = 1;
            }
        }
        return 1 + edits;
    }

    /**
     * Returns the normalized Cormode LZ distance between two strings.
     *
     * <p>
     * The normalization is defined as:
     * <br> normalized distance = (distAbs(src, tar) - 1) / src.length()
     * </p>
     *
     * @param src the source string
     * @param tar the target string
     * @return the normalized distance
     */
    public double dist(String src, String tar) {
        double num = distAbs(src, tar) - 1;
        if (num == 0) {
            return 0.0;
        }
        return num / src.length();
    }
}
