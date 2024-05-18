package bankingapp.account;

import bankingapp.accountrequest.AccountRequestDao;

public class AccountDaoFactory {
    private static AccountDao accountDao;

    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDaoImpl();
        }

        return accountDao;
    }
}
