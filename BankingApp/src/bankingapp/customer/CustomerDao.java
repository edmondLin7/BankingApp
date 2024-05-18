package bankingapp.customer;

import java.sql.SQLException;

public interface CustomerDao {
    void addCustomer(Customer customer) throws SQLException;
}
