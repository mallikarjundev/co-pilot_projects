import java.util.concurrent.Semaphore;

class ConnectionPool{
    private final Semaphore semaphore;

    public ConnectionPool(int maxConnections) {
        this.semaphore = new Semaphore(maxConnections);
    }

    public void acquireConnection(String threadName){
        try {
            System.out.println(threadName+" waiting for DB connection...");
            semaphore.acquire();
            System.out.println(threadName+" acquired DB connection...");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            System.out.println(threadName + " releasing DB connection.");
            semaphore.release();
        }
    }
}

public class ConnectionPoolSimulator {
    public static void main(String[] args) {
        ConnectionPool pool = new ConnectionPool(3);
        for (int i = 1; i <= 8; i++) {
            final int id = i;
            new Thread(() -> pool.acquireConnection("Thread-" + id)).start();
        }
    }
}
