package bankingapp.employee;

import bankingapp.employee.EmployeeDao;
import bankingapp.employee.EmployeeDaoImpl;

public class EmployeeDaoFactory {
    private static EmployeeDao employeeDao;

    private EmployeeDaoFactory() {
    }

    public static EmployeeDao getEmployeeDao() {
        if (employeeDao == null) {
            employeeDao = new EmployeeDaoImpl();
        }

        return employeeDao;
    }
}
