package streams.parallel;

import streams.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Q: What is a parallel stream? When should you use it?
 * Q: What are the pitfalls of parallel streams?
 */
public class ParallelStreamExamples {

    public static void main(String[] args) {

        /*
         * WHEN TO USE PARALLEL STREAMS:
         * - Large datasets (10k+ elements)
         * - CPU-intensive operations (no I/O)
         * - Stateless, non-interfering operations
         * - Operations that can be easily decomposed
         *
         * WHEN NOT TO USE:
         * - Small datasets (overhead > benefit)
         * - I/O-bound operations
         * - Operations that require ordering
         * - Shared mutable state
         * - LinkedList or iterators (poor splitting)
         */

        // Basic parallel stream
        System.out.println("=== Parallel Stream: sum of 1 to 1M ===");
        long start = System.currentTimeMillis();
        long sum = IntStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
        long end = System.currentTimeMillis();
        System.out.println("Sum: " + sum + " (parallel: " + (end - start) + "ms)");

        start = System.currentTimeMillis();
        sum = IntStream.rangeClosed(1, 1_000_000)
                .sum();
        end = System.currentTimeMillis();
        System.out.println("Sum: " + sum + " (sequential: " + (end - start) + "ms)");

        // Parallel with collect
        System.out.println("\n=== Parallel collect ===");
        List<Employee> employees = Employee.getSampleEmployees();
        List<String> names = employees.parallelStream()
                .filter(e -> e.getSalary() > 80000)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("High earners: " + names);

        // Thread safety issue demo
        System.out.println("\n=== WRONG: shared mutable state with parallel ===");
        List<Integer> unsafeList = new java.util.ArrayList<>();
        IntStream.rangeClosed(1, 1000).parallel().forEach(unsafeList::add);
        System.out.println("Expected 1000, got: " + unsafeList.size()); // often < 1000!

        System.out.println("\n=== CORRECT: use collect instead ===");
        List<Integer> safeList = IntStream.rangeClosed(1, 1000)
                .parallel()
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Expected 1000, got: " + safeList.size()); // always 1000

        // forEachOrdered preserves encounter order in parallel
        System.out.println("\n=== forEachOrdered preserves order ===");
        System.out.print("forEach (unordered): ");
        Arrays.asList(1, 2, 3, 4, 5).parallelStream().forEach(n -> System.out.print(n + " "));
        System.out.print("\nforEachOrdered: ");
        Arrays.asList(1, 2, 3, 4, 5).parallelStream().forEachOrdered(n -> System.out.print(n + " "));
        System.out.println();
    }
}
