package bankingapp.transfer;

import bankingapp.ConnectionFactory;
import bankingapp.account.AccountDao;
import bankingapp.account.AccountDaoImpl;
import bankingapp.transactionlog.TransactionLog;
import bankingapp.transactionlog.TransactionLogDao;
import bankingapp.transactionlog.TransactionLogDaoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferDaoImpl implements TransferDao {

    Connection connection = ConnectionFactory.getConnection();
    AccountDao accountDao = new AccountDaoImpl(); // to use deposit and withdraw methods

    private TransactionLogDao transactionLogDao = new TransactionLogDaoImpl();


    @Override
    public void postTransfer(int fromAccountId, int toAccountId, double amount) {
        PreparedStatement checkBalanceStmt = null;
        PreparedStatement withdrawStmt = null;
        PreparedStatement insertTransferStmt = null;
        ResultSet resultSet = null;

        try {
            connection.setAutoCommit(false); // Start transaction

            // Check if the source account has sufficient balance
            String checkBalanceSql = "SELECT balance FROM Account WHERE account_id = ?";
            checkBalanceStmt = connection.prepareStatement(checkBalanceSql);
            checkBalanceStmt.setInt(1, fromAccountId);
            resultSet = checkBalanceStmt.executeQuery();

            if (resultSet.next()) {
                double currentBalance = resultSet.getDouble("balance");
                if (currentBalance >= amount) {
                    // Withdraw from source account
                    String withdrawSql = "UPDATE Account SET balance = balance - ? WHERE account_id = ?";
                    withdrawStmt = connection.prepareStatement(withdrawSql);
                    withdrawStmt.setDouble(1, amount);
                    withdrawStmt.setInt(2, fromAccountId);
                    withdrawStmt.executeUpdate();

                    // Insert a pending transfer record
                    String insertTransferSql = "INSERT INTO Transfer (from_account_id, to_account_id, amount, status) VALUES (?, ?, ?, 'PENDING')";
                    insertTransferStmt = connection.prepareStatement(insertTransferSql);
                    insertTransferStmt.setInt(1, fromAccountId);
                    insertTransferStmt.setInt(2, toAccountId);
                    insertTransferStmt.setDouble(3, amount);
                    insertTransferStmt.executeUpdate();

                    connection.commit(); // Commit transaction
                    System.out.println("Transfer posted successfully.");
                } else {
                    System.out.println("Insufficient balance in the source account.");
                    connection.rollback(); // Rollback transaction
                }
            } else {
                System.out.println("Source account not found.");
                connection.rollback(); // Rollback transaction
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction on error
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void acceptTransfer(int transferId) {
        PreparedStatement getTransferStmt = null;
        PreparedStatement updateTransferStmt = null;
        PreparedStatement withdrawStmt = null;
        PreparedStatement depositStmt = null;
        ResultSet resultSet = null;

        try {
            connection.setAutoCommit(false); // Start transaction

            // Get the transfer details
            String getTransferSql = "SELECT * FROM Transfer WHERE transfer_id = ? AND status = 'PENDING'";
            getTransferStmt = connection.prepareStatement(getTransferSql);
            getTransferStmt.setInt(1, transferId);
            resultSet = getTransferStmt.executeQuery();

            if (resultSet.next()) {
                int fromAccountId = resultSet.getInt("from_account_id");
                int toAccountId = resultSet.getInt("to_account_id");
                double amount = resultSet.getDouble("amount");

                // Withdraw the amount from the source account
                String withdrawSql = "UPDATE Account SET balance = balance - ? WHERE account_id = ?";
                withdrawStmt = connection.prepareStatement(withdrawSql);
                withdrawStmt.setDouble(1, amount);
                withdrawStmt.setInt(2, fromAccountId);
                withdrawStmt.executeUpdate();

                // Deposit the amount to the destination account
                String depositSql = "UPDATE Account SET balance = balance + ? WHERE account_id = ?";
                depositStmt = connection.prepareStatement(depositSql);
                depositStmt.setDouble(1, amount);
                depositStmt.setInt(2, toAccountId);
                depositStmt.executeUpdate();

                // Update the transfer status to 'COMPLETED'
                String updateTransferSql = "UPDATE Transfer SET status = 'COMPLETED' WHERE transfer_id = ?";
                updateTransferStmt = connection.prepareStatement(updateTransferSql);
                updateTransferStmt.setInt(1, transferId);
                updateTransferStmt.executeUpdate();

                // Log the transfer transaction
                TransactionLog transactionLog = new TransactionLog(fromAccountId, "TRANSFER_OUT", amount, new Timestamp(System.currentTimeMillis()));
                transactionLogDao.addTransactionLog(transactionLog);

                transactionLog = new TransactionLog(toAccountId, "TRANSFER_IN", amount, new Timestamp(System.currentTimeMillis()));
                transactionLogDao.addTransactionLog(transactionLog);

                connection.commit(); // Commit transaction
                System.out.println("Transfer accepted successfully.");
            } else {
                System.out.println("Pending transfer not found.");
                connection.rollback(); // Rollback transaction
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction on error
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (getTransferStmt != null) getTransferStmt.close();
                if (updateTransferStmt != null) updateTransferStmt.close();
                if (withdrawStmt != null) withdrawStmt.close();
                if (depositStmt != null) depositStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Transfer> findTransfersByAccountId(int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Transfer WHERE to_account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int transferId = resultSet.getInt("transfer_id");
                int fromAccountId = resultSet.getInt("from_account_id");
                int toAccountId = resultSet.getInt("to_account_id");
                double amount = resultSet.getDouble("amount");
                String status = resultSet.getString("status");
                Timestamp createdAt = resultSet.getTimestamp("created_at");

                Transfer transfer = new Transfer(transferId, fromAccountId, toAccountId, amount, status, createdAt);
                transfers.add(transfer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transfers;
    }
}
