package nl.multicode;


import java.util.*;
import java.util.stream.Collectors;

public class AutoGrouper {

    public Map<String, Map<Integer, String>> autoGroupSentences(List<String> sentences, int minimalDenominatorLength, int minimumGroupSize) {
        List<Map<String, Map<Integer, String>>> groupedSentences = new ArrayList<>();
        List<String> remainingSentences = new ArrayList<>(sentences);

        while (!remainingSentences.isEmpty()) {
            Set<String> possibleDenominators = getPossibleDenominators(remainingSentences, minimalDenominatorLength);
            Set<String> validDenominators = filterValidDenominators(possibleDenominators, remainingSentences, minimumGroupSize);
            Set<String> longestDenominators = getLongestDenominators(validDenominators);
            Map<String, Map<Integer, String>> currentGroup = groupByDenominator(longestDenominators, remainingSentences);

            if (currentGroup.isEmpty()) {
                break;  // This will prevent the infinite loop.
            }

            groupedSentences.add(currentGroup);
            remainingSentences = getUngroupedSentences(remainingSentences, groupedSentences);
        }

        return groupedSentences.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Set<String> getPossibleDenominators(List<String> sentences, int minimalDenominatorLength) {
        Set<String> keys = new HashSet<>();
        final int MIN_KEY_LENGTH = 4;

        for (String sentence : sentences) {
            int effectiveDenominatorLength = Math.min(minimalDenominatorLength, sentence.length());
            for (int addedLength = 0; addedLength + effectiveDenominatorLength <= sentence.length(); addedLength++) {
                for (int i = 0; i <= sentence.length() - (effectiveDenominatorLength + addedLength); i++) {
                    keys.add(sentence.substring(i, i + effectiveDenominatorLength + addedLength).trim());
                }
            }
        }

        keys.removeIf(key -> key.length() < MIN_KEY_LENGTH);
        return keys;
    }

    private Set<String> filterValidDenominators(Set<String> denominators, List<String> sentences, int minimumGroupSize) {
        return denominators.stream()
                .filter(denominator -> sentences.stream().filter(sentence -> sentence.contains(denominator)).count() >= minimumGroupSize)
                .collect(Collectors.toSet());
    }

    private Set<String> getLongestDenominators(Set<String> denominators) {
        int maxLength = denominators.stream().mapToInt(String::length).max().orElse(0);
        return denominators.stream()
                .filter(denominator -> denominator.length() == maxLength)
                .collect(Collectors.toSet());
    }

    private Map<String, Map<Integer, String>> groupByDenominator(Set<String> denominators, List<String> sentences) {
        Map<String, Map<Integer, String>> group = new HashMap<>();

        for (String key : denominators) {
            Map<Integer, String> indexedSentences = new HashMap<>();
            for (int i = 0; i < sentences.size(); i++) {
                if (sentences.get(i).contains(key)) {
                    indexedSentences.put(i, sentences.get(i));
                }
            }
            if (!indexedSentences.isEmpty()) {
                group.put(key, indexedSentences);
            }
        }

        return group;
    }

    private List<String> getUngroupedSentences(List<String> sentences, List<Map<String, Map<Integer, String>>> groupedSentences) {
        Set<Integer> groupedIndices = groupedSentences.stream()
                .flatMap(map -> map.values().stream())
                .flatMap(indexedSentence -> indexedSentence.keySet().stream())
                .collect(Collectors.toSet());

        List<String> remainingSentences = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            if (!groupedIndices.contains(i)) {
                remainingSentences.add(sentences.get(i));
            }
        }

        return remainingSentences;
    }
}
