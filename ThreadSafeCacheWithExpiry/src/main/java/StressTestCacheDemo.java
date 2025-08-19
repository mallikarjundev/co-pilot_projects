import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StressTestCacheDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadSafeCache<String, String> cache = new ThreadSafeCache<>();
        Random random = new Random();

        int totalThreads = 15; // 10+ threads
        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < totalThreads; i++) {
            final int threadType = i % 3; // rotate between writer, reader, remover
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 20; j++) {
                        if (Thread.currentThread().isInterrupted()) {
                            System.out.println(Thread.currentThread().getName() + " interrupted, stopping early...");
                            return;
                        }
                        int keyId = random.nextInt(10) + 1;
                        String key = "key" + keyId;

                        switch (threadType) {
                            case 0 -> { // Writer
                                String value = "value" + keyId;
                                cache.put(key, value, 5000);
                                System.out.println(Thread.currentThread().getName() + " -> PUT " + key + "=" + value);
                            }
                            case 1 -> { // Reader
                                String value = cache.get(key);
                                System.out.println(Thread.currentThread().getName() + " -> GET " + key + " = " + value);
                            }
                            case 2 -> { // Remover
                                cache.remove(key);
                                System.out.println(Thread.currentThread().getName() + " -> REMOVE " + key);
                            }
                        }
                        Thread.sleep(random.nextInt(100));
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted while sleeping, stopping...");
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Tasks did not terminate in 5 seconds. Attempting forceful shutdown...");
                executor.shutdownNow(); // Attempt to cancel actively executing tasks
                // Give a little more time for shutdownNow to take effect, especially if tasks are
                // slow to respond to interruption.
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate!");
                }
            }
        } catch (InterruptedException ie) {
            System.err.println("Main thread interrupted while waiting for executor termination.");
            executor.shutdownNow();
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\n=== Final Cache State ===");
        cache.printCache();
        System.out.println("\nExecution time: " + (endTime - startTime) + " ms");
        cache.shutdown();
    }
}