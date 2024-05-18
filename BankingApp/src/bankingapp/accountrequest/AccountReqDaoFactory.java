package bankingapp.accountrequest;

import bankingapp.customer.CustomerDao;
import bankingapp.customer.CustomerDaoImpl;

public class AccountReqDaoFactory {
    private static AccountRequestDao accountReqDao;

    public static AccountRequestDao getAccountRequestDao() {
        if (accountReqDao == null) {
            accountReqDao = new AccountReqDaoImpl();
        }

        return accountReqDao;
    }
}
