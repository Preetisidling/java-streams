package streams.collectors;

import streams.model.Employee;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Q: Explain partitioningBy() vs groupingBy(). How do you use toMap()?
 */
public class PartitioningAndToMap {

    public static void main(String[] args) {
        List<Employee> employees = Employee.getSampleEmployees();

        // partitioningBy — splits into exactly TWO groups (true/false)
        System.out.println("=== partitioningBy: salary > 85k ===");
        Map<Boolean, List<Employee>> partitioned = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 85000));
        System.out.println("High earners: " + partitioned.get(true));
        System.out.println("Others: " + partitioned.get(false));

        // partitioningBy with downstream collector
        System.out.println("\n=== partitioningBy with count ===");
        Map<Boolean, Long> partitionCount = employees.stream()
                .collect(Collectors.partitioningBy(
                        e -> e.getAge() > 30,
                        Collectors.counting()));
        System.out.println("Over 30: " + partitionCount.get(true));
        System.out.println("30 or under: " + partitionCount.get(false));

        // toMap — convert stream to Map
        System.out.println("\n=== toMap: id -> name ===");
        Map<Integer, String> idToName = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));
        System.out.println(idToName);

        // toMap with merge function (handles duplicate keys)
        System.out.println("\n=== toMap: department -> highest salary (merge on conflict) ===");
        Map<String, Double> deptMaxSalary = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getDepartment,
                        Employee::getSalary,
                        Double::max));  // if same dept, keep higher salary
        System.out.println(deptMaxSalary);

        // toMap with merge + custom map type (preserve insertion order)
        System.out.println("\n=== toMap: name -> salary (LinkedHashMap) ===");
        Map<String, Double> nameToSalary = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getName,
                        Employee::getSalary,
                        (s1, s2) -> s1,
                        LinkedHashMap::new));
        System.out.println(nameToSalary);
    }
}
