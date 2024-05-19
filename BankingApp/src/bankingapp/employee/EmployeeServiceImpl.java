package bankingapp.employee;

import bankingapp.customer.Customer;
import bankingapp.customer.CustomerDao;
import bankingapp.customer.CustomerDaoImpl;

public class EmployeeServiceImpl {
    private EmployeeDao employeeDAO = new EmployeeDaoImpl();

    public int registerEmployee(String name, String position, String email) {
        Employee employee = new Employee(name, position, email);
        return employeeDAO.addEmployee(employee);
    }
}
