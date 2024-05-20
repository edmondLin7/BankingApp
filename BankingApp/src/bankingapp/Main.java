package bankingapp;

import bankingapp.account.Account;
import bankingapp.account.AccountServiceImpl;
import bankingapp.accountrequest.AccountReqServiceImpl;
import bankingapp.accountrequest.AccountRequest;
import bankingapp.customer.Customer;
import bankingapp.customer.CustomerDao;
import bankingapp.customer.CustomerDaoFactory;
import bankingapp.customer.CustomerServiceImpl;
import bankingapp.employee.Employee;
import bankingapp.employee.EmployeeDao;
import bankingapp.employee.EmployeeDaoFactory;
import bankingapp.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CustomerServiceImpl customerService = new CustomerServiceImpl();
    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    private static AccountReqServiceImpl accountReqService = new AccountReqServiceImpl();

    private static AccountServiceImpl accountService = new AccountServiceImpl();

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
        System.out.println("2. Register Account");
        System.out.println("3. Employee Login");
        System.out.println("4. Request to create an banking account");
        System.out.println("5. Create Account If Approved");
        System.out.println("6. Customer Deposit money");


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
                    break;
                case 3:
                    if(employeeService.loginEmployee()) { // if employee login successful
                        System.out.println("1. Show all account request: ");
                        System.out.println("2. Accept or Deny account request creation: ");
                        int selection = scanner.nextInt();
                        if (selection == 1) {
                            accountReqService.getAllAccountRequests();
                        } else if (selection == 2) {
                            System.out.println("All Account Requests");
                            accountReqService.getAllAccountRequests();
                            System.out.println("1. Accept an account");
                            System.out.println("2. Deny an account");
                            int selection2 = scanner.nextInt();
                            if (selection2 == 1) {
                                accountReqService.approveAccountRequest();
                            } else if (selection2 == 2) {
                                accountReqService.rejectAccountRequest();
                            }
                        }
                    }
                    break;
                case 4:
                    accountReqService.createAccountRequest();
                    break;
                case 5:
                    System.out.print("Enter custID to see your pending account requests ");
                    int custID = scanner.nextInt();
                    List<AccountRequest> accountRequestList = accountReqService.getAllCustIdAccountRequests(custID);
                    System.out.print("Enter request ID to create account: ");
                    int requestId = scanner.nextInt();
                    AccountRequest accRequest = new AccountRequest(-1, -1, "N");
                    for (AccountRequest acc : accountRequestList) {
                        if (acc.getRequestId() == requestId) {
                            accRequest = acc;
                        }
                    }
                    if (accRequest.getStatus().equals("A")) {
                        accountService.createAccount();
                        accountReqService.deleteAccountRequest(accRequest.getRequestId());
                    } else {
                        System.out.println("Account not approved");
                    }
                break;
                    // int accountId = accountService.createAccountIfApproved(createRequestId);
                case 6:
                    accountService.findAccountsByCustomerId();
                    accountService.deposit();
            }
        }
    }
}