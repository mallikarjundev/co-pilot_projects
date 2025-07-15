import java.util.*;

public class InventoryManager {
    private Map<String, Product> inventory = new HashMap<>();

    public boolean addProduct(Product product){
        if (inventory.containsKey(product.getId())){
            return false;
        }
        inventory.put(product.getId(), product);
        return true;
    }

    public boolean updateProductQuantity(String productId, int newQuantity){
        Product product = inventory.get(productId);
        if (product!=null){
            product.setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    public boolean updateProductPrice(String productId, double newPrice){
        Product product = inventory.get(productId);
        if (product!=null){
            product.setPrice(newPrice);
            return true;
        }
        return false;
    }

    public boolean removeProduct(String productId){
        return inventory.remove(productId)!=null;
    }

    public List<Product> searchByName(String keyword){
        keyword = keyword.toLowerCase();
        List<Product> results = new ArrayList<>();
        for (Product product: inventory.values()){
            if (product.getName().toLowerCase().contains(keyword)){
                results.add(product);
            }
        }
        return results;
    }

    public List<Product> searchByCategory(String category){
        category = category.toLowerCase();
        List<Product> results = new ArrayList<>();
        for (Product product: inventory.values()){
            if (product.getCategory().toLowerCase().contains(category)){
                results.add(product);
            }
        }
        return results;
    }

    public List<Product> getAllProductsSortedByName(){
        List<Product> products = new ArrayList<>(inventory.values());
        products.sort(Comparator.comparing(Product::getName));
        return products;
    }

    public List<Product> getAllProductsSortedByQuantity(){
        List<Product> products = new ArrayList<>(inventory.values());
        products.sort(Comparator.comparingInt(Product::getQuantity));
        return products;
    }

    public Collection<Product> getAllProducts(){
        return inventory.values();
    }
}
