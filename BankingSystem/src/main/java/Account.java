import java.io.Serializable;

public class Account implements Serializable {
    private String accountId;
    private String accountHolder;
    private double balance;
    private AccountType accountType;

    public Account(String accountId, String accountHolder, double initialBalance, AccountType type) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.accountType = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount){
        balance+=amount;
    }

    public void withdraw(double amount){
        balance-=amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", accountHolder='" + accountHolder + '\'' +
                ", balance=" + balance +
                ", type=" + accountType +
                '}';
    }
}
