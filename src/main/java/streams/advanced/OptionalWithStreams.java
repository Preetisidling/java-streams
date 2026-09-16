package streams.advanced;

import streams.model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Q: How does Optional work with Streams?
 */
public class OptionalWithStreams {

    public static void main(String[] args) {
        List<Employee> employees = Employee.getSampleEmployees();

        // findFirst returns Optional
        System.out.println("=== Optional from findFirst ===");
        Optional<Employee> found = employees.stream()
                .filter(e -> e.getDepartment().equals("Marketing"))
                .findFirst();

        // ifPresent — execute action only if value exists
        found.ifPresent(e -> System.out.println("Found: " + e));

        // orElse — provide default value
        Employee result = found.orElse(new Employee(0, "Unknown", "N/A", 0, 0, "N/A"));
        System.out.println("Result: " + result);

        // orElseGet — provide default via supplier (lazy)
        Employee lazyResult = found.orElseGet(() ->
                new Employee(0, "Default", "N/A", 0, 0, "N/A"));
        System.out.println("Lazy result: " + lazyResult);

        // orElseThrow — throw exception if empty
        try {
            Employee mustExist = found.orElseThrow(
                    () -> new RuntimeException("No Marketing employees found"));
        } catch (RuntimeException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // map on Optional
        System.out.println("\n=== map on Optional ===");
        Optional<String> highestPaidName = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .map(Employee::getName);
        System.out.println("Highest paid: " + highestPaidName.orElse("None"));

        // filter on Optional
        System.out.println("\n=== filter on Optional ===");
        Optional<Employee> seniorEngineer = employees.stream()
                .filter(e -> e.getDepartment().equals("Engineering"))
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .filter(e -> e.getAge() > 30);
        System.out.println("Senior highest paid engineer: " +
                seniorEngineer.map(Employee::getName).orElse("None matching criteria"));

        // Handling null values in a list
        System.out.println("\n=== Filtering nulls from a list ===");
        List<String> withNulls = Arrays.asList("Java", null, "Streams", null, "API");
        List<String> nonNulls = withNulls.stream()
                .filter(s -> s != null)  // or Objects::nonNull
                .collect(java.util.stream.Collectors.toList());
        System.out.println(nonNulls);
    }
}
