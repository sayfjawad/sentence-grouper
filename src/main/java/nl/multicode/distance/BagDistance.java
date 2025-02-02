package nl.multicode.distance;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementeert de Bag Distance voor het vergelijken van strings.
 */
public class BagDistance {

    /**
     * Berekent de Bag Distance tussen twee strings.
     *
     * @param src De bronstring.
     * @param tar De doelstring.
     * @return De Bag Distance tussen de twee strings.
     */
    public int compute(String src, String tar) {
        if (tar.equals(src)) {
            return 0;
        } else if (src.isEmpty()) {
            return tar.length();
        } else if (tar.isEmpty()) {
            return src.length();
        }

        // Maak multisets (bags) voor de bron- en doelstrings
        Map<Character, Integer> srcBag = createMultiset(src);
        Map<Character, Integer> tarBag = createMultiset(tar);

        // Bereken het verschil tussen de multisets
        int srcOnlyCard = multisetDifferenceCardinality(srcBag, tarBag);
        int tarOnlyCard = multisetDifferenceCardinality(tarBag, srcBag);

        // De Bag Distance is het maximum van de twee verschillen
        return Math.max(srcOnlyCard, tarOnlyCard);
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
     * Berekent de cardinaliteit van het verschil tussen twee multisets (A - B).
     *
     * @param multisetA De eerste multiset.
     * @param multisetB De tweede multiset.
     * @return De cardinaliteit van het verschil tussen de multisets.
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
