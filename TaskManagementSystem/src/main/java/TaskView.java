import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TaskView extends JFrame {

    public JTextField titleField = new JTextField();
    public JTextField dueDateField = new JTextField();
    public JTextArea descField = new JTextArea();
    public JCheckBox completedBox = new JCheckBox("Completed");
    public JTable taskTable = new JTable();
    public JButton addButton = new JButton("Add Task");
    public JButton updateButton = new JButton("Update Task");
    public JButton deleteButton = new JButton("Delete Task");

    public TaskView(){
        setTitle("Task Manager");
        setSize(800,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5,4));
        form.add(new JLabel("Title: "));
        form.add(titleField);
        form.add(new JLabel("Description: "));
        form.add(new JScrollPane(descField));
        form.add(new JLabel("Due Date: "));
        form.add(dueDateField);
        form.add(new JLabel("Status: "));
        form.add(completedBox);

        JPanel buttons = new JPanel();
        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        form.add(buttons);

        add(form, BorderLayout.NORTH);
        taskTable.setModel(new DefaultTableModel(new Object[]{"ID","TITLE","DUE DATE","STATUS"},0));
        add(new JScrollPane(taskTable),BorderLayout.CENTER);
    }
}
