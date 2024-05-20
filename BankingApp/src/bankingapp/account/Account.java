package bankingapp.account;

public class Account {
    private int accountId;
    private int custId;
    private double balance;
    private String status;

    public Account(int custId, double balance) {
        this.custId = custId;
        this.balance = balance;
        this.status = "A";
    }

    public Account(int accountId, double balance, String status) {
        this.accountId = accountId;
        this.balance = balance;
        this.status = status;
    }

    public int getAccountId() {return accountId; }
    public int getCustId() {
        return custId;
    }
    public void setCustId(int custId) {this.custId = custId;}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
