package nl.multicode.distance;

import org.apache.commons.codec.language.DoubleMetaphone;

/**
 * Implements the Double Metaphone algorithm for phonetic similarity.
 */
public class DoubleMetaphoneEncoder {

    private final DoubleMetaphone doubleMetaphone;

    /**
     * Constructs a DoubleMetaphoneEncoder instance.
     */
    public DoubleMetaphoneEncoder() {
        this.doubleMetaphone = new DoubleMetaphone();
    }

    /**
     * Encodes a word using Double Metaphone.
     *
     * @param word The input word
     * @return The primary Double Metaphone code
     */
    public String encode(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }
        return doubleMetaphone.encode(word);
    }

    /**
     * Encodes a word and returns the alternative Double Metaphone code.
     *
     * @param word The input word
     * @return The alternate Double Metaphone code
     */
    public String encodeAlternate(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }
        return doubleMetaphone.doubleMetaphone(word, true);
    }

    /**
     * Checks if two words have the same Double Metaphone encoding.
     *
     * @param word1 The first word
     * @param word2 The second word
     * @return True if the words have matching phonetic encodings
     */
    public boolean isPhoneticMatch(String word1, String word2) {
        return encode(word1).equals(encode(word2)) || encodeAlternate(word1).equals(encodeAlternate(word2));
    }
}
