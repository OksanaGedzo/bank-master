package ee.bcs.bank.restbank;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    public static final String ATM = "ATM";
    public static final char NEW_ACCOUNT = 'n';
    public static final char DEPOSIT = 'd';
    public static final char WITHDRAWAL = 'w';
    public static final char SEND_MONEY = 's';
    public static final char RECEIVE_MONEY = 'r';

    @Resource
    private AccountService accountService;

    @Resource
    private BalanceService balanceService;

    public TransactionDto createExampleTransaction() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountId(123);
        transactionDto.setBalance(1000);
        transactionDto.setAmount(100);
        transactionDto.setTransactionType(SEND_MONEY);
        transactionDto.setReceiverAccountNumber("EE123");
        transactionDto.setSenderAccountNumber("EE456");
        transactionDto.setLocalDateTime(LocalDateTime.now());
        return transactionDto;
    }

    public RequestResult addNewTransaction(Bank bank, TransactionDto transaction) {
        RequestResult result = new RequestResult();
        List<AccountDto> accounts = bank.getAccounts();
        int accountId = transaction.getAccountId();
        if (!accountService.accountIdExist(accounts, accountId)) {
            result.setAccountId(accountId);
            result.setError("Account ID " + accountId + " does not exist!!!");
            return result;
        }

        Character transactionType = transaction.getTransactionType();
        Integer amount = transaction.getAmount();

        int transactionId = bank.getTransactionIdCounter();
        AccountDto account = accountService.getAccountById(accounts, accountId);

        Integer balance = account.getBalance();
        int newBalance;
        String receiverAccountNumber;
        switch (transactionType) {

            case NEW_ACCOUNT:
                transaction.setId(transactionId);
                transaction.setSenderAccountNumber(null);
                transaction.setReceiverAccountNumber(null);
                transaction.setBalance(0);
                transaction.setAmount(0);
                transaction.setLocalDateTime(LocalDateTime.now());
                bank.addTransactionToTransactions(transaction);
                bank.incrementTransactionId();
                result.setTransactionId(transactionId);
                result.setAccountId(accountId);
                result.setMessage("Successfully added 'new account' transaction");
                return result;


            case DEPOSIT:
                newBalance = balance + amount;
                transaction.setId(transactionId);
                transaction.setSenderAccountNumber(ATM);
                transaction.setReceiverAccountNumber(account.getAccountNumber());
                transaction.setBalance(newBalance);
                transaction.setLocalDateTime(LocalDateTime.now());
                bank.addTransactionToTransactions(transaction);
                bank.incrementTransactionId();
                account.setBalance(newBalance);
                result.setTransactionId(transactionId);
                result.setAccountId(accountId);
                result.setMessage("Successfully made deposit transaction");
                return result;

            case WITHDRAWAL:
                if (!balanceService.enoughMoneyOnAccount(balance, amount)) {
                    result.setAccountId(accountId);
                    result.setError("Not enough money: " + amount);
                    return result;
                }
                newBalance = balance - amount;

                transaction.setId(transactionId);
                transaction.setSenderAccountNumber(account.getAccountNumber());
                transaction.setReceiverAccountNumber(ATM);
                transaction.setBalance(newBalance);
                transaction.setLocalDateTime(LocalDateTime.now());

                bank.addTransactionToTransactions(transaction);
                bank.incrementTransactionId();

                account.setBalance(newBalance);

                result.setTransactionId(transactionId);
                result.setAccountId(accountId);
                result.setMessage("Successfully made withdrawal transaction");
                return result;

            case SEND_MONEY:
                if (!balanceService.enoughMoneyOnAccount(balance, amount)) {

                    result.setAccountId(accountId);
                    result.setError("Not enough money: " + amount);
                    return result;
                }

                newBalance = balance - amount;

                transaction.setId(transactionId);
                transaction.setSenderAccountNumber(account.getAccountNumber());
                transaction.setBalance(newBalance);
                transaction.setLocalDateTime(LocalDateTime.now());

                bank.addTransactionToTransactions(transaction);
                bank.incrementTransactionId();

                account.setBalance(newBalance);

                result.setTransactionId(transactionId);
                result.setAccountId(accountId);
                result.setMessage("Successfully sent money");

                receiverAccountNumber = transaction.getReceiverAccountNumber();
                if (accountService.accountNumberExist(accounts, receiverAccountNumber)) {
                    AccountDto receiverAccount = accountService.getAccountByNumber(accounts, receiverAccountNumber);

                    int receiverAccountId = receiverAccount.getId();
                    Integer receiverCurrentBalance = receiverAccount.getBalance();

                    int receiverNewBalance = receiverCurrentBalance + amount;
                    TransactionDto receiverTransaction = new TransactionDto();

                    receiverTransaction.setId(bank.getTransactionIdCounter());
                    receiverTransaction.setAccountId(receiverAccountId);
                    receiverTransaction.setSenderAccountNumber(account.getAccountNumber());
                    receiverTransaction.setReceiverAccountNumber(receiverAccountNumber);
                    receiverTransaction.setBalance(receiverNewBalance);
                    receiverTransaction.setLocalDateTime(LocalDateTime.now());
                    receiverTransaction.setAmount(amount);
                    receiverTransaction.setTransactionType(RECEIVE_MONEY);

                    bank.addTransactionToTransactions(receiverTransaction);
                    bank.incrementTransactionId();

                    receiverAccount.setBalance(receiverNewBalance);
                }

                return result;

            default:
                result.setError("Unknown transaction type: " + transactionType);
                return result;

        }

    }

    public RequestResult receiveNewTransaction(Bank bank, TransactionDto transaction) {
        RequestResult result = new RequestResult();
        String receiverAccountNumber = transaction.getReceiverAccountNumber();
        List<AccountDto> accounts = bank.getAccounts();

        if (!accountService.accountNumberExist(accounts, receiverAccountNumber)) {
            result.setError("No such account in our bank: " + receiverAccountNumber);
            return result;
        }

        AccountDto receiverAccount = accountService.getAccountByNumber(accounts, receiverAccountNumber);

        int transactionId = bank.getTransactionIdCounter();
        Integer receiverCurrentBalance = receiverAccount.getBalance();
        Integer amount = transaction.getAmount();

        int receiverNewBalance = receiverCurrentBalance + amount;
        int receiverAccountId = receiverAccount.getId();

        transaction.setTransactionType(RECEIVE_MONEY);
        transaction.setBalance(receiverNewBalance);
        transaction.setId(transactionId);
        transaction.setAccountId(receiverAccountId);
        transaction.setLocalDateTime(LocalDateTime.now());

        bank.addTransactionToTransactions(transaction);
        bank.incrementTransactionId();


        receiverAccount.setBalance(receiverNewBalance);

        result.setTransactionId(transactionId);
        result.setMessage("Transaction received");
        return result;
    }

}
