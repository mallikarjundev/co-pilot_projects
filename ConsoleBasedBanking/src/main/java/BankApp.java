import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)) {
            Bank bank = new Bank();
            boolean running = true;

            System.out.println("Welcome to Bank App");

            while (running){
                System.out.println("\n Choose an option:");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Check Balance");
                System.out.println("5. View Account Details");
                System.out.println("6. Exit");
                System.out.println("Enter Choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice){
                    case 1:
                        System.out.println("Enter account holder's name: ");
                        String name = sc.nextLine();
                        String accNum = bank.createAccount(name);
                        System.out.println("Account created. Account Number: "+accNum);
                        break;
                    case 2:
                        System.out.println("Enter account number: ");
                        String depAcc = sc.nextLine();
                        System.out.println("Enter amount to deposit: ");
                        double depAmt = sc.nextDouble();
                        sc.nextLine();
                        if (bank.depost(depAcc,depAmt)){
                            System.out.println("Account deposited.");
                        }else {
                            System.out.println("Account not found.");
                        }
                        break;
                    case 3:
                        System.out.println("Enter account number: ");
                        String witAcc = sc.nextLine();
                        System.out.println("Enter amount to withdraw: ");
                        double witAmt = sc.nextDouble();
                        sc.nextLine();
                        if (bank.withdraw(witAcc,witAmt)){
                            System.out.println("Account withdrawn.");
                        }else {
                            System.out.println("Insufficient balance or invalid Account.");
                        }
                        break;
                    case 4:
                        System.out.println("Enter account number: ");
                        String balAcc = sc.nextLine();
                        Double bal = bank.checkBalance(balAcc);
                        if (bal!=null){
                            System.out.println("Current Balance: $"+String.format("%.2f",bal));
                        }else {
                            System.out.println("Account not found.");
                        }
                        break;
                    case 5:
                        System.out.println("Enter account number: ");
                        String viewAcc = sc.nextLine();
                        Account acc = bank.getAccount(viewAcc);
                        if (acc!=null){
                            System.out.println(acc);
                        }else {
                            System.out.println("Account not found.");
                        }
                        break;
                    case 6:
                        System.out.println("Thank you for banking with us!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        }
    }
}

