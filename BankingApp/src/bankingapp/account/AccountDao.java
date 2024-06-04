package bankingapp.account;

import java.util.List;

public interface AccountDao {
    void createAccount(Account account);

    void deposit(int accountId, double amount);

    void withdraw(int accountId, double amount);

    List<Account> findAccountsByCustomerId(int customerId);
    List<Account> findAllAccounts();
}
