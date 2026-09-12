package streams.intermediate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Q: What is the difference between map() and flatMap()?
 *
 * map()     -> one-to-one transformation (T -> R)
 * flatMap() -> one-to-many transformation + flattening (T -> Stream<R>)
 */
public class FlatMapExamples {

    public static void main(String[] args) {

        // Problem: Flatten a list of lists
        List<List<Integer>> nested = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5),
                Arrays.asList(6, 7, 8, 9)
        );

        // Using map — gives Stream<List<Integer>> (NOT what we want)
        System.out.println("=== map() gives nested structure ===");
        List<List<Integer>> mapped = nested.stream()
                .map(l -> l)  // each element is still a List
                .collect(Collectors.toList());
        System.out.println(mapped); // [[1,2,3],[4,5],[6,7,8,9]]

        // Using flatMap — flattens into Stream<Integer>
        System.out.println("\n=== flatMap() flattens ===");
        List<Integer> flat = nested.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println(flat); // [1,2,3,4,5,6,7,8,9]

        // Real-world: Get unique words from sentences
        System.out.println("\n=== Unique words from sentences ===");
        List<String> sentences = Arrays.asList(
                "Java streams are powerful",
                "Streams make code concise",
                "Java is great"
        );

        List<String> uniqueWords = sentences.stream()
                .map(s -> s.split(" "))       // String -> String[]
                .flatMap(Arrays::stream)       // String[] -> Stream<String>
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(uniqueWords);

        // flatMap with Optional (Java 9+ style, but concept is same)
        System.out.println("\n=== flatMap to extract chars from words ===");
        List<String> words = Arrays.asList("Hello", "World");
        List<Character> chars = words.stream()
                .flatMap(w -> w.chars().mapToObj(c -> (char) c))
                .collect(Collectors.toList());
        System.out.println(chars);
    }
}
