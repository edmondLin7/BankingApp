package bankingapp.transactionlog;

import java.sql.Timestamp;

public class TransactionLog {
    private int logId;
    private int accountId;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;

    // Constructors
    public TransactionLog() {
    }

    public TransactionLog(int logId, int accountId, String transactionType, double amount, Timestamp transactionDate) {
        this.logId = logId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public TransactionLog(int accountId, String transactionType, double amount, Timestamp timestamp) {
        this.logId = logId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = timestamp;
    }
    @Override
    public String toString() {
        return "TransactionLog{" +
                "logId=" + logId +
                ", accountId=" + accountId +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
    // Getters
    public int getLogId() {
        return logId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    // Setters
    public void setLogId(int logId) {
        this.logId = logId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
}
