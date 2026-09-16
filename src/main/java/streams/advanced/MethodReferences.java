package streams.advanced;

import streams.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Q: What are Method References? What are the 4 types?
 */
public class MethodReferences {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");

        // Type 1: Reference to a static method — ClassName::staticMethod
        System.out.println("=== Static method reference ===");
        List<Integer> numbers = Arrays.asList(1, -2, 3, -4, 5);
        numbers.stream()
                .map(Math::abs)  // equivalent to n -> Math.abs(n)
                .forEach(System.out::println);

        // Type 2: Reference to instance method of a particular object — object::method
        System.out.println("\n=== Instance method of specific object ===");
        names.forEach(System.out::println);  // System.out is the specific object

        // Type 3: Reference to instance method of an arbitrary object — ClassName::method
        System.out.println("\n=== Instance method of arbitrary object ===");
        List<String> upperNames = names.stream()
                .map(String::toUpperCase)  // equivalent to s -> s.toUpperCase()
                .collect(Collectors.toList());
        System.out.println(upperNames);

        // Type 4: Constructor reference — ClassName::new
        System.out.println("\n=== Constructor reference ===");
        List<String> words = Arrays.asList("hello world", "java streams");
        List<StringBuilder> builders = words.stream()
                .map(StringBuilder::new)  // equivalent to s -> new StringBuilder(s)
                .collect(Collectors.toList());
        builders.forEach(sb -> System.out.println(sb.reverse()));

        // Functional interfaces used with streams
        System.out.println("\n=== Common Functional Interfaces ===");

        // Predicate<T> — takes T, returns boolean
        Predicate<String> startsWithA = s -> s.startsWith("A");
        List<String> aNames = names.stream().filter(startsWithA).collect(Collectors.toList());
        System.out.println("Names starting with A: " + aNames);

        // Function<T, R> — takes T, returns R
        Function<String, Integer> length = String::length;
        List<Integer> lengths = names.stream().map(length).collect(Collectors.toList());
        System.out.println("Name lengths: " + lengths);

        // Consumer<T> — takes T, returns void
        Consumer<String> printer = System.out::println;
        System.out.print("Consuming: ");
        names.forEach(n -> System.out.print(n + " "));
        System.out.println();

        // Supplier<T> — takes nothing, returns T
        Supplier<List<String>> listFactory = java.util.ArrayList::new;
        List<String> newList = listFactory.get();
        System.out.println("New list type: " + newList.getClass().getSimpleName());

        // BiFunction<T, U, R> — takes T and U, returns R
        BiFunction<String, String, String> concat = String::concat;
        System.out.println("BiFunction concat: " + concat.apply("Hello ", "World"));

        // UnaryOperator<T> — takes T, returns T (special case of Function)
        UnaryOperator<String> exclaim = s -> s + "!";
        List<String> excited = names.stream().map(exclaim).collect(Collectors.toList());
        System.out.println("Excited: " + excited);

        // BinaryOperator<T> — takes (T, T), returns T (special case of BiFunction)
        BinaryOperator<Integer> add = Integer::sum;
        int total = Arrays.asList(1, 2, 3, 4, 5).stream().reduce(0, add);
        System.out.println("Sum via BinaryOperator: " + total);
    }
}
