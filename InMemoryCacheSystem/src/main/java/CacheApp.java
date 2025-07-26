import java.util.Scanner;

public class CacheApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter max cache size (for LRU): ");
        int maxSize = Integer.parseInt(sc.nextLine());

        Cache<String,String> cache = new Cache<>(maxSize);
        boolean running = true;

        System.out.println("🧠 In-Memory Cache with TTL + LRU Eviction");

        while (running){
            System.out.println("\n1. Put  2. Get  3. Remove  4. Size  0. Exit");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice){
                case 1 -> {
                    System.out.print("Enter key: ");
                    String key = sc.nextLine();
                    System.out.print("Enter value: ");
                    String value = sc.nextLine();
                    System.out.print("Enter TTL (ms): ");
                    long ttl = Long.parseLong(sc.nextLine());
                    cache.put(key,value,ttl);
                    System.out.println("✅ Stored in cache.");
                }
                case 2 -> {
                    System.out.print("Enter key: ");
                    String key = sc.nextLine();
                    String val = cache.get(key);
                    System.out.println(val!=null?"🔍 Value: " + val : "⚠️ Not found or expired.");
                }
                case 3 -> {
                    System.out.print("Enter key to remove: ");
                    cache.remove(sc.nextLine());
                }
                case 4 -> {
                    System.out.println("Cache Size: "+cache.size());
                }
                case 0 -> {
                    running=false;
                    cache.shutdown();
                }
                default -> System.out.println("Invalid choice.");

            }
        }
        System.out.println("👋 Cache shutdown.");
    }
}
