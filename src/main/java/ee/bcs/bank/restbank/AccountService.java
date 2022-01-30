package ee.bcs.bank.restbank;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

// @Service - Springi annotatsioon, mis tekitab klassist Bean'i.
// Tänu sellele on hiljem võimalik sellele teenusele hõlpsat @Resource'ga ligi saada
// Kui seda annotatsiooni pole, siis Springil pole sellest klassist Bean'i ja ta ei tea sellest midagi
@Service
public class AccountService {

    // See meetod on defineeritud nii, et mingeid parameetreid (andmeid) ta sisse ei võta
    // See meetod peab tagastama AccountDto tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public AccountDto createExampleAccount() {

        // Loome uue AccountDto tüüpi objekti
        // Anname muutujale nimeks 'account'
        // Kui klasside ja objektide teema on veel endiselt segane, siis palun vaata uuesti "Kasutaja poolt loodavad klassid":
        // https://youtu.be/pMYcginZIjQ
        AccountDto account = new AccountDto();

        // lisame setteritega mingid väärtused
        account.setAccountNumber(createRandomAccountNumber());
        account.setFirstName("Juss");
        account.setLastName("Sepp");
        account.setBalance(0);
        account.setLocked(false);

        // Tagastame RETURN statement'iga 'account' objekti
        // Peale return'i minnakse sellest defineeritud meetodist välja
        return account;
    }

    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    private String createRandomAccountNumber() {
        //  Loob ja tagastab mingi suvalise konto numbri vahemikus EE1000 -  EE9999
        Random random = new Random();
        return "EE" + (random.nextInt(9999) + 1000);
    }

    // See meetod on defineeritud nii, et ta võtab sisse List<AccountDto> ja int tüüpi objektid
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama booleani tüüpi vastuse (true/false)
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public boolean accountIdExist(List<AccountDto> accounts, int accountId) {
        // Võtame accountIdExist() signatuuri parameetris sisse List<AccountDto> ja int tüüpi objektid
        // Siin signatuuris antakse nendele objektidele nimeks 'accounts' ja 'accountId'
        // accounts = list kontodest mis on meie bank objektis
        // accountId = konto mille olemasolu soovime kontrollida

        // Teeme for tsükkli kõikide kontode kohta ('accounts'), mis on Listis
        // Kui Listide for tsükli teema on veel endiselt segane, siis palun vaata uuesti "FOR tsükkel List":
        // https://youtu.be/aR5hP8dXvZ8
        // Käime kõik kontod läbi ('accounts') ja lisame igal tsükklil ühe objekti 'account' muutujasse
        for (AccountDto account : accounts) {
            // Kui 'account' objekti field/väli 'id' (saadakse getteri abil) on võrdne muutujaga 'accountId',
            // siis minnakse if koodibloki sisse
            if (account.getId() == accountId) {
                // Niipea kui satutakse "return true" statement'i peale siis tagastatakse 'true"
                // Peale return'i minnakse sellest defineeritud meetodist välja
                return true;
            }
        }

        // Kui käidi for-tsükliga kõik kontod läbi ('accounts') aga ei leitud ühtegi samasugust ID'd ('accountId'),
        // siis jõutakse ju siia "return false" rea peale
        // Tagastatakse 'false'
        // Peale return'i minnakse sellest defineeritud meetodist välja
        return false;
    }


    // See meetod on defineeritud nii, et ta võtab sisse List<AccountDto> tüüpi objekti ja int tüüpi väärtuse
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama AccountDto tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public AccountDto getAccountById(List<AccountDto> accounts, int accountId) {
        // Võtame getAccountById() signatuuri parameetris sisse List<AccountDto> ja int tüüpi objektid
        // Siin signatuuris antakse nendele objektidele nimeks 'accounts' ja 'accountId'
        // accounts = list kontodest mis on meie bank objektis
        // accountId = konto ID, mille objekti me soovime siin meetodis tagastada

        // Teeme for tsükkli kõikide kontode kohta ('accounts'), mis on Listis
        // Kui Listide for tsükli teema on veel endiselt segane, siis palun vaata uuesti "FOR tsükkel List":
        // https://youtu.be/aR5hP8dXvZ8
        // Käime kõik kontod läbi ('accounts') ja lisame igal tsükklil ühe objekti 'account' muutujasse
        for (AccountDto account : accounts) {
            // Kui 'account' objekti field/väli 'id' (saadakse getteri abil) on võrdne muutujaga 'accountId',
            // siis minnakse if koodibloki sisse
            if (account.getId() == accountId) {
                // Niipea kui satutakse "return account" statement'i peale siis tagastatakse 'account' objekt"
                // Peale return'i minnakse sellest defineeritud meetodist välja
                return account;
            }
        }

        // Kui käidi for-tsükliga kõik kontod läbi ('accounts') aga ei leitud ühtegi samasugust ID'd ('accountId'),
        // siis jõutakse ju siia "return null" rea peale
        // Seda aga ei tohiks ju juhtuda, sest enne getAccountById() meetodi välja kutsumist,
        // me kontrollisime meetodiga accountIdExist(), et kas meil on selline konto olemas
        // Kui aga siia ikkagi jõutakse siis tagastatakse 'null'
        // Peale return'i minnakse sellest defineeritud meetodist välja
        return null;
    }

    // See meetod on defineeritud nii, et ta võtab sisse List<AccountDto> ja String tüüpi objektid
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama booleani tüüpi vastuse (true/false)
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public boolean accountNumberExist(List<AccountDto> accounts, String receiverAccountNumber) {
        // Võtame accountNumberExist() signatuuri parameetris sisse List<AccountDto> ja String tüüpi objektid
        // Siin signatuuris antakse nendele objektidele nimeks 'accounts' ja 'receiverAccountNumber'
        // accounts = list kontodest mis on meie bank objektis
        // receiverAccountNumber = kontonumber mille olemasolu soovime kontrollida

        // Teeme for tsükkli kõikide kontode kohta ('accounts'), mis on Listis
        // Kui Listide for tsükli teema on veel endiselt segane, siis palun vaata uuesti "FOR tsükkel List":
        // https://youtu.be/aR5hP8dXvZ8
        // Käime kõik kontod läbi ('accounts') ja lisame igal tsükklil ühe objekti 'account' muutujasse
        for (AccountDto account : accounts) {
            // Kui 'account' objekti field/väli 'receiverAccountNumber' (saadakse getteri abil) on võrdne muutujaga 'receiverAccountNumber',
            // siis minnakse if koodibloki sisse
            if (account.getAccountNumber().equals(receiverAccountNumber)) {
                // Niipea kui satutakse "return true" statement'i peale siis tagastatakse 'true"
                // Peale return'i minnakse sellest defineeritud meetodist välja
                return true;
            }
        }

        // Kui käidi for-tsükliga kõik kontod läbi ('accounts') aga ei leitud ühtegi samasugust konto numbrit ('receiverAccountNumber'),
        // siis jõutakse ju siia "return false" rea peale
        // Tagastatakse 'rea'
        // Peale return'i minnakse sellest defineeritud meetodist välja
        return false;
    }


    // See meetod on defineeritud nii, et ta võtab sisse List<AccountDto> ja String tüüpi objektid
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama AccountDto tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public AccountDto getAccountByNumber(List<AccountDto> accounts, String receiverAccountNumber) {
        // Võtame getAccountByNumber() signatuuri parameetris sisse List<AccountDto> ja srting tüüpi objektid
        // Siin signatuuris antakse nendele objektidele nimeks 'accounts' ja 'receiverAccountNumber'
        // accounts = list kontodest mis on meie bank objektis
        // receiverAccountNumber = konto number, mille objekti me soovime siin meetodis tagastada

        // Teeme for tsükkli kõikide kontode kohta ('accounts'), mis on Listis
        // Kui Listide for tsükli teema on veel endiselt segane, siis palun vaata uuesti "FOR tsükkel List":
        // https://youtu.be/aR5hP8dXvZ8
        // Käime kõik kontod läbi ('accounts') ja lisame igal tsükklil ühe objekti 'account' muutujasse
        for (AccountDto account : accounts) {
            // Kui 'account' objekti field/väli 'receiverAccountNumber' (saadakse getteri abil) on võrdne muutujaga 'receiverAccountNumber',
            // siis minnakse if koodibloki sisse
            if (account.getAccountNumber().equals(receiverAccountNumber)) {
                // Niipea kui satutakse "return account" statement'i peale siis tagastatakse 'account' objekt"
                // Peale return'i minnakse sellest defineeritud meetodist välja
                return account;
            }
        }

        // Kui käidi for-tsükliga kõik kontod läbi ('accounts') aga ei leitud ühtegi samasugust konto numbrit ('receiverAccountNumber'),
        // siis jõutakse ju siia "return null" rea peale
        // Seda aga ei tohiks ju juhtuda, sest enne getAccountByNumber() meetodi välja kutsumist,
        // me kontrollisime meetodiga accountNumberExist(), et kas meil on selline konto olemas
        // Kui aga siia ikkagi jõutakse siis tagastatakse 'null'
        // Peale return'i minnakse sellest defineeritud meetodist välja
        return null;
    }

    // See meetod on defineeritud nii, et ta võtab sisse List<AccountDto> ja AccountDto tüüpi objektid
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama RequestResult tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public RequestResult updateOwnerDetails(List<AccountDto> accounts, AccountDto incomingUpdatedAccount) {
        // Võtame updateOwnerDetails() signatuuri parameetris sisse List<AccountDto> ja AccountDto tüüpi objektid
        // Siin signatuuris antakse nendele objektidele nimeks 'accounts' ja 'incomingUpdatedAccount'
        // accounts = list kontodest mis on meie bank objektis
        // incomingUpdatedAccount = konto objekt (mille väärtused tulid meil JSON'ist)


        // Loome uue RequestResult tüüpi objekti 'result'
        // See on siis see objekt, mida meie meetod updateOwnerDetails() hakkab hiljem return'iga tagastama
        RequestResult result = new RequestResult();


        int accountId = incomingUpdatedAccount.getId();

        // kontrollime kas konto eksisteerib (konto ID järgi - 1, 2, 3, jne)
        // Kutsume välja meie poolt defineeritud meetodi nimega accountIdExist()
        // See meetod on meil ära defineeritud AccountService klassis
        // Kaasa anname parameetrina 'accounts' objekti (kõik 'bank' objektis olevad kontod) ja 'accountId' (otsitava konto ID)
        // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
        // nagu meetodi nimigi ütleb, vaatame, et kas sellise accountId'ga konto eksisteerib  meie 'accounts' listis
        // vaata ka kommentaare selle meetodi sees
        if (!accountIdExist(accounts, accountId)) {
            // ! märk keerab true ja false tulemuse vastupidiseks.
            // seda meetodit accountIdExist() peaks lugema vastupidiselt ehk siis peaks mõttes lugema "if accountIdDoesNotExist"
            // Kui ! märgi teema on veel endiselt segane, siis palun vaata uuesti (algab umbes 5:50):
            // https://www.youtube.com/watch?v=-ZoNqSyZcAk&list=PLoUhzAQ9yR0JBefXhMgV9k0Ycof5yHfNC&index=14&t=265s

            // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
            result.setError("Account ID: " + accountId + " does not exist!");
            result.setAccountId(accountId);
            // Tagastame RETURN statement'iga 'result' objekti
            // Peale return'i minnakse sellest defineeritud meetodist välja
            return result;
        }

        // leiame konto objekti meie seniste kontode hulgast
        AccountDto account = getAccountById(accounts, accountId);

        // võtame getteri abil 'incomingUpdatedAccount' objekti küljest (JSON andmed) 'firstName' välja väärtuse ja lisame selle muutujasse
        String firstNameFromUpdatedAccount = incomingUpdatedAccount.getFirstName();

        // võtame getteri abil 'incomingUpdatedAccount' objekti küljest (JSON andmed) 'lastName' välja väärtuse ja lisame selle muutujasse
        String lastNameFromUpdatedAccount = incomingUpdatedAccount.getLastName();

        // uuendame setterite abil account (objekt mis on meie kontode listis) ees ja perekonna nime
        account.setFirstName(firstNameFromUpdatedAccount);
        account.setLastName(lastNameFromUpdatedAccount);

        // väärtustame setteritega 'result' objekti väärtused
        result.setAccountId(accountId);
        result.setMessage("Successfully updated account.");

        // Tagastame RETURN statement'iga 'result' objekti
        // Peale return'i minnakse sellest defineeritud meetodist välja
        return result;
    }

    // See meetod on defineeritud nii, et ta võtab sisse List<AccountDto> tüüpi objekti ja int väärtuse
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama RequestResult tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public RequestResult deleteAccount(List<AccountDto> accounts, int accountId) {
        // Võtame deleteAccount() signatuuri parameetris sisse List<AccountDto> tüüpi objekt ja int tüüpi väärtus
        // Siin signatuuris antakse nendele objektidele nimeks 'accounts' ja 'accountId'
        // accounts = list kontodest mis on meie bank objektis
        // accountId = konto ID, mille objekti soovime 'accounts' listist eemaldada

        // Loome uue RequestResult tüüpi objekti 'result'
        // See on siis see objekt, mida meie meetod updateOwnerDetails() hakkab hiljem return'iga tagastama
        RequestResult result = new RequestResult();

        if (!accountIdExist(accounts, accountId)) {
            result.setError("Account ID: " + accountId + " does not exist!");
            result.setAccountId(accountId);
            return result;
        }

        // leiame konto objekti meie seniste kontode hulgast
        AccountDto account = getAccountById(accounts, accountId);

        // eemaldame objekti listist (andes sisendiks objekti enda, mida soovime listist eemaldada)
        accounts.remove(account);

        // väärtustame setteritega mingid väärtused
        result.setMessage("Account deleted");
        result.setAccountId(accountId);

        // Tagastame RETURN statement'iga 'result' tulemuse
        // Peale return'i minnakse sellest defineeritud deleteAccount() meetodist välja
        return result;
    }


    // See meetod on defineeritud nii, et ta võtab sisse List<AccountDto> tüüpi objekti ja int väärtuse
    // See on selleks vajalik, et me saaksime siin meetodis nende objektidega kuidagi toimetada.
    // See meetod peab tagastama RequestResult tüüpi objekti
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public RequestResult switchLockStatus(List<AccountDto> accounts, int accountId) {
        // Loome uue RequestResult tüüpi objekti 'result'
        // See on siis see objekt, mida meie meetod updateOwnerDetails() hakkab hiljem return'iga tagastama
        RequestResult result = new RequestResult();

        // kontrollime kas konto eksisteerib (konto ID järgi - 1, 2, 3, jne)
        // Kutsume välja meie poolt defineeritud meetodi nimega accountIdExist()
        // See meetod on meil ära defineeritud AccountService klassis
        // Kaasa anname parameetrina 'accounts' objekti (kõik 'bank' objektis olevad kontod) ja 'accountId' (otsitava konto ID)
        // See on selleks vajalik, et me saaksime seal meetodis sellega toimetada:
        // nagu meetodi nimigi ütleb, vaatame, et kas sellise accountId'ga konto eksisteerib  meie 'accounts' listis
        // vaata ka kommentaare selle meetodi sees
        if (!accountIdExist(accounts, accountId)) {
            // ! märk keerab true ja false tulemuse vastupidiseks.
            // seda meetodit accountIdExist() peaks lugema vastupidiselt ehk siis peaks mõttes lugema "if accountIdDoesNotExist"
            // Kui ! märgi teema on veel endiselt segane, siis palun vaata uuesti (algab umbes 5:50):
            // https://www.youtube.com/watch?v=-ZoNqSyZcAk&list=PLoUhzAQ9yR0JBefXhMgV9k0Ycof5yHfNC&index=14&t=265s

            // meisterdame setteritega valmis 'result' objekti (result objekti defineerisime ja lõime meie addNewTransaction() meetodi alguses)
            result.setError("Account ID: " + accountId + " does not exist!");
            result.setAccountId(accountId);
            return result;
        }

        // leiame konto objekti meie seniste kontode hulgast
        AccountDto account = getAccountById(accounts, accountId);
        // Võtame 'account' objektist getteri abil locked staatuse
        Boolean lockedStatus = account.getLocked();

        // kui locked staatus on true, siis minnakse if koodibloki sisse
        if (lockedStatus) {
            // ja muudetakse setteri abil locked 'false' peale
            account.setLocked(false);
            // ning setteri abil pannakse result messageks "Account is now unlocked"
            result.setMessage("Account is now unlocked!");
        } else {
            // kui lockedStatus on 'false'
            // ja muuda setteri abil locked 'true' peale
            account.setLocked(true);
            // ning setteri abil pane result messageks "Account is now locked"
            result.setMessage("Account is now locked!");
        }

        // setteri abil lisatakse ka resulti objekti ka accountId;
        result.setAccountId(accountId);

        // Tagastame RETURN statement'iga 'result' tulemuse
        // Peale return'i minnakse sellest defineeritud switchLockStatus() meetodist välja
        return result;
    }
}
