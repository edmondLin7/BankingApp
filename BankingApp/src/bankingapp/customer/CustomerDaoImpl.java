package bankingapp.customer;

import bankingapp.ConnectionFactory;

import java.sql.*;

public class CustomerDaoImpl implements CustomerDao {
    Connection connection = ConnectionFactory.getConnection();


    public int addCustomer(Customer customer)  {
        try {
            String sql = "insert into customer (name, email, pin) values (?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPin());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("Your customer Id is : " + generatedKeys.getInt(1));
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // return -1 if the insertion fails
    }
}
