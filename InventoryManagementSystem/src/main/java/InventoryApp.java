import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class InventoryApp {
    private static final InventoryManager manager = new InventoryManager();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("\nWelcome to Inventory Management System!");

        while (running){
            printMenu();
            int choice = getIntInput("Choose an option:");
            switch (choice){
                case 1 -> addProduct();
                case 2 -> updateQuantity();
                case 3 -> updatePrice();
                case 4 -> removeProduct();
                case 5 -> searchByName();
                case 6 -> searchByCategory();
                case 7 -> displayAllSortedByName();
                case 8 -> displayAllSortedByQuantity();
                case 9 -> displayAll();
                case 0 -> running=false;
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        System.out.println("\nThank you for using Inventory Management System!");
    }

    private static void printMenu(){
        System.out.println("\n======== Menu =======");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product Quantity");
        System.out.println("3. Update Product Price");
        System.out.println("4. Remove Product");
        System.out.println("5. Search Product by Name");
        System.out.println("6. Search Product by Category");
        System.out.println("7. Display All Products (Sorted by Name)");
        System.out.println("8. Display All Products (Sorted by Quantity)");
        System.out.println("9. Display All Products");
        System.out.println("0. Exit");
    }

    private static void addProduct(){
        System.out.println("\n---- Add Product ----");
        String id = getStringInput("Enter product id: ");
        String name = getStringInput("Enter product name: ");
        String category = getStringInput("Enter product category: ");
        double price = getDoubleInput("Enter price: ");
        int quantity = getIntInput("Enter quantity: ");

        Product product = new Product(id,name,category,price,quantity);
        if (manager.addProduct(product)){
            System.out.println("Product added successfully.");
        }else {
            System.out.println("Product ID already exists!");
        }
    }

    private static void updateQuantity(){
        String id = getStringInput("Enter product id: ");
        int quantity = getIntInput("Enter quantity: ");
        if (manager.updateProductQuantity(id,quantity)){
            System.out.println("Quantity Updated.");
        }else {
            System.out.println("Product not found.");
        }
    }

    private static void updatePrice(){
        String id = getStringInput("Enter product id: ");
        double price = getDoubleInput("Enter price: ");
        if (manager.updateProductPrice(id,price)){
            System.out.println("Price Updated.");
        }else {
            System.out.println("Product not found.");
        }
    }

    private static void removeProduct(){
        String id = getStringInput("Enter product id: ");
        if (manager.removeProduct(id)){
            System.out.println("Product removed.");
        }else {
            System.out.println("Product not found.");
        }
    }

    private static void searchByName(){
        String keyword = getStringInput("Enter keyword to search by name: ");
        List<Product> products = manager.searchByName(keyword);
        displayProducts(products);
    }

    private static void searchByCategory(){
        String category = getStringInput("Enter category to search: ");
        List<Product> products = manager.searchByCategory(category);
        displayProducts(products);
    }

    private static void displayAllSortedByName(){
        List<Product> products = manager.getAllProductsSortedByName();
        displayProducts(products);
    }

    private static void displayAllSortedByQuantity(){
        List<Product> products = manager.getAllProductsSortedByQuantity();
        displayProducts(products);
    }

    private static void displayAll(){
        displayProducts(manager.getAllProducts());
    }

    private static void displayProducts(Collection<Product> products){
        if (products.isEmpty()){
            System.out.println("No products found.");
            return;
        }
        System.out.println("\n----- Products List ----");
        for (Product product: products){
            System.out.println(product);
        }
    }

    private static int getIntInput(String prompt){
        while (true){
            try {
                System.out.println(prompt);
                return Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static double getDoubleInput(String prompt){
        while (true){
            try {
                System.out.println(prompt);
                return Double.parseDouble(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static String getStringInput(String prompt){
        System.out.println(prompt);
        return sc.nextLine();
    }
}
