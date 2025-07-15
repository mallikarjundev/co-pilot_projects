public class TaskWorker implements Runnable{
    private final Task task;
    private final int maxRetries;

    public TaskWorker(Task task, int maxRetries) {
        this.task = task;
        this.maxRetries = maxRetries;
    }

    @Override
    public void run(){
        long start = System.currentTimeMillis();
        int attempt = 0;
        boolean success = false;

        while (attempt<=maxRetries && !success){
            attempt++;
            try {
                System.out.printf("Attempt %d for Task %s by %s%n",attempt,task.getId(),Thread.currentThread().getName());

                if (Math.random()<0.3){
                    throw  new RuntimeException("Simulated Failure");
                }

                System.out.printf("✅ Task %s completed by %s%n",
                        task.getId(), Thread.currentThread().getName());
                success = true;
            }catch (Exception e){
                System.out.printf("❌ Task %s failed on attempt %d: %s%n",
                        task.getId(), attempt, e.getMessage());
            }
        }

        if (!success) {
            System.out.printf("🚨 Task %s failed after %d attempts%n",
                    task.getId(), maxRetries);
        }

        long end = System.currentTimeMillis();
        System.out.printf("Task %s took %d ms%n",task.getId(),end-start);

//        String threadName = Thread.currentThread().getName();
//        System.out.println("[START] Task "+task.getId()+" | Thread: "+threadName);
//
//        try {
//            Thread.sleep(task.getDurationSecs());
//        }catch (InterruptedException e){
//            System.out.println("[ERROR] Task "+task.getId()+" interrupted.");
//            Thread.currentThread().interrupt();
//        }
//        System.out.println("[END] Task "+task.getId()+" | Thread: "+threadName);
    }
}
