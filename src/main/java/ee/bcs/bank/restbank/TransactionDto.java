package ee.bcs.bank.restbank;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// Lombok'i annotatsioonid, mis loovad koodi kompileerimise hetkel vajalikud getterid ja setterid
@Getter
@Setter
public class TransactionDto {
    // DTO - Data Transfer Object
    private int id;
    private int accountId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Integer amount;
    private Integer balance;
    private LocalDateTime localDateTime;
    private Character transactionType;
}
