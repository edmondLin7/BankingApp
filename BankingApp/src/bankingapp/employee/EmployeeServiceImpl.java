package bankingapp.employee;

import bankingapp.customer.Customer;
import bankingapp.customer.CustomerDao;
import bankingapp.customer.CustomerDaoImpl;

import java.util.Scanner;

public class EmployeeServiceImpl {
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    private Scanner scanner = new Scanner(System.in);
    public int registerEmployee(String name, String position, String email) {
        Employee employee = new Employee(name, position, email);
        return employeeDao.addEmployee(employee);
    }
    public boolean loginEmployee() {
        System.out.print("Enter employee ID: ");
        int employeeID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return employeeDao.loginEmployee(employeeID);
    }
}
