package nl.multicode.distance;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementeert de Baulieu XIII Distance voor het vergelijken van strings.
 */
public class BaulieuXIIIDistance {

    /**
     * Berekent de Baulieu XIII Distance tussen twee strings.
     *
     * @param src De bronstring.
     * @param tar De doelstring.
     * @return De Baulieu XIII Distance tussen de twee strings.
     */
    public double compute(String src, String tar) {
        if (src.equals(tar)) {
            return 0.0;
        }

        // Maak multisets (bags) voor de bron- en doelstrings
        Map<Character, Integer> srcBag = createMultiset(src);
        Map<Character, Integer> tarBag = createMultiset(tar);

        // Bereken snijpunt en verschillen
        int intersectionCardinality = multisetIntersectionCardinality(srcBag, tarBag);
        int srcOnlyCardinality = multisetDifferenceCardinality(srcBag, tarBag);
        int tarOnlyCardinality = multisetDifferenceCardinality(tarBag, srcBag);

        // Bereken de Baulieu XIII afstand
        double numerator = srcOnlyCardinality + tarOnlyCardinality;
        double denominator = intersectionCardinality + srcOnlyCardinality + tarOnlyCardinality +
                intersectionCardinality * Math.pow(intersectionCardinality - 4, 2);

        if (denominator == 0.0) {
            return 0.0; // Vermijd deling door nul
        }

        return numerator / denominator;
    }

    /**
     * Maakt een multiset (bag) van een string.
     *
     * @param str De invoerstring.
     * @return Een map die de multiset vertegenwoordigt.
     */
    private Map<Character, Integer> createMultiset(String str) {
        Map<Character, Integer> multiset = new HashMap<>();
        for (char c : str.toCharArray()) {
            multiset.put(c, multiset.getOrDefault(c, 0) + 1);
        }
        return multiset;
    }

    /**
     * Berekent de cardinaliteit van de multiset doorsnede.
     *
     * @param multisetA De eerste multiset.
     * @param multisetB De tweede multiset.
     * @return De cardinaliteit van de multiset doorsnede.
     */
    private int multisetIntersectionCardinality(Map<Character, Integer> multisetA,
                                                Map<Character, Integer> multisetB) {
        int cardinality = 0;
        for (Map.Entry<Character, Integer> entry : multisetA.entrySet()) {
            char key = entry.getKey();
            int countA = entry.getValue();
            int countB = multisetB.getOrDefault(key, 0);
            cardinality += Math.min(countA, countB);
        }
        return cardinality;
    }

    /**
     * Berekent de cardinaliteit van het multiset-verschil (A - B).
     *
     * @param multisetA De eerste multiset.
     * @param multisetB De tweede multiset.
     * @return De cardinaliteit van het multiset-verschil.
     */
    private int multisetDifferenceCardinality(Map<Character, Integer> multisetA,
                                              Map<Character, Integer> multisetB) {
        int cardinality = 0;
        for (Map.Entry<Character, Integer> entry : multisetA.entrySet()) {
            char key = entry.getKey();
            int countA = entry.getValue();
            int countB = multisetB.getOrDefault(key, 0);
            cardinality += Math.max(0, countA - countB);
        }
        return cardinality;
    }
}
