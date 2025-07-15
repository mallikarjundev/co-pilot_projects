import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterestScheduler {

    private final BankService bankService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public InterestScheduler(BankService bankService){
        this.bankService=bankService;
    }

    public void start(){
        scheduler.scheduleAtFixedRate(this::applyInterestToAllAccounts,0,15, TimeUnit.SECONDS);
    }

    public void stop(){
        scheduler.shutdown();
    }

    private void applyInterestToAllAccounts(){
//        System.out.println("\n[InterestScheduler] Applying interest to all accounts...");
        for (Account account: bankService.getAllAccounts()){
            double balance = account.getBalance();
            AccountType type = account.getAccountType();
            double rate = type.getAnnualInterestRate();
            // Assume daily interest for simplicity: annual rate / 365
            double interest = Math.floor(balance*(rate/365)*100)/100.0;

            if (interest>0){
                bankService.depositInterest(account.getAccountId(), interest);
//                System.out.printf("Interest of $%.2f applied to account %s (%s)\n", interest,account.getAccountId(),type);
            }
        }
    }
}
