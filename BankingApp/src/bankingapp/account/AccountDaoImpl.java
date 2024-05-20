package bankingapp.account;

import bankingapp.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDaoImpl implements AccountDao {

    Connection connection = ConnectionFactory.getConnection();

    @Override
    public void createAccount(Account account) {
        try {
            String sql = "insert into account (name, balance) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getAccountHolderName());
            preparedStatement.setDouble(2, account.getBalance());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("account saved");
            } else {
                System.out.println("Opps! something went wrong, please try again");
            }
        } catch (SQLException e) {
            System.out.println("Account is already made with email");
        }
    }

}
