import java.util.concurrent.Semaphore;

class NonBlockingSemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1); // only 1 resource

        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            if (semaphore.tryAcquire()) {
                try {
                    System.out.println(name + " acquired permit.");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.println(name + " releasing permit.");
                    semaphore.release();
                }
            } else {
                System.out.println(name + " could not acquire permit, skipping work.");
            }
        };

        for (int i = 1; i <= 3; i++) {
            new Thread(task, "Thread-" + i).start();
        }
    }
}