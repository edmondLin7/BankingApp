package bankingapp.transfer;

import java.util.List;

public interface TransferDao {
    void postTransfer(int fromAccountId, int toAccountId, double amount);
    List<Transfer> findTransfersByAccountId(int accountId);
    void acceptTransfer(int transferId);

}