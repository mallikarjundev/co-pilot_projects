public class CacheValue<V> {
    private final V value;
    private final long expiryTime; // timestamp in milliseconds

    public CacheValue(V value, long expiryTime){
        this.value = value;
        this.expiryTime = expiryTime;
    }

    public V getValue(){
        return value;
    }

    public long getExpiryTime(){
        return expiryTime;
    }

    public boolean isExpired(){
        return System.currentTimeMillis()>expiryTime;
    }
}
