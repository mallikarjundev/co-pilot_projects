import java.util.*;

public class BankService {

    private static final double LOW_BALANCE_THRESHOLD = 100.0;
    private static final double LARGE_TRANSACTION_AMOUNT = 600.0;

    private Map<String, Account> accounts = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();

    private static final String Accounts_file = "/Users/arjunoggu/IdeaProjects/pilot_projects/BankingSystem/src/main/files/account.dat";
    private static final String Transactions_file="/Users/arjunoggu/IdeaProjects/pilot_projects/BankingSystem/src/main/files/transaction.dat";

    public BankService(){
        this.accounts = FileStorageUtil.loadAccounts(Accounts_file);
        this.transactions = FileStorageUtil.loadTransactions(Transactions_file);
    }

    public boolean createAccount(String id, String name, double initialDeposit, AccountType type){
        if (accounts.containsKey(id)) return false;
        accounts.put(id, new Account(id,name,initialDeposit, type));
        transactions.add(new Transaction(id,"Create",initialDeposit,"Initial Deposit"));
        return true;
    }

    public boolean deposit(String id, double amount){
        Account acc = accounts.get(id);
        if (acc==null || amount<=0) return false;
        acc.deposit(amount);
        transactions.add(new Transaction(id,"Deposit",amount,""));

        if (amount>=LARGE_TRANSACTION_AMOUNT){
            transactions.add(new Transaction(id,"Alert:",amount,"$ Large amount is deposited"));
        }
        return true;
    }

    public boolean depositInterest(String id, double amount){
        Account acc = accounts.get(id);
        if (acc==null || amount<=0) return false;
        acc.deposit(amount);
        transactions.add(new Transaction(id,"Interest",amount,"Scheduled Interest"));
        return true;
    }

    public boolean withdraw(String id, double amount){
        Account acc = accounts.get(id);
        if (acc==null || amount<=0 || acc.getBalance()<amount) return false;
        acc.withdraw(amount);
        transactions.add(new Transaction(id,"Withdraw",amount,""));

        if (amount>=LARGE_TRANSACTION_AMOUNT){
            transactions.add(new Transaction(id,"Alert:",amount,"$ Large amount is withdrawn"));
        }

        if (acc.getBalance()<LOW_BALANCE_THRESHOLD){
            transactions.add(new Transaction(id,"Warning:",amount,"$ is below threshold limit"));
        }

        return true;
    }

    public boolean transfer(String fromId, String toId, double amount){
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from==null || to==null || amount<=0 || from.getBalance()<amount) return false;
        from.withdraw(amount);
        to.deposit(amount);
        transactions.add(new Transaction(fromId,"Transfer-Out",amount,"To: "+toId));
        transactions.add(new Transaction(toId,"Transfer-In",amount,"From: "+fromId));

        return true;
    }

    public Account getAccount(String id){
        return accounts.get(id);
    }

    public Collection<Account> getAllAccounts(){
        return accounts.values();
    }

    public List<Transaction> getAllTransactions(){
        return transactions;
    }

    public List<Transaction> getMiniStatement(String accId, int limit){
        List<Transaction> mini = new ArrayList<>();
        for (int i= transactions.size()-1;i>=0 && mini.size()<limit;i--){
            Transaction t = transactions.get(i);
            if (t.getAccountId().equals(accId)){
                mini.add(t);
            }
        }

        Collections.reverse(mini);
        return mini;
    }

    public void saveData(){
        FileStorageUtil.saveAccounts(accounts,Accounts_file);
        FileStorageUtil.saveTransaction(transactions,Transactions_file);
    }
}
