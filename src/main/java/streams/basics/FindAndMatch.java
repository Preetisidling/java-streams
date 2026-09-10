package streams.basics;

import streams.model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Q: Explain findFirst(), findAny(), anyMatch(), allMatch(), noneMatch().
 */
public class FindAndMatch {

    public static void main(String[] args) {
        List<Employee> employees = Employee.getSampleEmployees();

        // findFirst — returns first element in encounter order
        System.out.println("=== findFirst: first engineer ===");
        Optional<Employee> firstEngineer = employees.stream()
                .filter(e -> e.getDepartment().equals("Engineering"))
                .findFirst();
        firstEngineer.ifPresent(System.out::println);

        // findAny — returns any element (useful with parallel streams)
        System.out.println("\n=== findAny: any HR employee ===");
        Optional<Employee> anyHR = employees.parallelStream()
                .filter(e -> e.getDepartment().equals("HR"))
                .findAny();
        anyHR.ifPresent(System.out::println);

        // anyMatch — true if ANY element matches
        System.out.println("\n=== anyMatch: anyone earning > 100k? ===");
        boolean anyHighEarner = employees.stream()
                .anyMatch(e -> e.getSalary() > 100000);
        System.out.println(anyHighEarner); // true (Grace earns 110k)

        // allMatch — true if ALL elements match
        System.out.println("\n=== allMatch: everyone older than 20? ===");
        boolean allAbove20 = employees.stream()
                .allMatch(e -> e.getAge() > 20);
        System.out.println(allAbove20); // true

        // noneMatch — true if NO elements match
        System.out.println("\n=== noneMatch: nobody in Marketing? ===");
        boolean noMarketing = employees.stream()
                .noneMatch(e -> e.getDepartment().equals("Marketing"));
        System.out.println(noMarketing); // true
    }
}
