package bankingapp.accountrequest;

public class AccountRequest {

    private int requestId;
    private int customerId;
    private String status;

    // Parameterized constructor
    public AccountRequest(int requestId, int customerId, String status) {
        this.requestId = requestId;
        this.customerId = customerId;
        this.status = status;
    }

    public AccountRequest(int customerId, String status) {
        this.customerId = customerId;
        this.status = status;
    }

    public int getRequestId() { return requestId; }
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method
    @Override
    public String toString() {
        return "AccountRequest{" +
                " customerId=" + customerId +
                ", status='" + status + '\'' +
                '}';
    }
}
