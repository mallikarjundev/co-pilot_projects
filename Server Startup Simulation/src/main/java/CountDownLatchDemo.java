import java.util.concurrent.CountDownLatch;

class Service implements Runnable{

    private final String name;
    private final int startupTime;
    private final CountDownLatch latch;

    public Service(String name, int startupTime, CountDownLatch latch) {
        this.name = name;
        this.startupTime = startupTime;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name+" starting....");
            Thread.sleep(startupTime);
            System.out.println(name+" is UP");
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            latch.countDown();
        }
    }
}

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int serviceCount=3;
        CountDownLatch latch = new CountDownLatch(serviceCount);

        new Thread(new Service("Database Service",2000,latch)).start();
        new Thread(new Service("Cache Service",1000,latch)).start();
        new Thread(new Service("Web Server",3000,latch)).start();

        System.out.println("Main thread waiting for all services to start...");
        latch.await(); // wait until count = 0

        System.out.println("All services are UP. Application Started!");
    }
}
