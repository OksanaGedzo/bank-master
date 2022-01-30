package ee.bcs.bank.restbank;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Bank {
    private List<AccountDto> accounts = new ArrayList<>();
    private int accountIdCounter = 1;
    private List<TransactionDto> transactions = new ArrayList<>();
    private int transactionIdCounter = 1;

    public void addAccountToAccounts(AccountDto account) {
        accounts.add(account);
    }

    public void incrementAccountIdCounter() {
        accountIdCounter++;
    }

    public void addTransactionToTransactions(TransactionDto transaction) {
        transactions.add(transaction);
    }

    public void incrementTransactionId() {
        transactionIdCounter++;
    }

}
