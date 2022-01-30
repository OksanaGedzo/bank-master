package ee.bcs.bank.restbank;

import org.springframework.stereotype.Service;


@Service
public class BankService {

    public RequestResult addAccountToBank(Bank bank, AccountDto account) {
        int accountId = bank.getAccountIdCounter();
        account.setId(accountId);
        account.setBalance(0);
        account.setLocked(false);
        bank.addAccountToAccounts(account);
        bank.incrementAccountIdCounter();
        RequestResult result = new RequestResult();
        result.setAccountId(accountId);
        result.setMessage("Added new account");
        return result;
    }

}
