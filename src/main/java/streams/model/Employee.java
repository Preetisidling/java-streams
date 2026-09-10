package streams.model;

import java.util.Arrays;
import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;
    private int age;
    private String city;

    public Employee(int id, String name, String department, double salary, int age, String city) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.age = age;
        this.city = city;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public int getAge() { return age; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return name + " [" + department + ", $" + salary + ", age=" + age + ", " + city + "]";
    }

    public static List<Employee> getSampleEmployees() {
        return Arrays.asList(
            new Employee(1, "Alice", "Engineering", 95000, 30, "New York"),
            new Employee(2, "Bob", "Engineering", 85000, 25, "San Francisco"),
            new Employee(3, "Charlie", "HR", 70000, 35, "New York"),
            new Employee(4, "Diana", "HR", 72000, 28, "Chicago"),
            new Employee(5, "Eve", "Finance", 90000, 32, "San Francisco"),
            new Employee(6, "Frank", "Finance", 88000, 40, "New York"),
            new Employee(7, "Grace", "Engineering", 110000, 38, "Chicago"),
            new Employee(8, "Hank", "Engineering", 78000, 23, "San Francisco"),
            new Employee(9, "Ivy", "HR", 65000, 26, "Chicago"),
            new Employee(10, "Jack", "Finance", 95000, 45, "New York")
        );
    }
}
