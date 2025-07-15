import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskProcessorApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome...");

        System.out.println("Enter number of threads to use: ");
        int threadCount = Integer.parseInt(sc.nextLine());

        System.out.println("Enter number of tasks to process: ");
        int taskCount = Integer.parseInt(sc.nextLine());

        List<Task> tasks = new ArrayList<>();
        for (int i=1;i<=taskCount;i++){
            tasks.add(new Task("T"+i, "Task number "+i,5));
        }

        TaskManager manager = new TaskManager(threadCount);
        manager.submitTasks(tasks);
        manager.shutdown();

        System.out.println("Processing finished.");
    }
}
