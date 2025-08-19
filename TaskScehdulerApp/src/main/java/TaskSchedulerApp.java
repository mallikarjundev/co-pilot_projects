import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskSchedulerApp {
    public static void main(String[] args) throws InterruptedException{
        // Step 1: Create Scheduler
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Step 2: Schedule one-time reminder after 3 seconds
        scheduler.schedule(new ReminderTask("Meeting at 3 PM"),3, TimeUnit.SECONDS);

        // Step 3: Schedule periodic reminder (every 5 seconds)
        scheduler.scheduleAtFixedRate(new ReminderTask("Drink water!"),1,5,TimeUnit.SECONDS);

        // Step 4: Schedule cleanup with fixed delay (runs every 4 sec after task finishes)
        scheduler.scheduleWithFixedDelay(new CleanupTask(),2,4, TimeUnit.SECONDS);

        Thread.sleep(20000);
        scheduler.shutdown();
        if (!scheduler.awaitTermination(5,TimeUnit.SECONDS)){
            scheduler.shutdownNow();
        }

        System.out.println("Scheduler stopped");
    }
}
