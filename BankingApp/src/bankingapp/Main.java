package bankingapp;

import bankingapp.customer.Customer;
import bankingapp.customer.CustomerDao;
import bankingapp.customer.CustomerDaoFactory;
import bankingapp.employee.Employee;
import bankingapp.employee.EmployeeDao;
import bankingapp.employee.EmployeeDaoFactory;

import java.sql.SQLException;

public class Main {
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
    public static void main(String[] args) throws SQLException {
        /*
        addEmployee();
         */
        CustomerDao customerDao = CustomerDaoFactory.getCustomerDao();
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();
        customerDao.addCustomer(new Customer("Jaden Smith", "JSmith@gmail.com"));
        employeeDao.addEmployee(new Employee("Jaden Smith", "admin", "JSmith@gmail.com"));

    }
}