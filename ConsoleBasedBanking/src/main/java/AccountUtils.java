import java.util.UUID;

public class AccountUtils {
    public static String generateAccountNumber(){
        return UUID.randomUUID().toString().substring(0,8);
    }
}
