package bankingapp.employee;

import bankingapp.customer.Customer;

import java.sql.SQLException;

public interface EmployeeDao {
    void addEmployee(Employee employee) throws SQLException;

}
