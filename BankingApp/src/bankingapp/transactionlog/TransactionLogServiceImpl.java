package bankingapp.transactionlog;

import java.util.List;

public class TransactionLogServiceImpl {

    private TransactionLogDao transactionLogDao = new TransactionLogDaoImpl();


    public void getAllTransactionLogs() {
        List<TransactionLog> transactionLogs = transactionLogDao.getAllTransactionLogs();
        // Print out all transaction logs
        for (TransactionLog log : transactionLogs) {
            System.out.println(log.toString());
        }

    }
}
