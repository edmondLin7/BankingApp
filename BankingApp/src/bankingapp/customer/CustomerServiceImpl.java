package bankingapp.customer;

import java.sql.SQLException;

public class CustomerServiceImpl {
    private CustomerDao customerDao = new CustomerDaoImpl();

    public void registerCustomer(String name, String email) {
        Customer customer = new Customer(name, email);
        customerDao.addCustomer(customer);
    }
}
