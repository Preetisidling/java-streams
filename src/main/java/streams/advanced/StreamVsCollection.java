package streams.advanced;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Q: What are the key differences between Stream and Collection?
 * Q: What is lazy evaluation in Streams?
 * Q: Can you reuse a Stream?
 */
public class StreamVsCollection {

    public static void main(String[] args) {

        /*
         * KEY DIFFERENCES:
         * 1. Collection stores data; Stream computes data on-demand
         * 2. Collection is eagerly populated; Stream is lazily evaluated
         * 3. Collection can be iterated multiple times; Stream can be consumed ONLY ONCE
         * 4. Stream operations can be short-circuited (findFirst, limit, anyMatch)
         * 5. Streams don't modify the source collection
         */

        // LAZY EVALUATION DEMO
        System.out.println("=== Lazy Evaluation Demo ===");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        // Nothing executes until terminal operation (collect/forEach/reduce)
        Stream<Integer> lazyStream = numbers.stream()
                .filter(n -> {
                    System.out.println("  filter: " + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    System.out.println("  map: " + n);
                    return n * 10;
                });

        System.out.println("Stream created but nothing printed yet!");
        System.out.println("Now triggering terminal operation...");
        lazyStream.forEach(n -> System.out.println("  result: " + n));

        // SHORT-CIRCUIT DEMO: not all elements are processed
        System.out.println("\n=== Short-Circuit with findFirst ===");
        numbers.stream()
                .filter(n -> {
                    System.out.println("  checking: " + n);
                    return n > 3;
                })
                .findFirst()
                .ifPresent(n -> System.out.println("  found: " + n));
        // Only checks 1, 2, 3, 4 — stops at 4!

        // STREAM CANNOT BE REUSED
        System.out.println("\n=== Stream can only be consumed once ===");
        Stream<String> stream = Stream.of("a", "b", "c");
        stream.forEach(System.out::println);
        try {
            stream.forEach(System.out::println); // throws IllegalStateException
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
