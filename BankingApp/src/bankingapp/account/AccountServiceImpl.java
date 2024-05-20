package bankingapp.account;

import bankingapp.accountrequest.AccountRequest;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Scanner;

public class AccountServiceImpl {
    private AccountDao accountDao = new AccountDaoImpl();
    private Scanner scanner = new Scanner(System.in);

    public void createAccount() {
        System.out.print("Enter custId ");
        int custId = scanner.nextInt();
        System.out.print("Enter starting balance ");
        double balance = scanner.nextDouble();
        accountDao.createAccount(new Account(custId, balance, "A"));
    }

    public List<Account> findAccountsByCustomerId() {
        System.out.print("Enter custId ");
        int custId = scanner.nextInt();

        List<Account> accounts = accountDao.findAccountsByCustomerId(custId);
        for (Account acc : accounts) {
            System.out.println("Account Id: " + acc.getAccountId() +
                    " | Balance: " + acc.getBalance() +
                    " | Status: " + acc.getStatus()
            );
        }
        return accounts;
    }

    public void deposit() {
        System.out.print("Enter accountId ");
        int accId = scanner.nextInt();
        System.out.print("Enter ammount to add ");
        int balance = scanner.nextInt();
        accountDao.deposit(accId, balance);
    }
}
