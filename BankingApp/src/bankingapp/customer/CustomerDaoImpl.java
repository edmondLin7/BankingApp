package bankingapp.customer;

import bankingapp.ConnectionFactory;

import java.sql.*;

public class CustomerDaoImpl implements CustomerDao {
    Connection connection = ConnectionFactory.getConnection();
    public int addCustomer(Customer customer) {
        try {
            String sql = "{CALL add_customer(?, ?, ?)}";
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, customer.getName());
            callableStatement.setString(2, customer.getEmail());
            callableStatement.registerOutParameter(3, Types.INTEGER);

            callableStatement.executeUpdate();

            int newCustomerId = callableStatement.getInt(3);
            System.out.println("Your customer Id is : " + newCustomerId);
            return newCustomerId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // return -1 if the insertion fails
    }
}
