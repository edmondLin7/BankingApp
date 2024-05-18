package bankingapp.employee;

import bankingapp.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao{
    Connection connection = ConnectionFactory.getConnection();

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        try {
            String sql = "insert into employee (name, position, email) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getPosition());
            preparedStatement.setString(3, employee.getEmail());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("employee saved");
            } else {
                System.out.println("Opps! something went wrong, please try again");
            }
        } catch (SQLException e) {
            System.out.println("Account is already made with email");
        }
    }
}
