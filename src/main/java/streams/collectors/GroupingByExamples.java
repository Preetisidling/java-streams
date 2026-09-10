package streams.collectors;

import streams.model.Employee;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Q: Explain Collectors.groupingBy() and its variations.
 * This is one of the MOST asked interview questions on Streams.
 */
public class GroupingByExamples {

    public static void main(String[] args) {
        List<Employee> employees = Employee.getSampleEmployees();

        // 1. Simple grouping: group employees by department
        System.out.println("=== Group by Department ===");
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        byDept.forEach((dept, emps) -> {
            System.out.println(dept + ": " + emps);
        });

        // 2. Group and count
        System.out.println("\n=== Count per Department ===");
        Map<String, Long> countByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println(countByDept);

        // 3. Group and get average salary
        System.out.println("\n=== Average Salary per Department ===");
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(avgSalaryByDept);

        // 4. Group and get max salary employee per department
        System.out.println("\n=== Highest Paid per Department ===");
        Map<String, Optional<Employee>> maxSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))));
        maxSalaryByDept.forEach((dept, emp) ->
                System.out.println(dept + ": " + emp.orElse(null)));

        // 5. Group and collect names
        System.out.println("\n=== Names per Department ===");
        Map<String, List<String>> namesByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println(namesByDept);

        // 6. Group and join names as comma-separated string
        System.out.println("\n=== Names joined per Department ===");
        Map<String, String> joinedNames = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.mapping(Employee::getName, Collectors.joining(", "))));
        System.out.println(joinedNames);

        // 7. Multi-level grouping: by department, then by city
        System.out.println("\n=== Group by Department then City ===");
        Map<String, Map<String, List<Employee>>> byDeptThenCity = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.groupingBy(Employee::getCity)));
        byDeptThenCity.forEach((dept, cityMap) -> {
            System.out.println(dept + ":");
            cityMap.forEach((city, emps) -> System.out.println("  " + city + " -> " + emps));
        });

        // 8. Group with summarizing statistics
        System.out.println("\n=== Salary Statistics per Department ===");
        Map<String, DoubleSummaryStatistics> statsByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.summarizingDouble(Employee::getSalary)));
        statsByDept.forEach((dept, stats) ->
                System.out.println(dept + ": count=" + stats.getCount()
                        + " avg=" + stats.getAverage()
                        + " max=" + stats.getMax()
                        + " min=" + stats.getMin()));
    }
}
