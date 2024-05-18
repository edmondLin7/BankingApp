package bankingapp.accountrequest;

import java.sql.SQLException;
import java.util.List;

public interface AccountRequestDao {
    void createAccountRequest(AccountRequest accountRequest);
    List<AccountRequest> getAllAccountRequests() throws SQLException;
    void updateAccountRequestStatus(int requestId, String newStatus) throws SQLException;

}
