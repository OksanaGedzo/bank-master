package ee.bcs.bank.restbank;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

// @Service - Springi annotatsioon, mis tekitab klassist Bean'i.
// Tänu sellele on hiljem võimalik sellele teenusele hõlpsat @Resource'ga ligi saada
// Kui seda annotatsiooni pole, siis Springil pole sellest klassist Bean'i ja ta ei tea sellest midagi
@Service
public class TransactionService {

    // Konstandid. Nende muutujad on konstantselt sama väärtusega
    // Neid ei ole võimalik enam uuesti mingu uue väärtusega väärtustada
    public static final String ATM = "ATM";
    public static final char NEW_ACCOUNT = 'n';
    public static final char DEPOSIT = 'd';
    public static final char WITHDRAWAL = 'w';
    public static final char SEND_MONEY = 's';
    public static final char RECEIVE_MONEY = 'r';

    // @Resource abiga saab Spring teha meile ligipääsu:
    // @Service, @Component, @Mapper, @Repository, jne annotatsiooniga klassidele
    @Resource
    private AccountService accountService;

    @Resource
    private BalanceService balanceService;

    // See meetod on defineeritud nii, et mingeid parameetreid (andmeid) ta sisse ei võta
    // See meetod peab tagastama TransactionDto tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public TransactionDto createExampleTransaction() {

        // Loome uue TransactionDto tüüpi objekti
        // Kui klasside ja objektide teema on veel endiselt segane, siis palun vaata uuesti "Kasutaja poolt loodavad klassid":
        // https://youtu.be/pMYcginZIjQ
        TransactionDto transactionDto = new TransactionDto();

        // lisame setteritega mingid väärtused
        transactionDto.setAccountId(123);
        transactionDto.setBalance(1000);
        transactionDto.setAmount(100);
        transactionDto.setTransactionType(SEND_MONEY);
        transactionDto.setReceiverAccountNumber("EE123");
        transactionDto.setSenderAccountNumber("EE456");
        transactionDto.setLocalDateTime(LocalDateTime.now());

