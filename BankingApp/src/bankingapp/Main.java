package bankingapp;

import bankingapp.customer.Customer;
import bankingapp.customer.CustomerDao;
import bankingapp.customer.CustomerDaoFactory;
import bankingapp.customer.CustomerServiceImpl;
import bankingapp.employee.Employee;
import bankingapp.employee.EmployeeDao;
import bankingapp.employee.EmployeeDaoFactory;
import bankingapp.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static CustomerServiceImpl customerService = new CustomerServiceImpl();
    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    public static void addEmployee() throws SQLException {
        /*
        Connection connection = ConnectionFactory.getConnection();

        String sql = "insert into employee (name, email) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "test");
        preparedStatement.setString(2, "test");
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("employee saved");
        } else {
            System.out.println("Opps! something went wrong, please try again");
        }
*/
    }
    public static void main(String[] args) {
        /*
        addEmployee();
         */
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Bank App");
        System.out.println("1. Register Customer");
        System.out.println("2. Apply for Account");
        System.out.println("3. View Account Balance");
        System.out.println("4. Exit");

        while (true) {
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (option) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();
                    customerService.registerCustomer(name, email);
                    break;
                case 2:
                    System.out.print("Enter employee name: ");
                    String employeeName = scanner.nextLine();
                    System.out.print("Enter employee position: ");
                    String position = scanner.nextLine();
                    System.out.print("Enter employee email: ");
                    String employeeEmail = scanner.nextLine();
                    int employeeId = employeeService.registerEmployee(employeeName, position, employeeEmail);
            }
        }
    }
}