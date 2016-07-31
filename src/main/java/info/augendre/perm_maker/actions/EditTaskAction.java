package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Task;
import info.augendre.perm_maker.gui.DefinePlanningDialog;
import info.augendre.perm_maker.gui.TaskDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by gaugendre on 18/04/2016 13:00
 */
public class EditTaskAction extends AbstractAction {
    private DefinePlanningDialog definePlanningDialog;

    public EditTaskAction(DefinePlanningDialog definePlanningDialog) {
        this.definePlanningDialog = definePlanningDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!definePlanningDialog.isSelectionEmpty()) {
            List<Task> tasks = definePlanningDialog.getSelectedTasks();
            tasks
                .stream()
                .filter(task -> task != null)
                .forEachOrdered(EditTaskAction::editTask);
        }
    }

    public static void editTask(Task task) {
        TaskDialog dialog = new TaskDialog(task);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}