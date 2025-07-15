import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction  implements Serializable {

    private String accountId;
    private String type; //Deposit, Withdraw, Transfer
    private double amount;
    private LocalDateTime timestamp;
    private String note;

    public Transaction(String accountId, String type, double amount, String note) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp=LocalDateTime.now();
        this.note = note;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return timestamp + " | " + type + " | " + amount + " | " + accountId + " | " + note;
    }
}
