import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TaskManagerTest {

    @Test
    public void testSubmitTasksAndShutdown(){
        TaskManager taskManager = new TaskManager(3);

        List<Task> tasks = new ArrayList<>();
        for (int i=1;i<=5;i++){
            tasks.add(new Task("T"+i,"Testing task "+i,5));
        }

        assertDoesNotThrow(()->{
            taskManager.submitTasks(tasks);
            taskManager.shutdown();
        });
    }
}
