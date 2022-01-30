package ee.bcs.bank.restbank;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// Lombok'i annotatsioonid, mis loovad koodi kompileerimise hetkel vajalikud getterid ja setterid
@Setter
@Getter
public class Bank {

    // List<AccountDto> tüüpi klassi field/väli, kus sees saame hoida mitmeid AccountDto tüüpi objekte
    // new ArrayList<>() osaga luuakse seda nüüpi objekt. Enne seda tegevust on accounts väärtus lihtsalt 'null'
    // Kui 'accounts' oleks lihtsalt null, siis me ei saaks kutsuda välja tema meetodeid nagu näiteks add(), remove(), jne
    // Kui List'i teema on veel endiselt segane, siis palun vaata uuesti:
    // https://youtu.be/0JsSD7tZC54
    private List<AccountDto> accounts = new ArrayList<>();

    // int tüüpi klassi field/väli, kus sees saame hoida account ID'de loenduri väärtust
    private int accountIdCounter = 1;

    // List<TransactionDto> tüüpi klassi field/väli, kus sees saame hoida mitmeid TransactionDto tüüpi objekte
    // Kuid List'i teema on veel endiselt segane, siis palun vaata uuesti:
    // https://youtu.be/0JsSD7tZC54
    private List<TransactionDto> transactions = new ArrayList<>();

    // int tüüpi klassi field/väli, kus sees saame hoida transaction ID'de loenduri väärtust
    private int transactionIdCounter = 1;


    // See meetod on defineeritud nii, et ta võtab sisse AccountDto tüüpi objekti
    // See on selleks vajalik, et me saaksime siin meetodis selle objektiga kuidagi toimetada.
    // See meetod on 'void'... Siin meetodi sees tehakse midagi aga ta ei tagasta mingit objekti
    // Seega selle meetodi väljakutsumisel ei saa me näiteks mingisse muutujasse midagi tagastada ja väärtustada
    // sest siin meetodis ei ole ju kusagil "return mingiObjekt"
    public void addAccountToAccounts(AccountDto account) {
        // Võtame addAccountToAccounts() signatuuri parameetris sisse AccountDto tüüpi objekti
        // Siin signatuuris antakse sellele objektile nimeks 'account'

        // Listi 'accounts' lisatakse (add) juurde objekt 'account'
        // See List<AccountDto> tüüpi objekt on meil siin klassi alguses ära defineeritud
        // Kui List'i teema on veel endiselt segane, siis palun vaata uuesti:
        // https://youtu.be/0JsSD7tZC54
        accounts.add(account);
    }


    // See meetod on defineeritud nii, et ta ei võta sisse mingeid parameetreid
    // See meetod on 'void'... Siin meetodi sees tehakse midagi aga ta ei tagasta mingit objekti
    // Seega selle meetodi väljakutsumisel ei saa me näiteks mingisse muutujasse midagi tagastada ja väärtustada
    // sest siin meetodis ei ole ju kusagil "return mingiObjekt"
    public void incrementAccountIdCounter() {

        // See int tüüpi objekt 'accountIdCounter' on meil siin klassi alguses ära defineeritud
        // Pöördume selle klassi muutuja poole ja suurendame selle väärtust ühe võrra
        // Kui incrementeerimise teema on veel endiselt segane, siis palun vaata uuesti:
        // https://youtu.be/puu56Z3erWM
        accountIdCounter++;
    }

    // See meetod on defineeritud nii, et ta võtab sisse TransactionDto tüüpi objekti
    // See on selleks vajalik, et me saaksime siin meetodis selle objektiga kuidagi toimetada.
    // See meetod on 'void'... Siin meetodi sees tehakse midagi aga ta ei tagasta mingit objekti
    // Seega selle meetodi väljakutsumisel ei saa me näiteks mingisse muutujasse midagi tagastada ja väärtustada
    // sest siin meetodis ei ole ju kusagil "return mingiObjekt"
    public void addTransactionToTransactions(TransactionDto transaction) {
        // Võtame addTransactionToTransactions() signatuuri parameetris sisse TransactionDto tüüpi objekti
        // Siin signatuuris antakse sellele objektile nimeks 'transaction'

        // Listi 'transactions' lisatakse (add) juurde objekt 'transaction'
        // See List<TransactionDto> tüüpi objekt on meil siin klassi alguses ära defineeritud
        // Kui List'i teema on veel endiselt segane, siis palun vaata uuesti:
        // https://youtu.be/0JsSD7tZC54
        transactions.add(transaction);
    }


    // See meetod on defineeritud nii, et ta ei võta sisse mingeid parameetreid
    // See meetod on 'void'... Siin meetodi sees tehakse midagi aga ta ei tagasta mingit objekti
    // Seega selle meetodi väljakutsumisel ei saa me näiteks mingisse muutujasse midagi tagastada ja väärtustada
    // sest siin meetodis ei ole ju kusagil "return mingiObjekt"
    public void incrementTransactionId() {

        // See int tüüpi objekt 'transactionIdCounter' on meil siin klassi alguses ära defineeritud
        // Pöördume selle klassi muutuja poole ja suurendame selle väärtust ühe võrra
        // Kui incrementeerimise teema on veel endiselt segane, siis palun vaata uuesti:
        // https://youtu.be/puu56Z3erWM
        transactionIdCounter++;
    }

}
