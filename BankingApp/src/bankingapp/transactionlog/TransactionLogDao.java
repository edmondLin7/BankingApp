package bankingapp.transactionlog;

import java.util.List;

public interface TransactionLogDao {
    List<TransactionLog> getAllTransactionLogs();
    void addTransactionLog(TransactionLog transactionLog);
}
