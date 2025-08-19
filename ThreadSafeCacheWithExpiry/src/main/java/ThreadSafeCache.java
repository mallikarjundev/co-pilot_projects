import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadSafeCache<K,V> {
    private final ConcurrentHashMap<K, CacheValue<V>> cacheMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor();

    public ThreadSafeCache(){
        startCleaner();
    }

    public void put(K key, V value, long ttlMillis){
        long expiryTime = System.currentTimeMillis()+ttlMillis;
        cacheMap.put(key,new CacheValue<>(value,expiryTime));
    }

    public V get(K key){
        CacheValue<V> cacheValue = cacheMap.get(key);
        if (cacheValue==null){
            return null;
        }

        if (cacheValue.isExpired()){
            cacheMap.remove(key);
            return null;
        }

        return cacheValue.getValue();
    }

    public void remove(K key){
        cacheMap.remove(key);
    }

    private void startCleaner(){
        cleaner.scheduleAtFixedRate(()->{
            long now = System.currentTimeMillis();
            Iterator<Map.Entry<K, CacheValue<V>>> it = cacheMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<K,CacheValue<V>> entry = it.next();
                if (entry.getValue().getExpiryTime()<now){
                    it.remove();
                }
            }
        },5,5, TimeUnit.SECONDS);
    }

    public void shutdown(){
        cleaner.shutdown();
        try {
            if (!cleaner.awaitTermination(1, TimeUnit.SECONDS)) { // Give it a short time to finish
                cleaner.shutdownNow(); // Force shutdown if it doesn't stop
            }
        } catch (InterruptedException e) {
            cleaner.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void printCache(){
        if (cacheMap.isEmpty()){
            System.out.println("[Cache is Empty]");
        }else {
            cacheMap.forEach((k,v)->{
                System.out.println(k+" => "+v.getValue()+
                        " (expires in "+(v.getExpiryTime()-System.currentTimeMillis())+" ms)");
            });
        }
    }
}
