package ee.bcs.bank.restbank;

import org.springframework.stereotype.Service;

// @Service - Springi annotatsioon, mis tekitab klassist Bean'i.
// Tänu sellele on hiljem võimalik sellele teenusele hõlpsat @Resource'ga ligi saada
// Kui seda annotatsiooni pole, siis Springil pole sellest klassist Bean'i ja ta ei tea sellest midagi
@Service
public class BankService {

    // See meetod on defineeritud nii, et ta võtab sisse Bank ja AccountDto tüüpi objektid
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama RequestResult tüüpi objekti
    public RequestResult addAccountToBank(Bank bank, AccountDto account) {
        // Võtame addAccountToBank() signatuuri parameetris sisse Bank ja AccountDto tüüpi objektid
        // Siin signatuuris antakse nendele objektidele nimeks 'bank' ja 'account'


        // Võtame 'bank' objektist järgmise vaba id väärtuse (loendur 1,2,3, jne)
        int accountId = bank.getAccountIdCounter();

        // väärtustame setteritega mingid väärtused
        account.setId(accountId);
        account.setBalance(0);
        account.setLocked(false);

        // Kutsume välja 'bank' objekti küljes oleva meetodi addAccountToAccounts()
        // See meetod on siis ära defineeritud Bank klassis
        // Kaasa anname parameetrina 'account' objekti
        // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
        // nagu meetodi nimigi ütleb, lisame uue konto olemasolevate kontode listi
        // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
        // https://youtu.be/KtZfO5z_JzQ
        // https://youtu.be/vJn0BuWFrBE
        // vaata ka kommentaare selle meetodi sees
        bank.addAccountToAccounts(account);

        // Kutsume välja 'bank' objekti küljes oleva meetodi incrementAccountIdCounter()
        // See meetod on siis ära defineeritud Bank klassis
        // mingeid parameetreid (andmeid) see incrementAccountIdCounter() meetod sisse ei võta.
        // me ei anna meetodi välja kutsumisel talle midagi kaasa
        // nagu meetodi nimigi ütleb, incrementeerime/suurendame 'bank' objekti sees olevat accountIdCounter'it
        // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
        // https://youtu.be/KtZfO5z_JzQ
        // https://youtu.be/vJn0BuWFrBE
        // vaata ka kommentaare selle meetodi sees
        bank.incrementAccountIdCounter();

        // Loome uue RequestResult tüüpi objekti 'result'
        // See on siis see objekt, mida meie meetod addAccountToBank() hakkab hiljem return'iga tagastama
        RequestResult result = new RequestResult();

        // väärtustame setteritega mingid väärtused
        result.setAccountId(accountId);
        result.setMessage("Added new account");

        // Tagastame RETURN statement'iga 'result' objekti
        // Peale return'i minnakse sellest defineeritud meetodist välja
        return result;
    }

}
