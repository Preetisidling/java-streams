package streams.intermediate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Q: Explain distinct(), limit(), skip(), and peek().
 */
public class DistinctLimitSkipPeek {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);

        // distinct — removes duplicates (uses equals/hashCode)
        System.out.println("=== distinct ===");
        List<Integer> unique = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(unique); // [3, 1, 4, 5, 9, 2, 6]

        // limit — takes first N elements
        System.out.println("\n=== limit(3) ===");
        List<Integer> firstThree = numbers.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(firstThree); // [3, 1, 4]

        // skip — skips first N elements
        System.out.println("\n=== skip(5) ===");
        List<Integer> afterSkip = numbers.stream()
                .skip(5)
                .collect(Collectors.toList());
        System.out.println(afterSkip); // [9, 2, 6, 5, 3, 5]

        // Pagination pattern: skip + limit
        System.out.println("\n=== Pagination: page 2, size 3 ===");
        int page = 2, pageSize = 3;
        List<Integer> page2 = numbers.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        System.out.println(page2); // [1, 5, 9]

        // peek — perform an action without modifying the stream (useful for debugging)
        System.out.println("\n=== peek (debugging pipeline) ===");
        List<Integer> result = numbers.stream()
                .filter(n -> n > 3)
                .peek(n -> System.out.println("  after filter: " + n))
                .map(n -> n * 2)
                .peek(n -> System.out.println("  after map: " + n))
                .collect(Collectors.toList());
        System.out.println("Result: " + result);
    }
}
