import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProductCatalogStressTest {
    public static void main(String[] args) throws InterruptedException {
        ProductCatalog catalog = new ProductCatalog();
        Random random = new Random();

        int numReaders = 10;
        int numWriters = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numReaders+numWriters);

        long start = System.currentTimeMillis();

        // Writer Tasks
        for (int i=0;i<numWriters;i++){
            executor.submit(()->{
                for (int j=0;j<20;j++){
                    String product = "Product-"+(random.nextInt(20)+1);
                    double price = 10+ random.nextInt(90);
                    catalog.addOrUpdateProduct(product,price);
                    sleep(50);
                }
            });
        }

        // Reader tasks
        for (int i=0;i<numReaders;i++){
            executor.submit(()->{
                for (int j=0;j<50;j++){
                    String product = "Product-" + (random.nextInt(20) + 1);
                    Double price = catalog.getPrice(product);
                    System.out.println(Thread.currentThread().getName() +
                            " -> Read: " + product + " = " + price);
                    sleep(20);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        long end = System.currentTimeMillis();
        System.out.println("\nFinal Products:");
        catalog.printAllProducts();
        System.out.println("\nExecution Time: " + (end - start) + " ms");
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
