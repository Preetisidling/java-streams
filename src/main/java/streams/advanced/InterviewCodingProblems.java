package streams.advanced;

import streams.model.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Common Java 8 Streams coding problems asked in interviews.
 */
public class InterviewCodingProblems {

    public static void main(String[] args) {

        // 1. Find duplicate elements in a list
        System.out.println("=== 1. Find duplicates ===");
        List<Integer> nums = Arrays.asList(1, 2, 3, 2, 4, 5, 3, 6, 1);
        Set<Integer> duplicates = nums.stream()
                .filter(n -> Collections.frequency(nums, n) > 1)
                .collect(Collectors.toSet());
        System.out.println("Duplicates: " + duplicates);

        // Efficient way using groupingBy
        Set<Integer> duplicates2 = nums.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        System.out.println("Duplicates (efficient): " + duplicates2);

        // 2. First non-repeated character in a string
        System.out.println("\n=== 2. First non-repeated character ===");
        String str = "aabbcdeff";
        Character firstNonRepeated = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        System.out.println("First non-repeated in '" + str + "': " + firstNonRepeated);

        // 3. Frequency of each character in a string
        System.out.println("\n=== 3. Character frequency ===");
        String text = "programming";
        Map<Character, Long> charFreq = text.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(charFreq);

        // 4. Sort map by values
        System.out.println("\n=== 4. Sort map by values ===");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 85);
        scores.put("Bob", 92);
        scores.put("Charlie", 78);
        scores.put("Diana", 95);

        Map<String, Integer> sortedByValue = scores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        System.out.println(sortedByValue);

        // 5. Find the longest string in a list
        System.out.println("\n=== 5. Longest string ===");
        List<String> words = Arrays.asList("Java", "Streams", "Programming", "API", "Interview");
        String longest = words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        System.out.println("Longest: " + longest);

        // 6. Convert list of strings to uppercase and sort
        System.out.println("\n=== 6. Uppercase + sort ===");
        List<String> sorted = words.stream()
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(sorted);

        // 7. Check if list is palindrome
        System.out.println("\n=== 7. Palindrome check using streams ===");
        String palindrome = "racecar";
        boolean isPalin = IntStream.range(0, palindrome.length() / 2)
                .allMatch(i -> palindrome.charAt(i) == palindrome.charAt(palindrome.length() - 1 - i));
        System.out.println("'" + palindrome + "' is palindrome: " + isPalin);

        // 8. Separate odd and even numbers
        System.out.println("\n=== 8. Separate odd and even ===");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Map<Boolean, List<Integer>> oddEven = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Even: " + oddEven.get(true));
        System.out.println("Odd: " + oddEven.get(false));

        // 9. Sum and average of a list
        System.out.println("\n=== 9. Sum and average ===");
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        double avg = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
        System.out.println("Sum: " + sum + ", Average: " + avg);

        // 10. Nth highest salary
        System.out.println("\n=== 10. Nth highest salary ===");
        List<Employee> employees = Employee.getSampleEmployees();
        int n = 3;
        Optional<Double> nthHighest = employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(n - 1)
                .findFirst();
        System.out.println(n + "rd highest salary: $" + nthHighest.orElse(0.0));

        // 11. Group employees by department and find the one with max salary in each
        System.out.println("\n=== 11. Max salary employee per department ===");
        employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),
                                opt -> opt.map(Employee::getName).orElse("None"))))
                .forEach((dept, name) -> System.out.println(dept + ": " + name));

        // 12. Fibonacci using streams
        System.out.println("\n=== 12. Fibonacci first 10 numbers ===");
        java.util.stream.Stream.iterate(new long[]{0, 1}, f -> new long[]{f[1], f[0] + f[1]})
              .limit(10)
              .map(f -> f[0])
              .forEach(n2 -> System.out.print(n2 + " "));
        System.out.println();

        // 13. Merge and remove duplicates from two lists
        System.out.println("\n=== 13. Merge two lists, remove duplicates ===");
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(3, 4, 5, 6, 7);
        List<Integer> merged = java.util.stream.Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(merged);

        // 14. Find common elements between two lists (intersection)
        System.out.println("\n=== 14. Intersection of two lists ===");
        List<Integer> common = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());
        System.out.println(common);
    }
}
