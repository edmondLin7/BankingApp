package bankingapp.transfer;

import java.util.List;
import java.util.Scanner;

public class TransferServiceImpl {

    private TransferDaoImpl transferDao = new TransferDaoImpl();
    Scanner scanner = new Scanner(System.in);

    public void postTransfer() {

        System.out.println("Enter the source account ID:");
        int fromAccountId = scanner.nextInt();

        System.out.println("Enter the destination account ID:");
        int toAccountId = scanner.nextInt();

        System.out.println("Enter the amount to transfer:");
        double amount = scanner.nextDouble();
        transferDao.postTransfer(fromAccountId, toAccountId, amount);
    }

    public void findTransfersByAccountId() {
        System.out.println("Enter the source account ID:");
        int accountId = scanner.nextInt();

        List<Transfer> transfers = transferDao.findTransfersByAccountId(accountId);
        for (Transfer transfer : transfers) {
            System.out.println(transfer);
        }
    }

    public void acceptTransfer() {
        System.out.println("Enter the transfer ID:");
        int transferId = scanner.nextInt();
        transferDao.acceptTransfer(transferId);
    }
}