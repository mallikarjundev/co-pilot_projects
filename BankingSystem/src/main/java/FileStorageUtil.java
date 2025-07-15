import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileStorageUtil {
    public static void saveAccounts(Map<String,Account> accounts, String fileName){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Failed to save accounts: "+e.getMessage());
        }
    }

    public static Map<String, Account> loadAccounts(String fileName){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, Account>) in.readObject();
        }catch (Exception e) {
            System.out.println("Failed to load accounts: "+e.getMessage());
            return new HashMap<>();
        }
    }

    public static void saveTransaction(List<Transaction> trans, String fileName){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(trans);
        } catch (IOException e) {
            System.out.println("Failed to save transactions: "+e.getMessage());
        }
    }

    public static List<Transaction> loadTransactions(String fileName){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Transaction>)in.readObject();
        }catch (Exception e) {
            System.out.println("Failed to load transactions: "+e.getMessage());
            return new ArrayList<>();
        }

    }
}
