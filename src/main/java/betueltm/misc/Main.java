package betueltm.misc;

import java.util.HashSet;

public class Main {

	public static void main(String[] args) {
		Employee employee = new Employee("rajeev", 24L);
        Employee employee2 = new Employee("rajeev", 24L);

        HashSet<Employee> employees = new HashSet<Employee>();
        employees.add(employee);
        System.out.println(employees.contains(employee2));
        System.out.println("employee.hashCode():  " + employee.hashCode() + "  employee2.hashCode():" + employee2.hashCode());
    }
}
