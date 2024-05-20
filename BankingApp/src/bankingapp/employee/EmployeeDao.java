package bankingapp.employee;

import java.sql.SQLException;

public interface EmployeeDao {
    int addEmployee(Employee employee);
    boolean loginEmployee(int employeeID);
}
