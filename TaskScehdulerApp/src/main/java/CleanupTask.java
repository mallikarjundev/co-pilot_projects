public class CleanupTask implements Runnable{
    @Override
    public void run() {
        System.out.println("[Cleanup] Removing temporary files at "+System.currentTimeMillis());
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
