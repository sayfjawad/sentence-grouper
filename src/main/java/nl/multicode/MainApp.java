package nl.multicode;

import java.util.List;
import java.util.Map;
import nl.multicode.group.AutoGrouper;
import nl.multicode.group.SentenceGrouper;

public class MainApp {

    public static void main(String[] args) {

        final List<String> sentences1 = List.of(
                "Mark is an accountant and is in charge of money",
                "Jaqueline is a designer, she creates graphics",
                "Sayf is a programmer, he writes software",
                "Eddy is the manager of software development",
                "Judith writes tutorials for the software products",
                "Bob is an account manager, he manages customers accounts",
                "Quint is also in the graphics department and he makes icons",
                "Roxan is the cleaning lady, we would be lost without her");

        final List<String> sentences2 = List.of(
                "The sun danced across the morning sky, spreading warmth and hope.",
                "Every book on the shelf has a story to tell.",
                "When the clock struck midnight, the town was silent.",
                "The cat stared intently at the fluttering butterfly.",
                "She found solace in the rhythm of the raindrops on her window.",
                "Lost in thought, he wandered through the maze-like forest.",
                "Her laughter was contagious, filling the room with joy.",
                "He craved the taste of adventure and the thrill of the unknown.",
                "The mountains stood tall, guardians of ancient secrets.",
                "With each brushstroke, the painting came to life.",
                "The mysterious letter hinted at a long-forgotten past.",
                "Under the moonlight, the lake shimmered like a mirror.",
                "Whispers of forgotten tales echoed through the old mansion.",
                "The aroma of fresh bread wafted from the village bakery.",
                "His music transported listeners to a world of dreams.",
                "The waves kissed the shore, leaving traces of salt and memories.",
                "She danced with the grace and beauty of a falling leaf.",
                "With every step, the mountain's peak seemed just a bit closer.",
                "The children's giggles were the soundtrack of the summer.",
                "In the heart of the city, an old tree told tales of yesteryears.",
                "He searched for answers in the pages of dusty old books.",
                "The desert, with its endless sands, held tales of mirages and mysteries.",
                "She wore her scars like badges of honor, each one telling a story.",
                "With a twinkle in his eye, the magician unveiled his next trick.",
                "Every night, the stars whispered stories of distant galaxies.",
                "The meadow was a riot of colors, with flowers blooming everywhere.",
                "With the map in hand, they set off in search of buried treasure.",
                "The lighthouse stood firm, guiding ships through the stormy night.",
                "In the depths of winter, the fireplace told tales of warmth and family.",
                "She wrote letters to the future, hoping they would find their way."
        );

        analyseSentences(sentences1);
        analyseSentences(sentences2);
    }

    private static void analyseSentences(List<String> sentences) {
        // my own algorithm
        final int minimalDenominatorLength = 3;
        final int minimumGroupSize = 2;
        final var originalGroupingAlgorithResult = new AutoGrouper().autoGroupSentences(sentences, minimalDenominatorLength, minimumGroupSize);
        System.out.println(originalGroupingAlgorithResult.size() + " groups were found!");
        originalGroupingAlgorithResult.keySet().forEach(key -> {
            System.out.println("group elements found under key '" + key + "' are {");
            final Map<Integer, String> group = originalGroupingAlgorithResult.get(key);
            group.keySet().forEach(elementKey -> System.out.println("  " + group.get(elementKey)));
            System.out.println("}");
        });

        // AI algorithm
        final var aiGeneratedGroupingAlgorithm = new SentenceGrouper().groupSentences(sentences);
        for (Map.Entry<Integer, List<String>> entry : aiGeneratedGroupingAlgorithm.entrySet()) {
            System.out.println("Group " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
