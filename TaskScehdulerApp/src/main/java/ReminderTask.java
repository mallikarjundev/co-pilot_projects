public class ReminderTask implements Runnable{

    private final String message;

    public ReminderTask(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println("[Reminder] "+message+" | "+ System.currentTimeMillis());
    }
}
