package bankingapp.customer;

import bankingapp.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {
    Connection connection = ConnectionFactory.getConnection();


    public void addCustomer(Customer customer)  {
        try {
            String sql = "insert into customer (name, email) values (?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
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
}
