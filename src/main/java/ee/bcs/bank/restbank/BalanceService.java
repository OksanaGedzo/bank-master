package ee.bcs.bank.restbank;

import org.springframework.stereotype.Service;

// @Service - Springi annotatsioon, mis tekitab klassist Bean'i.
// Tänu sellele on hiljem võimalik sellele teenusele hõlpsat @Resource'ga ligi saada
// Kui seda annotatsiooni pole, siis Springil pole sellest klassist Bean'i ja ta ei tea sellest midagi
@Service
public class BalanceService {

    // See meetod on defineeritud nii, et ta võtab sisse Integer ja Integer tüüpi väärtused
    // See on selleks vajalik, et me saaksime siin meetodis nende väärtustega kuidagi toimetada.
    // See meetod peab tagastama booleani tüüpi vastuse (true/false)
    // Kui meetodite teema on veel endiselt segane, siis palun vaata uuesti "Meetodite loomine" ja "Public ja Private meetodid":
    // https://youtu.be/KtZfO5z_JzQ
    // https://youtu.be/vJn0BuWFrBE
    public boolean enoughMoneyOnAccount(Integer balance, Integer amount) {
        // Võtame accountIdExist() signatuuri parameetris sisse int ja int tüüpi väärtused
        // Siin signatuuris antakse nendele objektidele nimeks 'balance' ja 'amount'
        // tehakse võrdlus, et kas balance on suurem või võrdne kui amount
        // selle tulemuse võrdlus (true/false) pannakse muutujasse 'result'

        // Tagastame RETURN statement'iga 'result' tulemuse
        // Peale return'i minnakse sellest defineeritud enoughMoneyOnAccount() meetodist välja
        boolean result = balance >= amount;

        // Tagastame RETURN statement'iga 'result' tulemuse
        // Peale return'i minnakse sellest defineeritud enoughMoneyOnAccount() meetodist välja
        return result;
    }
}