        // Tagastame RETURN statement'iga accountDto objekti
        // Peale return'i minnakse sellest defineeritud meetodist välja
        return transactionDto;
    }

    // See meetod on defineeritud nii, et ta võtab sisse Bank ja TransactionDto tüüpi objektid
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama RequestResult tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public RequestResult addNewTransaction(Bank bank, TransactionDto transaction) {
        // Võtame addNewTransaction() signatuuri parameetris sisse Bank ja TransactionDto tüüpi objektid
        // Siin signatuuris antakse nendele objektidele nimeks 'bank' ja 'transaction'

        // Loome uue RequestResult tüüpi objekti 'result'
        // See on siis see objekt, mida meie meetod addNewTransaction() hakkab hiljem return'iga tagastama
        RequestResult result = new RequestResult();

        // võtame getteri abil 'bank' objekti küljest kontode listi (list kõikidest senistest kontodest) ja lisame selle muutujasse
        List<AccountDto> accounts = bank.getAccounts();

        // võtame getteri abil 'transaction' objekti (sisse tulnud POST sõnumi JSON) küljest account ID ja lisame selle muutujasse
        int accountId = transaction.getAccountId();

        // kontrollime kas konto eksisteerib (konto ID järgi - 1, 2, 3, jne)
        // Kutsume välja meie poolt defineeritud meetodi nimega accountIdExist()
        // See meetod on meil ära defineeritud AccountService klassis
        // Kaasa anname parameetrina 'accounts' objekti (kõik 'bank' objektis olevad kontod) ja 'accountId' (otsitava konto ID)
        // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
        // nagu meetodi nimigi ütleb, vaatame, et kas sellise accountId'ga konto eksisteerib  meie 'accounts' listis
        // vaata ka kommentaare selle meetodi sees
        if (!accountService.accountIdExist(accounts, accountId)) {
            // ! märk keerab true ja false tulemuse vastupidiseks.
            // seda meetodit accountIdExist() peaks lugema vastupidiselt ehk siis peaks mõttes lugema "if accountIdDoesNotExist"
            // Kui ! märgi teema on veel endiselt segane, siis palun vaata uuesti (algab umbes 5:50):
            // https://www.youtube.com/watch?v=-ZoNqSyZcAk&list=PLoUhzAQ9yR0JBefXhMgV9k0Ycof5yHfNC&index=14&t=265s

            // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
            result.setAccountId(accountId);
            result.setError("Account ID " + accountId + " does not exist!!!");

            // ja tagastame tulemuse
            // RETURN tähendab, et me nüüd tagastame meetodi addNewTransaction() tulemuse sinna,
            // kust me selle meetodi välja kutsusime
            // Seda käesolevat addNewTransaction() meetodit enam edasi ei töödelda. Siit meetodist lahkutakse and that's it.
            return result;
        }

        // Kui eelmisesse !accountIdExist if koodi blokki sisse ei satuta,
        // siis jätkatakse siin selle sama addNewTransaction() meetodi töötlemist edasi

        // võtame getteri abil 'transaction' objekti (sisse tulnud POST sõnumi JSON) küljest tehingu tüübi ID (transactionType) ja lisame selle muutujasse
        Character transactionType = transaction.getTransactionType();

        // võtame getteri abil 'transaction' objekti (sisse tulnud POST sõnumi JSON) küljest tehingu summa (amount) ja lisame selle muutujasse
        Integer amount = transaction.getAmount();

        // võtame getteri abil 'bank' objekti küljest järgmise vaba tehingu ID (transactionIdCounter) ja lisame selle muutujasse
        int transactionId = bank.getTransactionIdCounter();


        // pärime välja accountId abiga õige konto
        // Kutsume välja meie poolt defineeritud meetodi nimega getAccountById()
        // See meetod on meil ära defineeritud AccountService klassis
        // Kaasa anname parameetrina 'accounts' objekti (kõik 'bank' objektis olevad kontod) ja 'accountId' (otsitava konto ID)
        // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
        // Nagu meetodi nimigi ütleb, otsitakse välja see konto objekt, millel on sama ID, mis on accountId muutujas
        // Tulemuse (objekti) lisame AccountDto tüüpi muutujasse 'account'
        // vaata ka kommentaare selle meetodi sees
        AccountDto account = accountService.getAccountById(accounts, accountId);

        // võtame getteri abil 'account' objekti küljest kontojäägi ('balance') väärtuse ja lisame selle muutujasse
        Integer balance = account.getBalance();

        // Defineerime int tüüpi muutuja 'newBalance'
        // Me hetkel sellele muutujale väärust ei anna
        // aga me hiljem kasutame seda muutujat erinevates kohtades ja see poleks mõistlik,
        // et me defineerime mitu korda, seda tüüpi muutujat nimega 'newBalance'
        int newBalance;
        // Sama mõte, mis newBalance kommentaarides
        String receiverAccountNumber;


        // töötleme läbi erinevad olukorrad. Vastavalt 'transactionType' väärtusele käivitatakse vastav koodiblokk
        switch (transactionType) {

            case NEW_ACCOUNT:
                // kui transactionType == 'n'

                // täidame ära transaction objekti vastavalt sellisele loogikale, nagu me seda õigeks peame
                // JSON sõnumist on meil juba selles 'transaction' objektis juba alljärgnevate väljade väärtused olemas,
                // seega neid ei ole vaja väärtustada
                // accountId
                // transactionType

                // lisame setteritega vastavad väärtused
                transaction.setId(transactionId);
                transaction.setSenderAccountNumber(null);
                transaction.setReceiverAccountNumber(null);
                transaction.setBalance(0);
                transaction.setAmount(0);
                transaction.setLocalDateTime(LocalDateTime.now());

                // lisame tehingu transactionite alla
                // Kutsume välja meie poolt defineeritud meetodi nimega addTransactionToTransactions()
                // See meetod on meil ära defineeritud Bank klassis
                // addTransactionToTransactions() on meil selliselt defineeritud, et see võtab sisse parameetritena:
                // TransactionDto tüüpi objekti
                // See on selleks vajalik, et me saaksime anda meetodisse kaasa anda objektid, kus sees on mingid andmed,
                // millega me soovime kuidagi toimetada.
                // See addTransactionToTransactions() meetod ei tagasta mingit objekti
                // seega selle tulemust ei saa kuhugi muutujasse panna
                // Nagu meetodi nimigi ütleb, lisame uue tehingu olemasolevate tehingute listi
                // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                // https://youtu.be/KtZfO5z_JzQ
                // https://youtu.be/vJn0BuWFrBE
                // vaata ka kommentaare selle meetodi sees
                bank.addTransactionToTransactions(transaction);

                // Kutsume välja 'bank' objekti küljes oleva meetodi incrementTransactionId()
                // See meetod on siis ära defineeritud Bank klassis
                // mingeid parameetreid (andmeid) see incrementAccountIdCounter() meetod sisse ei võta.
                // me ei anna meetodi välja kutsumisel talle midagi ka kaasa
                // nagu meetodi nimigi ütleb, incrementeerime/suurendame 'bank' objekti sees olevat transactionIdCounter'it
                // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                // https://youtu.be/KtZfO5z_JzQ
                // https://youtu.be/vJn0BuWFrBE
                // vaata ka kommentaare selle meetodi sees
                bank.incrementTransactionId();

                // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
                result.setTransactionId(transactionId);
                result.setAccountId(accountId);
                result.setMessage("Successfully added 'new account' transaction");

                // Tagastame RETURN statement'iga 'result' objekti
                // Peale return'i minnakse sellest defineeritud addNewTransaction() meetodist välja
                return result;


            case DEPOSIT:
                // kui transactionType == 'd'

                // arvutame välja uue kontojäägi ('balance')
                newBalance = balance + amount;

                // täidame ära transaction objekti vastavalt sellisele loogikale, nagu me seda õigeks peame
                // JSON sõnumist on meil juba selles 'transaction' objektis juba alljärgnevate väljade väärtused olemas,
                // seega neid ei ole vaja väärtustada
                // accountId
                // amount
                // transactionType

                // lisame setteritega vastavad väärtused
                transaction.setId(transactionId);
                transaction.setSenderAccountNumber(ATM);
                transaction.setReceiverAccountNumber(account.getAccountNumber());
                transaction.setBalance(newBalance);
                transaction.setLocalDateTime(LocalDateTime.now());

                // lisame tehingu transactionite alla
                // Kutsume välja meie poolt defineeritud meetodi nimega addTransactionToTransactions()
                // See meetod on meil ära defineeritud Bank klassis
                // addTransactionToTransactions() on meil selliselt defineeritud, et see võtab sisse parameetritena:
                // TransactionDto tüüpi objekti
                // See on selleks vajalik, et me saaksime anda meetodisse kaasa anda objektid, kus sees on mingid andmed,
                // millega me soovime kuidagi toimetada.
                // See addTransactionToTransactions() meetod ei tagasta mingit objekti
                // seega selle tulemust ei saa kuhugi muutujasse panna
                // Nagu meetodi nimigi ütleb, lisame uue tehingu olemasolevate tehingute listi
                // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                // https://youtu.be/KtZfO5z_JzQ
                // https://youtu.be/vJn0BuWFrBE
                // vaata ka kommentaare selle meetodi sees
                bank.addTransactionToTransactions(transaction);

                // Kutsume välja 'bank' objekti küljes oleva meetodi incrementTransactionId()
                // See meetod on siis ära defineeritud Bank klassis
                // mingeid parameetreid (andmeid) see incrementAccountIdCounter() meetod sisse ei võta.
                // me ei anna meetodi välja kutsumisel talle midagi ka kaasa
                // nagu meetodi nimigi ütleb, incrementeerime/suurendame 'bank' objekti sees olevat transactionIdCounter'it
                // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                // https://youtu.be/KtZfO5z_JzQ
                // https://youtu.be/vJn0BuWFrBE
                // vaata ka kommentaare selle meetodi sees
                bank.incrementTransactionId();

                // uuendame setteriga konto kontojääki ('balance')
                account.setBalance(newBalance);

                // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
                result.setTransactionId(transactionId);
                result.setAccountId(accountId);
                result.setMessage("Successfully made deposit transaction");

                // Tagastame RETURN statement'iga 'result' objekti
                // Peale return'i minnakse sellest defineeritud addNewTransaction() meetodist välja
                return result;

            case WITHDRAWAL:
                // kui transactionType == 'w'

                // kontrollime kas kontol on piisavalt palju raha
                // Kutsume välja meie poolt defineeritud meetodi nimega enoughMoneyOnAccount()
                // See meetod on meil ära defineeritud BalanceService klassis
                // See meetod on võtab sisse int ja int tüüpi väärtused
                // Kaasa anname parameetrina 'balance'  ja 'amount' väärtused
                // See on selleks vajalik, et me saaksime seal meetodis nendega toimetada:
                // nagu meetodi nimigi ütleb, vaatame, et kas kontojääk on piisav, et me võime kontolt raha hakata võtma
                // vaata ka kommentaare selle meetodi sees
                if (!balanceService.enoughMoneyOnAccount(balance, amount)) {
                    // ! märk keerab true ja false tulemuse vastupidiseks.
                    // seda meetodit enoughMoneyOnAccount() peaks lugema vastupidiselt ehk siis peaks mõttes lugema "if notEnoughMoneyOnAccount"
                    // Kui ! märgi teema on veel endiselt segane, siis palun vaata uuesti (algab umbes 5:50):
                    // https://www.youtube.com/watch?v=-ZoNqSyZcAk&list=PLoUhzAQ9yR0JBefXhMgV9k0Ycof5yHfNC&index=14&t=265s

                    // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
                    result.setAccountId(accountId);
                    result.setError("Not enough money: " + amount);
                    return result;
                }

                // arvutame välja uue kontojäägi
                newBalance = balance - amount;

                // täidame ära transaction objekti vastavalt sellisele loogikale, nagu me seda õigeks peame
                // JSON sõnumist on meil juba selles 'transaction' objektis juba alljärgnevate väljade väärtused olemas,
                // seega neid ei ole vaja väärtustada
                // accountId
                // amount
                // transactionType

                // lisame setteritega vastavad väärtused
                transaction.setId(transactionId);
                transaction.setSenderAccountNumber(account.getAccountNumber());
                transaction.setReceiverAccountNumber(ATM);
                transaction.setBalance(newBalance);
                transaction.setLocalDateTime(LocalDateTime.now());

                // lisame tehingu transactionite alla
                // Kutsume välja meie poolt defineeritud meetodi nimega addTransactionToTransactions()
                // See meetod on meil ära defineeritud Bank klassis
                // addTransactionToTransactions() on meil selliselt defineeritud, et see võtab sisse parameetritena:
                // TransactionDto tüüpi objekti
                // See on selleks vajalik, et me saaksime anda meetodisse kaasa anda objektid, kus sees on mingid andmed,
                // millega me soovime kuidagi toimetada.
                // See addTransactionToTransactions() meetod ei tagasta mingit objekti
                // seega selle tulemust ei saa kuhugi muutujasse panna
                // Nagu meetodi nimigi ütleb, lisame uue tehingu olemasolevate tehingute listi
                // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                // https://youtu.be/KtZfO5z_JzQ
                // https://youtu.be/vJn0BuWFrBE
                // vaata ka kommentaare selle meetodi sees
                bank.addTransactionToTransactions(transaction);

                // Kutsume välja 'bank' objekti küljes oleva meetodi incrementTransactionId()
                // See meetod on siis ära defineeritud Bank klassis
                // mingeid parameetreid (andmeid) see incrementAccountIdCounter() meetod sisse ei võta.
                // me ei anna meetodi välja kutsumisel talle midagi ka kaasa
                // nagu meetodi nimigi ütleb, incrementeerime/suurendame 'bank' objekti sees olevat transactionIdCounter'it
                // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                // https://youtu.be/KtZfO5z_JzQ
                // https://youtu.be/vJn0BuWFrBE
                // vaata ka kommentaare selle meetodi sees
                bank.incrementTransactionId();

                // uuendame setteriga konto kontojääki ('balance')
                account.setBalance(newBalance);

                // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
                result.setTransactionId(transactionId);
                result.setAccountId(accountId);
                result.setMessage("Successfully made withdrawal transaction");
                return result;

            case SEND_MONEY:
                // kui transactionType == 's'

                // kontrollime kas kontol on piisavalt palju raha
                // Kutsume välja meie poolt defineeritud meetodi nimega enoughMoneyOnAccount()
                // See meetod on meil ära defineeritud BalanceService klassis
                // See meetod on võtab sisse int ja int tüüpi väärtused
                // Kaasa anname parameetrina 'balance'  ja 'amount' väärtused
                // See on selleks vajalik, et me saaksime seal meetodis nendega toimetada:
                // nagu meetodi nimigi ütleb, vaatame, et kas kontojääk ('balance') on piisav, et me võime kontolt raha hakata võtma
                // vaata ka kommentaare selle meetodi sees
                if (!balanceService.enoughMoneyOnAccount(balance, amount)) {
                    // ! märk keerab true ja false tulemuse vastupidiseks.
                    // seda meetodit enoughMoneyOnAccount() peaks lugema vastupidiselt ehk siis peaks mõttes lugema "if notEnoughMoneyOnAccount"
                    // Kui ! märgi teema on veel endiselt segane, siis palun vaata uuesti (algab umbes 5:50):
                    // https://www.youtube.com/watch?v=-ZoNqSyZcAk&list=PLoUhzAQ9yR0JBefXhMgV9k0Ycof5yHfNC&index=14&t=265s

                    // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
                    result.setAccountId(accountId);
                    result.setError("Not enough money: " + amount);
                    return result;
                }

                // arvutame välja uue kontojäägi
                newBalance = balance - amount;

                // täidame ära transaction objekti vastavalt sellisele loogikale, nagu me seda õigeks peame
                // JSON sõnumist on meil juba selles 'transaction' objektis juba alljärgnevate väljade väärtused olemas,
                // seega neid ei ole vaja väärtustada
                // accountId
                // receiverAccountNumber
                // amount
                // transactionType

                // lisame setteritega vastavad väärtused
                transaction.setId(transactionId);
                transaction.setSenderAccountNumber(account.getAccountNumber());
                transaction.setBalance(newBalance);
                transaction.setLocalDateTime(LocalDateTime.now());


                // lisame tehingu transactionite alla
                // Kutsume välja meie poolt defineeritud meetodi nimega addTransactionToTransactions()
                // See meetod on meil ära defineeritud Bank klassis
                // addTransactionToTransactions() on meil selliselt defineeritud, et see võtab sisse parameetritena:
                // TransactionDto tüüpi objekti
                // See on selleks vajalik, et me saaksime anda meetodisse kaasa anda objektid, kus sees on mingid andmed,
                // millega me soovime kuidagi toimetada.
                // See addTransactionToTransactions() meetod ei tagasta mingit objekti
                // seega selle tulemust ei saa kuhugi muutujasse panna
                // Nagu meetodi nimigi ütleb, lisame uue tehingu olemasolevate tehingute listi
                // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                // https://youtu.be/KtZfO5z_JzQ
                // https://youtu.be/vJn0BuWFrBE
                // vaata ka kommentaare selle meetodi sees
                bank.addTransactionToTransactions(transaction);

                // Kutsume välja 'bank' objekti küljes oleva meetodi incrementTransactionId()
                // See meetod on siis ära defineeritud Bank klassis
                // mingeid parameetreid (andmeid) see incrementAccountIdCounter() meetod sisse ei võta.
                // me ei anna meetodi välja kutsumisel talle midagi ka kaasa
                // nagu meetodi nimigi ütleb, incrementeerime/suurendame 'bank' objekti sees olevat transactionIdCounter'it
                // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                // https://youtu.be/KtZfO5z_JzQ
                // https://youtu.be/vJn0BuWFrBE
                // vaata ka kommentaare selle meetodi sees
                bank.incrementTransactionId();

                // uuendame setteriga konto kontojääki ('balance')
                account.setBalance(newBalance);

                // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
                result.setTransactionId(transactionId);
                result.setAccountId(accountId);
                result.setMessage("Successfully sent money");

                // teeme SAAJA transaktsiooni (ainult sellisel juhul kui saaja kontonumber on ka meie klient)

                // võtame getteri abil 'transaction' objekti (sisse tulnud POST sõnumi JSON) küljest saaja konto numbri (receiverAccountNumber) ja lisame selle muutujasse
                receiverAccountNumber = transaction.getReceiverAccountNumber();

                // kontrollime kas saaja konto number eksisteerib meie andmebaasis meie konto numbrite hulgas

                // kontrollime kas konto eksisteerib (KONTONUMBRI järgi - "EE123", "EE456", jne)
                // Kutsume välja meie poolt defineeritud meetodi nimega accountNumberExist()
                // See meetod on meil ära defineeritud AccountService klassis
                // Kaasa anname parameetrina 'accounts' objekti (kõik 'bank' objektis olevad kontod) ja 'receiverAccountNumber' (otsitava konto kontonumber - "EE123", jne)
                // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
                // nagu meetodi nimigi ütleb, vaatame, et kas sellise receiverAccountNumber'ga konto eksisteerib  meie 'accounts' listis
                // vaata ka kommentaare selle meetodi sees
                if (accountService.accountNumberExist(accounts, receiverAccountNumber)) {

                    // kui leiti sama receiverAccountNumber meie kontode hulgast (kui saaja kontonumber on ka meie klient)
                    // siis teeme ära ka receive tehingu

                    // pärime välja receiverAccountNumber abiga õige konto
                    // Kutsume välja meie poolt defineeritud meetodi nimega getAccountByNumber()
                    // See meetod on meil ära defineeritud AccountService klassis
                    // Kaasa anname parameetrina 'accounts' objekti (kõik 'bank' objektis olevad kontod) ja 'receiverAccountNumber' (otsitava konto kontonumber - "EE123", jne)
                    // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
                    // Nagu meetodi nimigi ütleb, otsitakse välja see konto objekt, millel on sama konto number, mis on receiverAccountNumber muutujas
                    // Tulemuse (objekti) lisame AccountDto tüüpi muutujasse 'receiverAccount'
                    // vaata ka kommentaare selle meetodi sees
                    AccountDto receiverAccount = accountService.getAccountByNumber(accounts, receiverAccountNumber);

                    // võtame getteri abil 'receiverAccount' objekti küljest konto ID ja lisame selle muutujasse
                    int receiverAccountId = receiverAccount.getId();

                    // võtame getteri abil 'receiverAccount' objekti küljest kontojäägi ('balance') väärtuse ja lisame selle muutujasse
                    Integer receiverCurrentBalance = receiverAccount.getBalance();

                    // arvutame välja uue kontojäägi ja lisame selle muutujasse
                    int receiverNewBalance = receiverCurrentBalance + amount;

                    //loome uue transaktsiooni objekti 'receiverTransaction'
                    TransactionDto receiverTransaction = new TransactionDto();

                    // täidame ära transaction
                    receiverTransaction.setId(bank.getTransactionIdCounter());
                    receiverTransaction.setAccountId(receiverAccountId);
                    receiverTransaction.setSenderAccountNumber(account.getAccountNumber());
                    receiverTransaction.setReceiverAccountNumber(receiverAccountNumber);
                    receiverTransaction.setBalance(receiverNewBalance);
                    receiverTransaction.setLocalDateTime(LocalDateTime.now());
                    receiverTransaction.setAmount(amount);
                    receiverTransaction.setTransactionType(RECEIVE_MONEY);

                    // lisame tehingu transactionite alla
                    // Kutsume välja meie poolt defineeritud meetodi nimega addTransactionToTransactions()
                    // See meetod on meil ära defineeritud Bank klassis
                    // addTransactionToTransactions() on meil selliselt defineeritud, et see võtab sisse parameetritena:
                    // TransactionDto tüüpi objekti
                    // See on selleks vajalik, et me saaksime anda meetodisse kaasa anda objektid, kus sees on mingid andmed,
                    // millega me soovime kuidagi toimetada.
                    // See addTransactionToTransactions() meetod ei tagasta mingit objekti
                    // seega selle tulemust ei saa kuhugi muutujasse panna
                    // Nagu meetodi nimigi ütleb, lisame uue tehingu olemasolevate tehingute listi
                    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                    // https://youtu.be/KtZfO5z_JzQ
                    // https://youtu.be/vJn0BuWFrBE
                    // vaata ka kommentaare selle meetodi sees
                    bank.addTransactionToTransactions(receiverTransaction);

                    // Kutsume välja 'bank' objekti küljes oleva meetodi incrementTransactionId()
                    // See meetod on siis ära defineeritud Bank klassis
                    // mingeid parameetreid (andmeid) see incrementAccountIdCounter() meetod sisse ei võta.
                    // me ei anna meetodi välja kutsumisel talle midagi ka kaasa
                    // nagu meetodi nimigi ütleb, incrementeerime/suurendame 'bank' objekti sees olevat transactionIdCounter'it
                    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
                    // https://youtu.be/KtZfO5z_JzQ
                    // https://youtu.be/vJn0BuWFrBE
                    // vaata ka kommentaare selle meetodi sees
                    bank.incrementTransactionId();

                    // uuendame setteriga konto kontojääki ('balance')
                    receiverAccount.setBalance(receiverNewBalance);
                }

                // Tagastame RETURN statement'iga 'result' objekti
                // Peale return'i minnakse sellest defineeritud meetodist välja
                return result;

            default:
                // kui switchi case's ükski transactionType ei klappinud ('n', 'd', 'w', 's', 'r')
                // siis sätestame result objekti errori
                result.setError("Unknown transaction type: " + transactionType);

                // Tagastame RETURN statement'iga 'result' objekti
                // Peale return'i minnakse sellest defineeritud meetodist välja
                return result;

        }

    }


    // See meetod on defineeritud nii, et ta võtab sisse Bank ja TransactionDto tüüpi objektid
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama RequestResult tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public RequestResult receiveNewTransaction(Bank bank, TransactionDto transaction) {
        // Võtame addNewTransaction() signatuuri parameetris sisse Bank ja TransactionDto tüüpi objektid
        // Siin signatuuris antakse nendele objektidele nimeks 'bank' ja 'transaction'

        // Loome uue RequestResult tüüpi objekti 'result'
        // See on siis see objekt, mida meie meetod receiveNewTransaction() hakkab hiljem return'iga tagastama
        RequestResult result = new RequestResult();

        // võtame getteri abil 'transaction' objekti (sisse tulnud POST sõnumi JSON) küljest saaja konto numbri (receiverAccountNumber) ja lisame selle muutujasse
        String receiverAccountNumber = transaction.getReceiverAccountNumber();

        // võtame getteri abil 'bank' objekti küljest kontode listi (list kõikidest senistest kontodest) ja lisame selle muutujasse
        List<AccountDto> accounts = bank.getAccounts();

        // kontrollime kas konto eksisteerib (KONTONUMBRI järgi - "EE123", "EE456", jne)
        // Kutsume välja meie poolt defineeritud meetodi nimega accountNumberExist()
        // See meetod on meil ära defineeritud AccountService klassis
        // Kaasa anname parameetrina 'accounts' objekti (kõik 'bank' objektis olevad kontod) ja 'receiverAccountNumber' (otsitava konto kontonumber - "EE123", jne)
        // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
        // nagu meetodi nimigi ütleb, vaatame, et kas sellise receiverAccountNumber'iga konto eksisteerib  meie 'accounts' listis
        // vaata ka kommentaare selle meetodi sees
        if (!accountService.accountNumberExist(accounts, receiverAccountNumber)) {
            // ! märk keerab true ja false tulemuse vastupidiseks.
            // seda meetodit accountNumberExist() peaks lugema vastupidiselt ehk siis peaks mõttes lugema "if accountNumberDoesNotExist"
            // Kui ! märgi teema on veel endiselt segane, siis palun vaata uuesti (algab umbes 5:50):
            // https://www.youtube.com/watch?v=-ZoNqSyZcAk&list=PLoUhzAQ9yR0JBefXhMgV9k0Ycof5yHfNC&index=14&t=265s

            // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie receiveNewTransaction() meetodi alguses)
            result.setError("No such account in our bank: " + receiverAccountNumber);

            // ja tagastame tulemuse
            // RETURN tähendab, et me nüüd tagastame meetodi receiveNewTransaction() tulemuse sinna,
            // kust me selle meetodi välja kutsusime
            // Seda käesolevat receiveNewTransaction() meetodit enam edasi ei töödelda. Siit meetodist lahkutakse and that's it.
            return result;
        }

        // pärime välja receiverAccountNumber abiga õige konto
        // Kutsume välja meie poolt defineeritud meetodi nimega getAccountByNumber()
        // See meetod on meil ära defineeritud AccountService klassis
        // Kaasa anname parameetrina 'accounts' objekti (kõik 'bank' objektis olevad kontod) ja 'receiverAccountNumber' (otsitava konto kontonumber - "EE123", jne)
        // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
        // Nagu meetodi nimigi ütleb, otsitakse välja see konto objekt, millel on sama konto number, mis on receiverAccountNumber muutujas
        // Tulemuse (objekti) lisame AccountDto tüüpi muutujasse 'receiverAccount'
        // vaata ka kommentaare selle meetodi sees
        AccountDto receiverAccount = accountService.getAccountByNumber(accounts, receiverAccountNumber);

        // võtame getteri abil 'bank' objekti küljest järgmise vaba tehingu ID (transactionIdCounter) ja lisame selle muutujasse
        int transactionId = bank.getTransactionIdCounter();

        // võtame getteri abil 'receiverAccount' objekti küljest kontojäägi ('balance') väärtuse ja lisame selle muutujasse
        Integer receiverCurrentBalance = receiverAccount.getBalance();

        // võtame getteri abil 'transaction' objekti (sisse tulnud POST sõnumi JSON) küljest tehingu summa (amount) ja lisame selle muutujasse
        Integer amount = transaction.getAmount();

        // liidame kokku saaja praeguse kontojäägi ('receiverCurrentBalance') ja summa ('amount')
        int receiverNewBalance = receiverCurrentBalance + amount;
        int receiverAccountId = receiverAccount.getId();

        // lisame setteritega vastavad väärtused
        transaction.setTransactionType(RECEIVE_MONEY);
        transaction.setBalance(receiverNewBalance);
        transaction.setId(transactionId);
        transaction.setAccountId(receiverAccountId);
        transaction.setLocalDateTime(LocalDateTime.now());

        // lisame tehingu transactionite alla
        // Kutsume välja meie poolt defineeritud meetodi nimega addTransactionToTransactions()
        // See meetod on meil ära defineeritud Bank klassis
        // addTransactionToTransactions() on meil selliselt defineeritud, et see võtab sisse parameetritena:
        // TransactionDto tüüpi objekti
        // See on selleks vajalik, et me saaksime anda meetodisse kaasa anda objektid, kus sees on mingid andmed,
        // millega me soovime kuidagi toimetada.
        // See addTransactionToTransactions() meetod ei tagasta mingit objekti
        // seega selle tulemust ei saa kuhugi muutujasse panna
        // Nagu meetodi nimigi ütleb, lisame uue tehingu olemasolevate tehingute listi
        // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
        // https://youtu.be/KtZfO5z_JzQ
        // https://youtu.be/vJn0BuWFrBE
        // vaata ka kommentaare selle meetodi sees
        bank.addTransactionToTransactions(transaction);

        // Kutsume välja 'bank' objekti küljes oleva meetodi incrementTransactionId()
        // See meetod on siis ära defineeritud Bank klassis
        // mingeid parameetreid (andmeid) see incrementAccountIdCounter() meetod sisse ei võta.
        // me ei anna meetodi välja kutsumisel talle midagi ka kaasa
        // nagu meetodi nimigi ütleb, incrementeerime/suurendame 'bank' objekti sees olevat transactionIdCounter'it
        // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
        // https://youtu.be/KtZfO5z_JzQ
        // https://youtu.be/vJn0BuWFrBE
        // vaata ka kommentaare selle meetodi sees
        bank.incrementTransactionId();

        // uuendame setteriga konto kontojääki ('balance')
        receiverAccount.setBalance(receiverNewBalance);

        // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
        result.setTransactionId(transactionId);
        result.setMessage("Transaction received");

        // Tagastame RETURN statement'iga 'result' tulemuse
        // Peale return'i minnakse sellest defineeritud receiveNewTransaction() meetodist välja
        return result;
    }

}
