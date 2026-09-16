package streams.advanced;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Q: How do you write a custom Collector?
 */
public class CustomCollector {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Custom collector to collect into an unmodifiable list
        System.out.println("=== Custom Collector: ImmutableList ===");
        List<Integer> immutable = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collector.of(
                        ArrayList<Integer>::new,       // supplier
                        ArrayList::add,               // accumulator
                        (left, right) -> {            // combiner (for parallel)
                            left.addAll(right);
                            return left;
                        },
                        Collections::unmodifiableList  // finisher
                ));
        System.out.println(immutable);

        // Custom collector: comma-separated with custom formatting
        System.out.println("\n=== Custom Collector: formatted string ===");
        String result = numbers.stream()
                .collect(Collector.of(
                        StringBuilder::new,
                        (sb, n) -> {
                            if (sb.length() > 0) sb.append(" | ");
                            sb.append("#").append(n);
                        },
                        (sb1, sb2) -> {
                            if (sb1.length() > 0) sb1.append(" | ");
                            sb1.append(sb2);
                            return sb1;
                        },
                        StringBuilder::toString
                ));
        System.out.println(result);

        // Practical: collect into LinkedHashSet (preserving insertion order, no duplicates)
        System.out.println("\n=== Collect into LinkedHashSet ===");
        List<String> words = Arrays.asList("banana", "apple", "banana", "cherry", "apple");
        LinkedHashSet<String> ordered = words.stream()
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println(ordered);
    }
}
