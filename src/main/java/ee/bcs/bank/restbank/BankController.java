package ee.bcs.bank.restbank;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/solution")
public class BankController {
// midaiganes
    public static Bank bank = new Bank();

    @Resource
    private AccountService accountService;

    @Resource
    private TransactionService transactionService;

    @Resource
    private BankService bankService;

    @GetMapping("/bank")
    public Bank controllerGetBank() {
        return bank;
    }

    @GetMapping("/example/account")
    public AccountDto controllerGetExampleAccount() {
        AccountDto account = accountService.createExampleAccount();
        return account;
    }

    @GetMapping("/example/transaction")
    public TransactionDto controllerGetExampleTransaction() {
        TransactionDto transaction = transactionService.createExampleTransaction();
        return transaction;
    }

    @PostMapping("/new/account")
    public RequestResult controllerAddAccountToBank(@RequestBody AccountDto accountDto) {
        RequestResult result = bankService.addAccountToBank(bank, accountDto);
        return result;
    }

    @PostMapping("/new/transaction")
    public RequestResult controllerAddNewTransaction(@RequestBody TransactionDto transactionDto) {
        RequestResult result = transactionService.addNewTransaction(bank, transactionDto);
        return result;
    }

    @PostMapping("/receive/transaction")
    public RequestResult controllerReceiveNewTransaction(@RequestBody TransactionDto transactionDto) {
        RequestResult result = transactionService.receiveNewTransaction(bank, transactionDto);
        return result;
    }

    @PutMapping("/update/owner")
    public RequestResult controllerUpdateOwnerDetails(@RequestBody AccountDto accountDto) {
        RequestResult result = accountService.updateOwnerDetails(bank.getAccounts(), accountDto);
        return result;
    }

    @DeleteMapping("/delete/account")
    public RequestResult controllerDeleteAccount(@RequestParam int accountId) {
        RequestResult result = accountService.deleteAccount(bank.getAccounts(), accountId);
        return result;
    }

    @PutMapping("/lock/status")
    public RequestResult controllerSwitchLockStatus(int accountId) {
        RequestResult result = accountService.switchLockStatus(bank.getAccounts(), accountId);
        return result;
    }


//    todo: Loo endpoint /bankstatement/by/lastname
    // KONTO VÄLJAVÕTE (PEREKONNA NIME JÄRGI)
    // tee kõigepealt üks public meetod controllerBankStatementByName()
    // Meetod peaks tagastama List<TransactionDto> tüüpi objekti (kannetest/tehingutest)
    // Ära hakka kohe Controlleri annotatsioonidele mõtlema. Tee alustuseks lihtsalt üks public meetod valmis. Võid alguses panna "return null
    // Kui mingi meetodi põhi on valmis (võib olla täitsa igasuguse sisuta meetod),
    // siis hakka mõtlema controlleri mäppimise peale, et millise millise HTTP meetodiga võiks/peaks selline sõnum sisse tulema
    // Lisa raja definitsioon "/bankstatement/by/lastname"
    // Teades, et sisendiks on vaid üks String tüüpi sisend (perekonna nimi),
    // siis mõtle sellele, et millist sisendi sisse saamise lähenemist oleks kõige mõistlikum kasutada:
    // PathVariable,
    // RequestParam või
    // RequestBody

    // vihjeks nii palju, et seoses meie andmete hoidmise struktuuriga oleks sul vaja teha minimaalselt kaks eraldi tegevust/etappi
    // 1) oleks vaja leida õige konto ID, kasutades perekonna nime.
    // 2) oleks vaja siis leida selle ID järgi bank objektist kõik need transactionid, mis on selle ID'ga seotud.
    // Mõtle sellele, et kas mõnes service klassis on juba olemas mõni teenus mis võiks sind kuidagi aidata.
    // Mõtle sellele, et kas oleks vaja mingit funktsionaalsust juurde ehitada? Kui jah siis, kus service klassis see võiks elada...
    // Kui hakkad mingit uut meetodit tegema ja defineerima,
    // siis võid alguses teha need void tüüpi ja siis pärast hiljem ümber muuta, et mida nad võiks tagastada. Vahel on nii lihtsam kusagilt alustada.
    // Aga kui sa isegi tead, et mis tüüpi objekti või objektide listi see meetod võiks tagastada, siis alguses võid vabalt panna ikkagi return null;
    // Saad hiljem kõike muuta.
    // Võid alguses ka kogu pikalt kirja pandud loogika controllerBankStatementByName() meetodi sees ära lahendada ning hiljem mõelda,
    // et kus miski võiks elada ja kuhu võiks äkki mingi meetodi teha.
    // Kui lood mingeid meetodeid, mis tagastavad midagi, siis tagasta meetodi tulemused kuhugi muutujasse ning
    // pane muutuja nimeks midagi, mis sulle ütleb täpselt, et mis asjad seal muutujas siis täpselt ka on.
    // Võid alguses isegi eesti keeles teha kui tahad. Kõike saab hiljem re-faktoreerida.
    // Kui kõik on kenasti valmis ja töötab, siis mõtle, et kas tahaksid äkki proovida teha mingi täitsa uue service klassi,
    // et saaksid kogu tegevuse kuhugi kenasti ühte kohta panna (näiteks BankStatementService või midagi sarnast).


    // kuidas saab mingist suuremast listist mingi väiksema listi teha, võttes sealt vaid neid objekte, mis pakuvad huvi:
//    public List<SomeClass> getListOfItemsFromExistingListThatImInterested(List<SomeClass> someItems, int someId) {
//        List<SomeClass> result = new ArrayList<>();
//        for (SomeClass someItem : someItems) {
//            if (someItem.getId() == someId) {
//                result.add(someItem);
//            }
//        }
//        return result;
//    }

}
