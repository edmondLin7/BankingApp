package bankingapp.account;

import bankingapp.ConnectionFactory;
import bankingapp.transactionlog.TransactionLog;
import bankingapp.transactionlog.TransactionLogDao;
import bankingapp.transactionlog.TransactionLogDaoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    Connection connection = ConnectionFactory.getConnection();
    private TransactionLogDao transactionLogDao = new TransactionLogDaoImpl();

    @Override
    public void createAccount(Account account) {
        try {
            String sql = "insert into account (customer_id, balance, status) values (?, ?, ?)";
            System.out.println(account.getCustId());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getCustId());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setString(3, account.getStatus());
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

                // Log the deposit transaction
                TransactionLog transactionLog = new TransactionLog(accountId, "DEPOSIT", amount, new Timestamp(System.currentTimeMillis()));
                transactionLogDao.addTransactionLog(transactionLog);
            } else {
                System.out.println("Oops! Something went wrong, please try again.");
            }
        } catch (SQLException e) {
            System.out.println("No account");
        }
    }

    public void withdraw(int accountId, double amount) {
        try {
            // First, check if there is enough balance
            String checkBalanceSql = "SELECT balance FROM Account WHERE account_id = ?";
            PreparedStatement checkBalanceStmt = connection.prepareStatement(checkBalanceSql);
            checkBalanceStmt.setInt(1, accountId);
            ResultSet resultSet = checkBalanceStmt.executeQuery();

            if (resultSet.next()) {
                double currentBalance = resultSet.getDouble("balance");
                if (currentBalance >= amount) {
                    // Enough balance, proceed with withdrawal
                    String sql = "UPDATE Account SET balance = balance - ? WHERE account_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setDouble(1, amount);
                    preparedStatement.setInt(2, accountId);

                    int count = preparedStatement.executeUpdate();

                    if (count > 0) {
                        System.out.println("Withdrawal successful.");

                        // Log the withdrawal transaction with type and amount
                        TransactionLog transactionLog = new TransactionLog(accountId, "WITHDRAW", amount, new Timestamp(System.currentTimeMillis()));
                        transactionLogDao.addTransactionLog(transactionLog);
                    } else {
                        System.out.println("Oops! Something went wrong, please try again.");
                    }
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Oops! Something went wrong with the database operation.");
            e.printStackTrace();
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

    public void transfer(int sourceAccountId, int destinationAccountId, double amount) {
        Connection connection = null;
        PreparedStatement checkBalanceStmt = null;
        PreparedStatement withdrawStmt = null;
        PreparedStatement depositStmt = null;

        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Check if source account has enough balance
            String checkBalanceSql = "SELECT balance FROM Account WHERE account_id = ?";
            checkBalanceStmt = connection.prepareStatement(checkBalanceSql);
            checkBalanceStmt.setInt(1, sourceAccountId);
            ResultSet resultSet = checkBalanceStmt.executeQuery();

            if (resultSet.next()) {
                double currentBalance = resultSet.getDouble("balance");
                if (currentBalance >= amount) {
                    // Enough balance, proceed with withdrawal
                    String withdrawSql = "UPDATE Account SET balance = balance - ? WHERE account_id = ?";
                    withdrawStmt = connection.prepareStatement(withdrawSql);
                    withdrawStmt.setDouble(1, amount);
                    withdrawStmt.setInt(2, sourceAccountId);
                    withdrawStmt.executeUpdate();

                    // Deposit to destination account
                    String depositSql = "UPDATE Account SET balance = balance + ? WHERE account_id = ?";
                    depositStmt = connection.prepareStatement(depositSql);
                    depositStmt.setDouble(1, amount);
                    depositStmt.setInt(2, destinationAccountId);
                    depositStmt.executeUpdate();

                    connection.commit(); // Commit transaction
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Insufficient balance in source account.");
                }
            } else {
                System.out.println("Source account not found.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Oops! Something went wrong with the database operation.");
            e.printStackTrace();
        } finally {
            try {
                if (checkBalanceStmt != null) checkBalanceStmt.close();
                if (withdrawStmt != null) withdrawStmt.close();
                if (depositStmt != null) depositStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {

            }
        }
    }

    @Override
    public List<Account> findAllAccounts() {
        List<Account> allAccounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                int customerId = resultSet.getInt("customer_id");
                double balance = resultSet.getDouble("balance");
                String status = resultSet.getString("status");

                Account account = new Account(accountId, customerId, balance, status);
                allAccounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println("Accounts not found.");
        }
        return allAccounts;
    }
}
