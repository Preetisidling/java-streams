package streams.advanced;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Q: What are primitive streams? Why use IntStream, LongStream, DoubleStream?
 *
 * Primitive streams avoid autoboxing overhead.
 * They also provide sum(), average(), min(), max() directly.
 */
public class PrimitiveStreams {

    public static void main(String[] args) {

        // IntStream basics
        System.out.println("=== IntStream operations ===");
        int sum = IntStream.of(1, 2, 3, 4, 5).sum();
        System.out.println("Sum: " + sum);

        OptionalDouble avg = IntStream.rangeClosed(1, 100).average();
        System.out.println("Average 1-100: " + avg.getAsDouble());

        int max = IntStream.of(10, 3, 7, 15, 2).max().orElse(0);
        System.out.println("Max: " + max);

        // mapToInt — convert object stream to IntStream
        System.out.println("\n=== mapToInt from objects ===");
        List<String> words = Arrays.asList("Java", "Streams", "API", "Interview");
        int totalLength = words.stream()
                .mapToInt(String::length)
                .sum();
        System.out.println("Total character count: " + totalLength);

        // IntSummaryStatistics — get all stats at once
        System.out.println("\n=== IntSummaryStatistics ===");
        IntSummaryStatistics stats = IntStream.of(5, 10, 15, 20, 25)
                .summaryStatistics();
        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Max: " + stats.getMax());
        System.out.println("Avg: " + stats.getAverage());

        // Converting between stream types
        System.out.println("\n=== Stream type conversions ===");

        // int[] to IntStream
        int[] arr = {1, 2, 3, 4, 5};
        IntStream fromArray = Arrays.stream(arr);
        System.out.println("IntStream sum: " + fromArray.sum());

        // IntStream to int[]
        int[] generated = IntStream.range(1, 6).toArray();
        System.out.println("Generated array: " + Arrays.toString(generated));

        // IntStream to Stream<Integer> (boxing)
        List<Integer> boxed = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(java.util.stream.Collectors.toList());
        System.out.println("Boxed: " + boxed);

        // DoubleStream
        System.out.println("\n=== DoubleStream ===");
        double dSum = DoubleStream.of(1.5, 2.5, 3.5).sum();
        System.out.println("Double sum: " + dSum);
    }
}
