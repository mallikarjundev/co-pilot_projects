import model.User;
import service.AuthService;
import util.HashUtil;

import java.util.Scanner;

public class AuthApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    public static void main(String[] args) {
        boolean running = true;
        System.out.println("Welcome to the user Authentication System.");

        while (running){
            printMenu();
            int choice = getInt("Choose an option");

            switch (choice){
                case 1 -> register();
                case 2 -> login();
                case 3 -> authService.printAllUsers();
                case 4 -> resetPassword();
                case 0 -> running=false;
                default -> System.out.println("Invalid option.");
            }
        }

        System.out.println("Thank you!");
    }

    private static void printMenu(){
        System.out.println("\n1. Register");
        System.out.println("2. Login");
        System.out.println("3. View all users");
        System.out.println("4. Reset password");
        System.out.println("0. Exit");
    }

    private static void register(){
        String username = getString("Enter username");
        String password = getString("Enter password");
        String email = getString("Enter email");

        if (authService.register(username,password,email)){
            System.out.println("User Registered");
        }else {
            System.out.println("username already exists");
        }
    }

    private static void login(){
        String username = getString("Enter username");
        String password = getString("Enter password");

        if (authService.login(username,password)){
            System.out.println("User logged in");
        }else {
            System.out.println("invalid username or password");
        }
    }

    private static void resetPassword(){
        String username = getString("Enter username");
        String newPassword = getString("Enter password");
        String email = getString("Enter email");

        if (authService.resetPassword(username,email,newPassword)){
            System.out.println("Password reset complete.");
        }else {
            System.out.println("username/email is invalid.");
        }
    }
    private static String getString(String prompt){
        System.out.println(prompt+" ");
        return sc.nextLine();
    }

    private static int getInt(String prompt){
        while (true){
            try {
                System.out.println(prompt+" ");
                return Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Invalid number.");
            }
        }
    }
}
