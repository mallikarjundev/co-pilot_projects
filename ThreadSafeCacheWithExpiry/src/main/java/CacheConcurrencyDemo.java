import java.util.Random;

public class CacheConcurrencyDemo {
    private static final ThreadSafeCache<String, String> cache = new ThreadSafeCache<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Cache Concurrency Demo ===");

        // Add some initial values
        for (int i = 1; i <= 5; i++) {
            cache.put("key" + i, "value" + i, 5000);
        }

        // Create multiple threads to perform concurrent operations
        Thread writerThread = new Thread(CacheConcurrencyDemo::writerTask,"WriterThread");
        Thread readerThread = new Thread(CacheConcurrencyDemo::readerTask,"ReaderThread");
        Thread removerThread = new Thread(CacheConcurrencyDemo::removerTask,"RemoverThread");

        writerThread.start();
        readerThread.start();
        removerThread.start();

        // Wait for all threads to finish
        writerThread.join();
        readerThread.join();
        removerThread.join();

        System.out.println("\nFinal Cache State:");
        cache.printCache();
        cache.shutdown();
    }

    private static void writerTask(){
        Random random = new Random();
        for (int i=6;i<=10;i++){
            String key = "key" + i;
            String value = "value" + i;
            cache.put(key,value,3000+ random.nextInt(5000));
            System.out.println(Thread.currentThread().getName() + " -> PUT " + key);
            sleep(200);
        }
    }

    private static void readerTask(){
        for (int i=1;i<=10;i++){
            String key = "key" + i;
            String value = cache.get(key);
            System.out.println(Thread.currentThread().getName() + " -> GET " + key + " = " + value);
            sleep(150);
        }
    }

    private static void removerTask(){
        for (int i = 1; i <= 5; i++){
            String key = "key" + i;
            cache.remove(key);
            System.out.println(Thread.currentThread().getName() + " -> REMOVE " + key);
            sleep(300);
        }
    }

    private static void sleep(long millis){
        try {
                Thread.sleep(millis);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
