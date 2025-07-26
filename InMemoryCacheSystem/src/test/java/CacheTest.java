import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {

    private Cache<String,String> cache;

    @BeforeEach
    void setup(){
        cache = new Cache<>(3);
    }

    @AfterEach
    void tearDown(){
        cache.shutdown();
    }

    @Test
    void testPutAndGet(){
        cache.put("user1","Arjun",10000);
        assertEquals("Arjun",cache.get("user1"));
    }

    @Test
    void testExpiration() throws InterruptedException{
        cache.put("key2", "value2", 100);
        Thread.sleep(200);
        assertNull(cache.get("key2"));
    }

    @Test
    void testLRUEviction(){
        cache.put("a", "1", 5000);
        cache.put("b", "2", 5000);
        cache.put("c", "3", 5000);
        cache.get("a");
        cache.put("d","4",5000);
        assertNull(cache.get("b"));
        assertNotNull(cache.get("a"));
    }


}
