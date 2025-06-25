import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TaskController {
    private TaskView view;
    private TaskManager model;

    public TaskController(TaskManager model, TaskView view){
        this.model=model;
        this.view=view;

        refreshTable();

        view.addButton.addActionListener(e->addTask());
        view.updateButton.addActionListener(e->updateTask());
        view.deleteButton.addActionListener(e->deleteTask());

        view.taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.taskTable.getSelectedRow();
                if (row>=0){
                    view.titleField.setText(view.taskTable.getValueAt(row,1).toString());
                    view.dueDateField.setText(view.taskTable.getValueAt(row,2).toString());
                    view.completedBox.setSelected(view.taskTable.getValueAt(row,3).equals("Completed"));
                }
            }
        });
    }

    private void refreshTable(){
        DefaultTableModel tableModel= (DefaultTableModel) view.taskTable.getModel();
        tableModel.setRowCount(0);
        for (Task task: model.getAllTasks()){
            String status = task.isCompleted()?"Completed":"Pending";
            tableModel.addRow(new Object[]{task.getId(),task.getTitle(),task.getDueDate(),status});
        }
    }

    public void addTask(){
        model.addTask(view.titleField.getText(),
                view.descField.getText(),
                view.dueDateField.getText());
        clearForm();
        refreshTable();
    }

    public void updateTask(){
        int row = view.taskTable.getSelectedRow();
        if (row>=0){
            int taskId = (int) view.taskTable.getValueAt(row,0);
            model.updateTask(taskId,view.titleField.getText(),
                    view.descField.getText(),
                    view.dueDateField.getText(),
                    view.completedBox.isSelected());
            refreshTable();
        }
    }

    public void deleteTask(){
        int row = view.taskTable.getSelectedRow();
        if (row>=0) {
            int taskId = (int) view.taskTable.getValueAt(row, 0);
            model.deleteTask(taskId);
            clearForm();
            refreshTable();
        }
    }

    private void clearForm(){
        view.titleField.setText("");
        view.descField.setText("");
        view.dueDateField.setText("");
        view.completedBox.setSelected(false);
    }
}
