package bankingapp.transfer;



public class TransferDaoFactory {
    private static TransferDao transferDao;

    private TransferDaoFactory() {
    }

    public static TransferDao getEmployeeDao() {
        if (transferDao == null) {
            transferDao = new TransferDaoImpl();
        }

        return transferDao;
    }
}
