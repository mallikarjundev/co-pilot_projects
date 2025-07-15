import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cache<K,V> {
    private final ConcurrentHashMap<K,CacheEntry<V>> store = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    public Cache(){
        startCleaner();
    }

    public void put(K key, V value, long ttlMillis){
        store.put(key,new CacheEntry<>(value,ttlMillis));
    }

    public V get(K key){
        CacheEntry<V> entry = store.get(key);
        if (entry==null || entry.isExpired()){
            store.remove(key);
            return null;
        }

        return entry.getValue();
    }

    public void remove(K key){
        store.remove(key);
    }

    public void clear(){
        store.clear();
    }

    public int size(){
        return store.size();
    }

    private void startCleaner(){
        cleaner.scheduleAtFixedRate(()->{
            for (Map.Entry<K,CacheEntry<V>> entry:store.entrySet()){
                if (entry.getValue().isExpired()){
                    store.remove(entry.getKey());
                    System.out.println("Removed expired key: "+entry.getKey());
                }
            }
        },5,5, TimeUnit.SECONDS);
    }

    public void shutdown(){
        cleaner.shutdown();
    }
}
