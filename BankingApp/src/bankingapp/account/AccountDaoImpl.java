package bankingapp.account;

import bankingapp.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    Connection connection = ConnectionFactory.getConnection();

    @Override
    public void createAccount(Account account) {
        try {
            String sql = "insert into account (customer_id, balance) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getCustId());
            preparedStatement.setDouble(2, account.getBalance());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("account created");
            } else {
                System.out.println("Opps! something went wrong, please try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deposit(int accountId, double amount) {
        try {
            String sql = "UPDATE Account SET balance = balance + ? WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountId);

            int count = preparedStatement.executeUpdate();

            if (count > 0) {
                System.out.println("Deposit successful.");
            } else {
                System.out.println("Oops! Something went wrong, please try again.");
            }
        } catch (SQLException e) {
            System.out.println("No account");
        }
    }

    public List<Account> findAccountsByCustomerId(int customerId) {
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account WHERE customer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                double balance = resultSet.getDouble("balance");
                String status = resultSet.getString("status");

                Account account = new Account(accountId, balance, status);
                accounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println("Account not found");
        }
        return accounts;
    }
}
