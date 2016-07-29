package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Task;
import info.augendre.perm_maker.gui.DefinePlanningDialog;
import info.augendre.perm_maker.gui.TaskDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
        Task task = definePlanningDialog.getSelectedTask();
        if (task != null) {
            TaskDialog dialog = new TaskDialog(task);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }
}