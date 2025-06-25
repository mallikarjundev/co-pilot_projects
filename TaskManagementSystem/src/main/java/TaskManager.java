import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private final List<Task> tasks= new ArrayList<>();

    private int nextId=1;

    public void addTask(String title, String desc, String dueDate) {
        tasks.add(new Task(nextId++, title, desc, dueDate, false));
    }

    public void updateTask(int id, String title, String desc, String dueDate, boolean completed) {
        Task task = getTaskById(id);
        if (task != null){
            task.setTitle(title);
            task.setDescription(desc);
            task.setDueDate(dueDate);
            task.setCompleted(completed);
        }
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {
        return tasks.stream().filter(task -> task.getId() == id).findFirst().orElse(null);
    }
}
