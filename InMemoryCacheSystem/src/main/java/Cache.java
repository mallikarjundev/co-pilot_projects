import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cache<K,V> {
    private final int maxCapacity;
    private final Map<K,CacheEntry<V>> store;
    private final Timer cleanerTimer;

    public Cache(int maxCapacity){

        this.maxCapacity=maxCapacity;
        this.store = new LinkedHashMap<>(16,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<V>> eldest) {
                return size()>Cache.this.maxCapacity;
            }
        };
        this.cleanerTimer=new Timer(true);
        startCleaner();
    }



    public synchronized void put(K key, V value, long ttlMillis){
        store.put(key,new CacheEntry<>(value,ttlMillis));
    }

    public synchronized V get(K key){
        CacheEntry<V> entry = store.get(key);
        if (entry==null || entry.isExpired()){
            store.remove(key);
            return null;
        }

        return entry.getValue();
    }

    public synchronized void remove(K key){
        store.remove(key);
    }

    public synchronized void clear(){
        store.clear();
    }

    public int size(){
        return store.size();
    }

    private void startCleaner(){
        cleanerTimer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                synchronized (Cache.this){
                    Iterator<Map.Entry<K,CacheEntry<V>>> iterator = store.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<K,CacheEntry<V>> entry = iterator.next();
                        if (entry.getValue().isExpired()){
                            iterator.remove();
                            System.out.println("Removed expired key: "+entry.getKey());
                        }
                    }
                }
            }

        },5000,5000);
    }

    public void shutdown(){
        cleanerTimer.cancel();
    }
}
