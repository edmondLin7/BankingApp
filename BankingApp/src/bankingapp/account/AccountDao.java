package bankingapp.account;

import java.util.List;

public interface AccountDao {
    void createAccount(Account account);

    void deposit(int accountId, double amount);

    public List<Account> findAccountsByCustomerId(int customerId);
}
