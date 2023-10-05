package nl.multicode.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.tartarus.snowball.ext.EnglishStemmer;

public class SentenceGrouper {

    private static final double SIMILARITY_THRESHOLD = 0.03;

    public Map<Integer, List<String>> groupSentences(List<String> sentences) {

        Map<String, RealVector> tfidfVectors = calculateTFIDFVectors(sentences);
        Map<Integer, List<String>> groups = new HashMap<>();

        int groupId = 0;
        for (String sentence : sentences) {
            boolean added = false;
            for (Map.Entry<Integer, List<String>> entry : groups.entrySet()) {
                if (cosineSimilarity(tfidfVectors.get(sentence),
                        tfidfVectors.get(entry.getValue().get(0))) > SIMILARITY_THRESHOLD) {
                    entry.getValue().add(sentence);
                    added = true;
                    break;
                }
            }
            if (!added) {
                groups.put(groupId++, new ArrayList<>(Collections.singletonList(sentence)));
            }
        }

        return groups;
    }

    private static Map<String, RealVector> calculateTFIDFVectors(List<String> sentences) {

        Map<String, Map<String, Integer>> tf = new HashMap<>(); // term frequencies
        Map<String, Integer> df = new HashMap<>(); // document frequencies
        EnglishStemmer stemmer = new EnglishStemmer();

        // Calculate term frequencies and document frequencies
        for (String sentence : sentences) {
            Map<String, Integer> freqMap = new HashMap<>();
            tf.put(sentence, freqMap);
            for (String word : sentence.toLowerCase().split("\\W+")) {
                stemmer.setCurrent(word);
                stemmer.stem();
                String stemmedWord = stemmer.getCurrent();
                freqMap.put(stemmedWord, freqMap.getOrDefault(stemmedWord, 0) + 1);
                df.put(stemmedWord, df.getOrDefault(stemmedWord, 0) + 1);
            }
        }

        // Calculate TF-IDF vectors
        Map<String, RealVector> tfidfVectors = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : tf.entrySet()) {
            RealVector vector = new ArrayRealVector(df.size());
            int idx = 0;
            for (Map.Entry<String, Integer> dfEntry : df.entrySet()) {
                if (entry.getValue().containsKey(dfEntry.getKey())) {
                    vector.setEntry(idx,
                            (1 + Math.log(entry.getValue().get(dfEntry.getKey()))) * Math.log(
                                    sentences.size() * 1.0 / dfEntry.getValue()));
                }
                idx++;
            }
            tfidfVectors.put(entry.getKey(), vector);
        }

        return tfidfVectors;
    }

    private static double cosineSimilarity(RealVector vectorA, RealVector vectorB) {

        return vectorA.dotProduct(vectorB) / (vectorA.getNorm() * vectorB.getNorm());
    }
}

