import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts= new HashMap<>();

    public String createAccount(String holderName){
        String accountNumber = AccountUtils.generateAccountNumber();
        Account account = new Account(accountNumber,holderName);
        accounts.put(accountNumber,account);
        return accountNumber;
    }

    public boolean depost(String accountNumber, double amount){
        Account account = accounts.get(accountNumber);
        if (account!=null){
            account.deposit(amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(String accountNumber, double amount){
        Account account = accounts.get(accountNumber);
        if (account!=null){
            return  account.withdraw(amount);
        }
        return false;
    }

    public Double checkBalance(String accountNumber){
        Account account = accounts.get(accountNumber);
        return (account!=null)? account.getBalance() : null;
    }

    public Account getAccount(String accountNumber){
        return accounts.get(accountNumber);
    }

    public boolean accountExists(String accountNumber){
        return accounts.containsKey(accountNumber);
    }
}
