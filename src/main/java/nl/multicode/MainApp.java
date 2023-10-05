package nl.multicode;

import java.util.List;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) {

        final Map<String, Map<Integer, String>> results = new AutoGrouper().autoGroupSentences(
                List.of(
                        "Mark is an accountant and is in charge of money",
                        "Jaqueline is a designer, she creates graphics",
                        "Sayf is a programmer, he writes software",
                        "Eddy is the manager of software development",
                        "Judith writes tutorials for the software products",
                        "Bob is an account manager, he manages customers accounts",
                        "Quint is also in the graphics department and he makes icons",
                        "Roxan is the cleaning lady, we would be lost without her"), 3, 2);

        System.out.println(results.size() + " groups were found!");
        results.keySet().forEach(key -> {
            System.out.println("group elements found under key '" + key + "' are {");
            final Map<Integer, String> group = results.get(key);
            group.keySet().forEach(elementKey -> System.out.println("  " + group.get(elementKey)));
            System.out.println("}");
        });
    }

}
