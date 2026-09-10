package streams.basics;

import streams.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Q: Explain filter(), map(), and reduce() with examples.
 */
public class FilterMapReduce {

    public static void main(String[] args) {
        List<Employee> employees = Employee.getSampleEmployees();

        // ---- FILTER: select elements matching a predicate ----
        System.out.println("=== Engineers earning > 90k ===");
        List<Employee> highPaidEngineers = employees.stream()
                .filter(e -> e.getDepartment().equals("Engineering"))
                .filter(e -> e.getSalary() > 90000)
                .collect(Collectors.toList());
        highPaidEngineers.forEach(System.out::println);

        // ---- MAP: transform each element ----
        System.out.println("\n=== All employee names (uppercase) ===");
        List<String> names = employees.stream()
                .map(Employee::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(names);

        // ---- REDUCE: combine elements into a single result ----
        System.out.println("\n=== Total salary (using reduce) ===");
        double totalSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(0.0, Double::sum);
        System.out.println("Total: $" + totalSalary);

        // reduce with no identity — returns Optional
        System.out.println("\n=== Max salary (using reduce) ===");
        Optional<Double> maxSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::max);
        maxSalary.ifPresent(s -> System.out.println("Max: $" + s));

        // ---- Chaining filter + map + reduce ----
        System.out.println("\n=== Average salary in Finance ===");
        double avgFinanceSalary = employees.stream()
                .filter(e -> e.getDepartment().equals("Finance"))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        System.out.println("Avg Finance Salary: $" + avgFinanceSalary);
    }
}
