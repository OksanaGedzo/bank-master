package ee.bcs.bank.restbank;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    public AccountDto createExampleAccount() {
        AccountDto account = new AccountDto();
        account.setAccountNumber(createRandomAccountNumber());
        account.setFirstName("Juss");
        account.setLastName("Sepp");
        account.setBalance(0);
        account.setLocked(false);
        return account;
    }

    private String createRandomAccountNumber() {
        Random random = new Random();
        return "EE" + (random.nextInt(9999) + 1000);
    }

    public boolean accountIdExist(List<AccountDto> accounts, int accountId) {
        for (AccountDto account : accounts) {
            if (account.getId() == accountId) {
                return true;
            }
        }
        return false;
    }

    public AccountDto getAccountById(List<AccountDto> accounts, int accountId) {
        for (AccountDto account : accounts) {
            if (account.getId() == accountId) {
                return account;
            }
        }
        return null;
    }

    public boolean accountNumberExist(List<AccountDto> accounts, String receiverAccountNumber) {
        for (AccountDto account : accounts) {
            if (account.getAccountNumber().equals(receiverAccountNumber)) {
                return true;
            }
        }

        return false;
    }

    public AccountDto getAccountByNumber(List<AccountDto> accounts, String receiverAccountNumber) {
        for (AccountDto account : accounts) {
            if (account.getAccountNumber().equals(receiverAccountNumber)) {
                return account;
            }
        }
        return null;
    }

    public RequestResult updateOwnerDetails(List<AccountDto> accounts, AccountDto incomingUpdatedAccount) {
        RequestResult result = new RequestResult();
        int accountId = incomingUpdatedAccount.getId();
        if (!accountIdExist(accounts, accountId)) {
            result.setError("Account ID: " + accountId + " does not exist!");
            result.setAccountId(accountId);
            return result;
        }
        AccountDto account = getAccountById(accounts, accountId);
        String firstNameFromUpdatedAccount = incomingUpdatedAccount.getFirstName();

        String lastNameFromUpdatedAccount = incomingUpdatedAccount.getLastName();
        account.setFirstName(firstNameFromUpdatedAccount);
        account.setLastName(lastNameFromUpdatedAccount);

        result.setAccountId(accountId);
        result.setMessage("Successfully updated account.");

        return result;
    }

    public RequestResult deleteAccount(List<AccountDto> accounts, int accountId) {

        RequestResult result = new RequestResult();

        if (!accountIdExist(accounts, accountId)) {
            result.setError("Account ID: " + accountId + " does not exist!");
            result.setAccountId(accountId);
            return result;
        }

        AccountDto account = getAccountById(accounts, accountId);
        accounts.remove(account);

        result.setMessage("Account deleted");
        result.setAccountId(accountId);
        return result;
    }

    public RequestResult switchLockStatus(List<AccountDto> accounts, int accountId) {
        RequestResult result = new RequestResult();

        if (!accountIdExist(accounts, accountId)) {
            result.setError("Account ID: " + accountId + " does not exist!");
            result.setAccountId(accountId);
            return result;
        }

        AccountDto account = getAccountById(accounts, accountId);
        Boolean lockedStatus = account.getLocked();

        if (lockedStatus) {
            account.setLocked(false);
            result.setMessage("Account is now unlocked!");
        } else {
            account.setLocked(true);
            result.setMessage("Account is now locked!");
        }

        result.setAccountId(accountId);
        return result;
    }
}
