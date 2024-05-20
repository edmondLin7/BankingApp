package bankingapp.employee;

import bankingapp.ConnectionFactory;

import java.sql.*;

public class EmployeeDaoImpl implements EmployeeDao{
    Connection connection = ConnectionFactory.getConnection();

    @Override
    public int addEmployee(Employee employee) {
        try {
            String sql = "insert into employee (name, position, email) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPosition());
            preparedStatement.setString(3, employee.getEmail());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("Your accountID is " +  generatedKeys.getInt(1));
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // return -1 if the insertion fails
    }

    @Override
    public boolean loginEmployee(int employeeID) {
        try {
            String sql = "SELECT 1 FROM Employee WHERE employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful");
                return true;
            } else {
                System.out.println("Login unsuccessful");
            }
        } catch (SQLException e) {
            System.out.println("Unable to find user");
        }
        return false;
    }
}
