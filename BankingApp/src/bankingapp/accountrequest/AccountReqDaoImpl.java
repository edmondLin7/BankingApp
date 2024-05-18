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
                System.out.println("customer saved");
            } else {
                System.out.println("Opps! something went wrong, please try again");
            }
        } catch (SQLException e) {
            System.out.println("Account is already made with email");
        }
    }

    @Override
    public List<AccountRequest> getAllAccountRequests() throws SQLException {
        List<AccountRequest> accountRequests = new ArrayList<>();

        String sql = "select * from accountrequest";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int requestId = resultSet.getInt("request_id");
            int customerId = resultSet.getInt("customer_id");
            String status = resultSet.getString("status");
            accountRequests.add(new AccountRequest(requestId, customerId, status));
        }

        return accountRequests;
    }


    @Override
    public void updateAccountRequestStatus(int requestId, String newStatus) throws SQLException {
        String sql = "UPDATE AccountRequest SET status = ? WHERE request_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, requestId);
        preparedStatement.setString(2, newStatus);
        preparedStatement.executeUpdate();
    }
}
