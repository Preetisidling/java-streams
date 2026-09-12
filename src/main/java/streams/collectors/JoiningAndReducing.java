package streams.collectors;

import streams.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Q: How do you join strings using Streams? Explain Collectors.joining() and reducing().
 */
public class JoiningAndReducing {

    public static void main(String[] args) {
        List<Employee> employees = Employee.getSampleEmployees();

        // joining — concatenate strings
        System.out.println("=== joining() ===");
        String names = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining());
        System.out.println(names); // AliceBobCharlie...

        // joining with delimiter
        System.out.println("\n=== joining with delimiter ===");
        String csv = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", "));
        System.out.println(csv);

        // joining with delimiter, prefix, suffix
        System.out.println("\n=== joining with prefix/suffix ===");
        String formatted = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println(formatted);

        // Collectors.reducing — more general form of reduce
        System.out.println("\n=== Collectors.reducing: total salary ===");
        Double totalSalary = employees.stream()
                .collect(Collectors.reducing(0.0, Employee::getSalary, Double::sum));
        System.out.println("Total: $" + totalSalary);

        // Collectors.reducing with groupingBy
        System.out.println("\n=== Sum salary per department ===");
        employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.reducing(0.0, Employee::getSalary, Double::sum)))
                .forEach((dept, total) -> System.out.println(dept + ": $" + total));

        // Practical: frequency count of words
        System.out.println("\n=== Word frequency count ===");
        String text = "java streams java interview streams java coding";
        Arrays.stream(text.split(" "))
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .forEach((word, count) -> System.out.println(word + " -> " + count));
    }
}
