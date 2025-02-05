package nl.multicode.similarity;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the Refined Soundex algorithm.
 */
public class RefinedSoundex {

    private static final Map<Character, Character> SOUND_MAPPING = new HashMap<>();

    static {
        String mapping = "01360240043788015936020505";
        for (char c = 'A'; c <= 'Z'; c++) {
            SOUND_MAPPING.put(c, mapping.charAt(c - 'A'));
        }
    }

    /**
     * Encodes a word using Refined Soundex.
     *
     * @param word The input word
     * @return The Refined Soundex code
     */
    public String encode(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }

        word = word.toUpperCase().replaceAll("[^A-Z]", ""); // Normalize input
        StringBuilder result = new StringBuilder();
        result.append(word.charAt(0));

        char lastCode = getSoundexCode(word.charAt(0));
        for (int i = 1; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            char currentCode = getSoundexCode(currentChar);

            if (currentCode != '0' && currentCode != lastCode) {
                result.append(currentCode);
            }
            lastCode = currentCode;
        }

        return result.toString();
    }

    /**
     * Retrieves the Soundex code for a given character.
     *
     * @param c The character
     * @return The corresponding Soundex code
     */
    private char getSoundexCode(char c) {
        return SOUND_MAPPING.getOrDefault(c, '0');
    }
}
