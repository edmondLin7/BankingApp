package bankingapp.test;

import bankingapp.employee.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl();
    }

    @Test
    void testRegisterEmployeeReturnsNumber() {
        String employeeName = "John Doe";
        String position = "Software Engineer";
        String employeeEmail = "john.doe@example.com";

        int employeeId = employeeService.registerEmployee(employeeName, position, employeeEmail);

        // Check if the returned employeeId is a number
        assertTrue(employeeId > 0, "Employee ID should be a positive number");
    }
}
