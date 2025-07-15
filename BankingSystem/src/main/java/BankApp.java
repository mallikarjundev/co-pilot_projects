import java.util.List;
import java.util.Scanner;

public class BankApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final BankService bankService = new BankService();

    public static void main(String[] args) {

        InterestScheduler scheduler = new InterestScheduler(bankService);
        scheduler.start();

        boolean running = true;
        System.out.println("\nWelcome to Simple Banking System");

        while (running){
            printMenu();
            int choice = getInt("Choose an option:");
            switch (choice){
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transfer();
                case 5 -> viewAccount();
                case 6 -> listAllAccounts();
                case 7 -> showTransactions();
                case 8 -> showMiniStatement();
                case 0 -> {
                    running = false;
                    scheduler.stop();
                    bankService.saveData();
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        System.out.println("Thank you for using the Banking System!");
    }

    private static void printMenu(){
        System.out.println("\n========= MENU =========");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. View Account Details");
        System.out.println("6. List All Accounts");
        System.out.println("7. View Transactions");
        System.out.println("8. Mini Statement (last 5 transactions)");
        System.out.println("0. Exit");
    }

    private static void createAccount(){
        String id = getString("Enter Account ID:");
        String name = getString("Enter Account Holder Name");
        double initial = getDouble("Enter initial deposit:");
        String typePrompt="";

        System.out.println("Select Account type:");
        for (AccountType type: AccountType.values()){
            System.out.println("-"+type);
            typePrompt=type+" ";
        }

        String typeInput = getString("Enter account type: ");
        AccountType type = AccountType.valueOf(typeInput.toUpperCase());
        if (bankService.createAccount(id,name,initial,type)){
            System.out.println("Account Created.");
        }else {
            System.out.println("Account ID already exists.");
        }
    }

    private static void deposit(){
        String id = getString("Enter Account ID:");
        double deposit = getDouble("Enter amount to deposit:");
        if (bankService.deposit(id,deposit)){
            System.out.println("Deposit successful.");
        }else {
            System.out.println("Deposit failed.");
        }
    }

    private static void withdraw(){
        String id = getString("Enter Account ID:");
        double withdraw = getDouble("Enter amount to withdraw:");
        if (bankService.deposit(id,withdraw)){
            System.out.println("withdraw successful.");
        }else {
            System.out.println("withdraw failed.");
        }
    }

    private static void transfer(){
        String from = getString("Enter Sender Account ID:");
        String to = getString("Enter Receiver Account ID:");
        double transfer = getDouble("Enter amount to transfer:");
        if (bankService.transfer(from, to,transfer)){
            System.out.println("transfer successful.");
        }else {
            System.out.println("transfer failed.");
        }
    }

    private static void viewAccount(){
        String id = getString("Enter Account ID:");
        Account account = bankService.getAccount(id);
        if (account!=null){
            System.out.println("Account details: "+account);
        }else {
            System.out.println("Account not found.");
        }
    }

    private static void listAllAccounts(){
        for (Account acc: bankService.getAllAccounts()){
            System.out.println(acc);
        }
    }

    private static void showTransactions(){
        for (Transaction t: bankService.getAllTransactions()){
            System.out.println(t);
        }
    }

    private static void showMiniStatement(){
        String id = getString("Enter Account id:");
        List<Transaction> mini = bankService.getMiniStatement(id,5);
        if (mini.isEmpty()){
            System.out.println("No recent transactions found.");
            return;
        }

        System.out.println("Last 5 transactions for Account: "+id);
        for (Transaction t: mini){
            System.out.println(t);
        }
    }

    private static int getInt(String prompt){
        while (true){
            try {
                System.out.println(prompt);
                return Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static String getString(String prompt){
        System.out.println(prompt);
        return sc.nextLine();
    }

    private static double getDouble(String prompt){
        while (true){
            try {
                System.out.println(prompt);
                return Double.parseDouble(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}
