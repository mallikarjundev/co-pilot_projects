import java.util.Random;

public class ProductCatalogDemo {
    public static void main(String[] args) throws InterruptedException {
        ProductCatalog catalog = new ProductCatalog();
        Random random = new Random();

        // Writer thread - updates product prices
        Thread writer = new Thread(()->{
            for (int i = 1; i <= 5; i++){
                String product = "Product-"+i;
                double price = 10 + random.nextInt(90);
                catalog.addOrUpdateProduct(product,price);
                sleep(300);
            }
        },"WriterThread");

        // Multiple reader threads
        Runnable readerTask = ()->{
            for (int i=1;i<=5;i++){
                String product = "Product-"+i;
                Double price = catalog.getPrice(product);
                System.out.println(Thread.currentThread().getName()+" -> Read: "+product+" = "+price);
                sleep(200);
            }
        };

        Thread reader1 = new Thread(readerTask,"ReaderThreader-1");
        Thread reader2 = new Thread(readerTask,"ReaderThreader-2");

        // Start threads
        writer.start();
        reader1.start();
        reader2.start();

        // Wait for all to finish
        writer.join();
        reader1.join();
        reader2.join();

        System.out.println("\nFinal Products:");
        catalog.printAllProducts();
    }

    private static void sleep(long millis){
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
