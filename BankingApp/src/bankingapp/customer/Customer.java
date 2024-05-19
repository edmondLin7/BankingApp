package bankingapp.customer;
public class Customer {

    private String name;
    private String email;
    private String pin;

    // Constructors
    public Customer() {
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.pin = pin;
    }

    public String getPin() { return pin; }

    public void setPin(String pin) { this.pin = pin; }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString method
    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
