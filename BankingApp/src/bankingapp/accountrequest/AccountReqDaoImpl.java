package bankingapp.accountrequest;

import bankingapp.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountReqDaoImpl implements AccountRequestDao{
    Connection connection = ConnectionFactory.getConnection();

    @Override
    public void createAccountRequest(AccountRequest accountRequest) {
        try {
            String sql = "insert into accountrequest (customer_id, status) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountRequest.getCustomerId());
            preparedStatement.setString(2, accountRequest.getStatus());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("request created saved");
            } else {
                System.out.println("Opps! something went wrong, please try again");
            }
        } catch (SQLException e) {
            System.out.println("Error has occurred, try using a valid customerID");
        }
    }

    @Override
    public List<AccountRequest> getAllAccountRequests() {
        List<AccountRequest> accountRequests = new ArrayList<>();
        try {
            String sql = "select * from accountrequest";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int requestId = resultSet.getInt("request_id");
                int customerId = resultSet.getInt("customer_id");
                String status = resultSet.getString("status");
                accountRequests.add(new AccountRequest(requestId, customerId, status));
            }
        } catch (SQLException e) {
            System.out.println("Failed to get all accounts");
        }
        return accountRequests;
    }

    @Override
    public void updateAccountRequestStatus(int requestId, String newStatus)  {
        try {
            String sql = "UPDATE AccountRequest SET status = ? WHERE request_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, requestId);
            preparedStatement.executeUpdate();
            if(newStatus.equals("A")) {
                System.out.println("Account created");
            } else if (newStatus.equals("D")) {
                System.out.println("Account denied");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update status");
        }
    }

    @Override
    public List<AccountRequest> getAccountRequestsByCustomerId(int customerId) {
        List<AccountRequest> accountRequests = new ArrayList<>();

        try {
            String sql = "SELECT * FROM AccountRequest WHERE customer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int requestId = resultSet.getInt("request_id");
                int custId = resultSet.getInt("customer_id");
                String status = resultSet.getString("status");
                accountRequests.add(new AccountRequest(requestId, custId, status));
            }
        } catch (SQLException e) {
            System.out.println("Failed to update status");
        }
        return accountRequests;
    }

    @Override
    public void deleteAccountRequest(int requestId) {
        try {
            String sql = "DELETE FROM AccountRequest WHERE request_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, requestId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to delete");
        }
    }


}
