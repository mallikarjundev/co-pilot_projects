import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ProductCatalog {
    private final Map<String, Double> products = new HashMap<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void addOrUpdateProduct(String product, double price){
        rwLock.writeLock().lock();
        try {
            products.put(product,price);
            System.out.println(Thread.currentThread().getName()+" -> Updated: "+product+" = "+price);
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public Double getPrice(String product){
        rwLock.readLock().lock();
        try {
            return products.get(product);
        }finally {
            rwLock.readLock().unlock();
        }
    }

    public void printAllProducts(){
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+" -> Product List: "+products);
        }finally {
            rwLock.readLock().unlock();
        }
    }
}
