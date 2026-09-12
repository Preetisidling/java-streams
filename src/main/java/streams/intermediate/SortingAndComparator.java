package streams.intermediate;

import streams.model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Q: How do you sort a stream? Explain sorted() with Comparator.
 */
public class SortingAndComparator {

    public static void main(String[] args) {
        List<Employee> employees = Employee.getSampleEmployees();

        // Sort by salary ascending
        System.out.println("=== Sort by salary (ascending) ===");
        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary))
                .forEach(System.out::println);

        // Sort by salary descending
        System.out.println("\n=== Sort by salary (descending) ===");
        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .forEach(System.out::println);

        // Sort by department, then by salary descending
        System.out.println("\n=== Sort by department, then salary desc ===");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getDepartment)
                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed()))
                .forEach(System.out::println);

        // Find top 3 highest paid
        System.out.println("\n=== Top 3 highest paid ===");
        List<Employee> top3 = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(3)
                .collect(Collectors.toList());
        top3.forEach(System.out::println);

        // Find 2nd highest salary
        System.out.println("\n=== 2nd highest salary ===");
        Optional_secondHighest(employees);
    }

    private static void Optional_secondHighest(List<Employee> employees) {
        employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .ifPresent(s -> System.out.println("2nd highest salary: $" + s));
    }
}
