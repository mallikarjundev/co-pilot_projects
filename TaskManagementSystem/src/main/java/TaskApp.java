import javax.swing.*;

public class TaskApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            TaskManager model = new TaskManager();
            TaskView view = new TaskView();
            new TaskController(model,view);
            view.setVisible(true);
        });
    }
}
