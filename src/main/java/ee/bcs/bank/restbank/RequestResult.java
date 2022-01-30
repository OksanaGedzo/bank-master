package ee.bcs.bank.restbank;

import lombok.Getter;
import lombok.Setter;

// Lombok'i annotatsioonid, mis loovad koodi kompileerimise hetkel vajalikud getterid ja setterid
@Setter
@Getter
public class RequestResult {
    private int accountId;
    private int transactionId;
    private String message;
    private String error;
}
