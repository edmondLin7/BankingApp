package bankingapp.transactionlog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bankingapp.ConnectionFactory;


public class TransactionLogDaoImpl implements TransactionLogDao {
    // Assume connection initialization and other necessary setup
    Connection connection = ConnectionFactory.getConnection();

    @Override
    public List<TransactionLog> getAllTransactionLogs() {
        List<TransactionLog> transactionLogs = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM TransactionLog")) {

            while (resultSet.next()) {
                int logId = resultSet.getInt("log_id");
                int accountId = resultSet.getInt("account_id");
                String transactionType = resultSet.getString("transaction_type");
                double amount = resultSet.getDouble("amount");
                Timestamp transactionDate = resultSet.getTimestamp("transaction_date");

                TransactionLog transactionLog = new TransactionLog(logId, accountId, transactionType, amount, transactionDate);
                transactionLogs.add(transactionLog);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }

        return transactionLogs;
    }

    public void addTransactionLog(TransactionLog transactionLog) {
        try {
            String sql = "INSERT INTO TransactionLog (account_id, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transactionLog.getAccountId());
            preparedStatement.setString(2, transactionLog.getTransactionType());
            preparedStatement.setDouble(3, transactionLog.getAmount());
            preparedStatement.setTimestamp(4, transactionLog.getTransactionDate());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Transaction log added successfully.");
            } else {
                System.out.println("Failed to add transaction log.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding transaction log.");
            e.printStackTrace();
        }
    }
}
