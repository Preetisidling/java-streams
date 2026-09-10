package streams.basics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Q: What are the different ways to create a Stream in Java 8?
 */
public class StreamCreation {

    public static void main(String[] args) {

        // 1. From Collection
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> fromList = list.stream();
        System.out.println("From Collection:");
        fromList.forEach(System.out::println);

        // 2. From Array
        String[] arr = {"x", "y", "z"};
        Stream<String> fromArray = Arrays.stream(arr);
        System.out.println("\nFrom Array:");
        fromArray.forEach(System.out::println);

        // 3. Using Stream.of()
        Stream<String> fromOf = Stream.of("hello", "world");
        System.out.println("\nStream.of():");
        fromOf.forEach(System.out::println);

        // 4. Using Stream.iterate() — infinite stream with limit
        System.out.println("\nStream.iterate (first 5 even numbers):");
        Stream.iterate(0, n -> n + 2)
              .limit(5)
              .forEach(System.out::println);

        // 5. Using Stream.generate() — infinite stream with supplier
        System.out.println("\nStream.generate (3 random numbers):");
        Stream.generate(Math::random)
              .limit(3)
              .forEach(System.out::println);

        // 6. IntStream range
        System.out.println("\nIntStream.range(1, 6):");
        IntStream.range(1, 6).forEach(System.out::println);

        // 7. IntStream.rangeClosed (inclusive end)
        System.out.println("\nIntStream.rangeClosed(1, 5):");
        IntStream.rangeClosed(1, 5).forEach(System.out::println);

        // 8. From String chars
        System.out.println("\nStream from String chars:");
        "hello".chars()
               .mapToObj(c -> (char) c)
               .forEach(System.out::println);
    }
}
