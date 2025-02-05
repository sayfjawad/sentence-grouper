package nl.multicode.similarity;

/**
 * Implementeert de Indel Distance (alleen invoegingen en verwijderingen).
 */
public class IndelDistance {

    /**
     * Berekent de Indel Distance tussen twee strings.
     *
     * @param src De bronstring.
     * @param tar De doelstring.
     * @return De Indel Distance tussen de twee strings.
     */
    public int compute(String src, String tar) {
        final int[][] distanceMatrix = new int[src.length() + 1][tar.length() + 1];

        initializeDistanceMatrix(src, tar, distanceMatrix);
        fillDistanceMatrix(src, tar, distanceMatrix);

        return distanceMatrix[src.length()][tar.length()];
    }

    /**
     * Initialiseer de afstandsmatrix voor de Indel Distance-berekening.
     *
     * @param src             De bronstring.
     * @param tar             De doelstring.
     * @param distanceMatrix  De matrix om te initialiseren.
     */
    private void initializeDistanceMatrix(String src, String tar, int[][] distanceMatrix) {
        for (int i = 0; i <= src.length(); i++) {
            distanceMatrix[i][0] = i;
        }
        for (int j = 0; j <= tar.length(); j++) {
            distanceMatrix[0][j] = j;
        }
    }

    /**
     * Vul de afstandsmatrix op basis van Indel Distance-regels.
     *
     * @param src             De bronstring.
     * @param tar             De doelstring.
     * @param distanceMatrix  De matrix die wordt gevuld.
     */
    private void fillDistanceMatrix(String src, String tar, int[][] distanceMatrix) {
        for (int i = 1; i <= src.length(); i++) {
            for (int j = 1; j <= tar.length(); j++) {
                if (src.charAt(i - 1) == tar.charAt(j - 1)) {
                    distanceMatrix[i][j] = distanceMatrix[i - 1][j - 1]; // Geen kosten bij gelijke tekens
                } else {
                    int deletionCost = distanceMatrix[i - 1][j] + 1;
                    int insertionCost = distanceMatrix[i][j - 1] + 1;
                    distanceMatrix[i][j] = Math.min(deletionCost, insertionCost); // Minimaal van insertie of deletie
                }
            }
        }
    }
}
