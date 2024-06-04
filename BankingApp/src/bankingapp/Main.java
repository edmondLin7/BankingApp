package bankingapp;

import bankingapp.account.AccountServiceImpl;
import bankingapp.accountrequest.AccountReqServiceImpl;
import bankingapp.accountrequest.AccountRequest;
import bankingapp.customer.CustomerServiceImpl;
import bankingapp.employee.EmployeeServiceImpl;
import bankingapp.transactionlog.TransactionLogServiceImpl;
import bankingapp.transfer.TransferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static CustomerServiceImpl customerService = new CustomerServiceImpl();
    private static EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    private static AccountReqServiceImpl accountReqService = new AccountReqServiceImpl();
    private static AccountServiceImpl accountService = new AccountServiceImpl();
    private static TransferServiceImpl transferService = new TransferServiceImpl();
    private static TransactionLogServiceImpl transactionLogService = new TransactionLogServiceImpl();

    public static void addEmployee() throws SQLException {
        // Implementation of adding an employee
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        logger.info("Banking application started.");

        System.out.println("Welcome to the Bank App");
        System.out.println("1. Register Customer");
        System.out.println("2. Register Account");
        System.out.println("3. Employee Login");
        System.out.println("4. Request to create a banking account");
        System.out.println("5. Create Account If Approved");
        System.out.println("6. Customer Deposit money");
        System.out.println("7. Customer Withdraw money");
        System.out.println("8. Make Transfer money request");
        System.out.println("9. Accept Transfer money");
        System.out.println("10. Transaction history");

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
                    logger.info("Customer registered: name={}, email={}", name, email);
                    break;
                case 2:
                    System.out.print("Enter employee name: ");
                    String employeeName = scanner.nextLine();
                    System.out.print("Enter employee position: ");
                    String position = scanner.nextLine();
                    System.out.print("Enter employee email: ");
                    String employeeEmail = scanner.nextLine();
                    int employeeId = employeeService.registerEmployee(employeeName, position, employeeEmail);
                    logger.info("Employee registered: name={}, position={}, email={}, id={}", employeeName, position, employeeEmail, employeeId);
                    break;
                case 3:
                    if (employeeService.loginEmployee()) { // if employee login successful
                        logger.info("Employee login successful.");
                        System.out.println("1. Show all account request: ");
                        System.out.println("2. Accept or Deny account request creation: ");
                        int selection = scanner.nextInt();
                        if (selection == 1) {
                            accountReqService.getAllAccountRequests();
                            logger.info("Displayed all account requests.");
                        } else if (selection == 2) {
                            System.out.println("All Account Requests");
                            accountReqService.getAllAccountRequests();
                            System.out.println("1. Accept an account");
                            System.out.println("2. Deny an account");
                            int selection2 = scanner.nextInt();
                            if (selection2 == 1) {
                                accountReqService.approveAccountRequest();
                                logger.info("Account request approved.");
                            } else if (selection2 == 2) {
                                accountReqService.rejectAccountRequest();
                                logger.info("Account request denied.");
                            }
                        }
                    } else {
                        logger.warn("Employee login failed.");
                    }
                    break;
                case 4:
                    accountReqService.createAccountRequest();
                    logger.info("Account request created.");
                    break;
                case 5:
                    System.out.print("Enter custID to see your pending account requests: ");
                    int custID = scanner.nextInt();
                    List<AccountRequest> accountRequestList = accountReqService.getAllCustIdAccountRequests(custID);
                    logger.info("Retrieved account requests for customer ID: {}", custID);
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
                        logger.info("Account created for approved request ID: {}", requestId);
                    } else {
                        System.out.println("Account not approved");
                        logger.warn("Account request not approved for request ID: {}", requestId);
                    }
                    break;
                case 6:
                    accountService.findAccountsByCustomerId();
                    accountService.deposit();
                    logger.info("Customer deposited money.");
                    break;
                case 7:
                    accountService.findAccountsByCustomerId();
                    accountService.withdraw();
                    logger.info("Customer withdrew money.");
                    break;
                case 8:
                    accountService.findAllAccounts();
                    transferService.postTransfer();
                    logger.info("Transfer request made.");
                    break;
                case 9:
                    accountService.findAllAccounts();
                    transferService.findTransfersByAccountId();
                    transferService.acceptTransfer();
                    logger.info("Transfer accepted.");
                    break;
                case 10:
                    transactionLogService.getAllTransactionLogs();
                    logger.info("Transaction history displayed.");
                    break;
                default:
                    logger.warn("Invalid option selected: {}", option);
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }
}


//