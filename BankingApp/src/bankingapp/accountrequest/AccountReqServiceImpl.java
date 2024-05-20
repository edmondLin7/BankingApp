package bankingapp.accountrequest;

import java.util.List;
import java.util.Scanner;

public class AccountReqServiceImpl {
    private AccountRequestDao accountRequestDao = new AccountReqDaoImpl();
    private Scanner scanner = new Scanner(System.in);

    public void createAccountRequest() {
        System.out.print("Enter Cust ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        AccountRequest accountRequest = new AccountRequest(customerId, "P");
        accountRequestDao.createAccountRequest(accountRequest);
    }

    public void getAllAccountRequests() {
        List<AccountRequest> accounts = accountRequestDao.getAllAccountRequests();
        for (AccountRequest acc: accounts) {
            System.out.println("Account Id: " + acc.getRequestId() +
                        " | CustId: " + acc.getCustomerId() +
                        " | Status: " + acc.getStatus()
                    );
        }
    }

    public void approveAccountRequest() {
        System.out.print("Enter request ID to approve: ");
        int approveRequestId = scanner.nextInt();
        accountRequestDao.updateAccountRequestStatus(approveRequestId, "A");
    }

    public void rejectAccountRequest() {
        System.out.print("Enter request ID to reject: ");
        int rejectRequestId = scanner.nextInt();
        accountRequestDao.updateAccountRequestStatus(rejectRequestId, "R");
    }

    public void getAllCustIdAccountRequests(int CustId) {

        List<AccountRequest> accounts = accountRequestDao.getAccountRequestsByCustomerId(CustId);
        for (AccountRequest acc: accounts) {
            System.out.println("Account Id: " + acc.getRequestId() +
                    " | CustId: " + acc.getCustomerId() +
                    " | Status: " + acc.getStatus()
            );
        }
    }
}
