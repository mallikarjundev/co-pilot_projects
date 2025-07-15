import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private final ExecutorService executor;

    public TaskManager(int threadCount) {
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public void submitTasks(List<Task> tasks){
        for (Task t: tasks){
//            executor.submit(new TaskWorker(t));
            executor.submit(new TaskWorker(t,2));
        }
    }

    public void shutdown(){
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)){
                System.out.println("Forcing shutdown....");
                executor.shutdownNow();
            }else {
                System.out.println("All tasks completed.");
            }
        }catch (InterruptedException e){
            System.out.println("shutdown interrupted");
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
