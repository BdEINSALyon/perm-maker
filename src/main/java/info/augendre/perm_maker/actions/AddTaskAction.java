package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.data.Task;
import info.augendre.perm_maker.gui.TaskDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by gaugendre on 18/04/2016 13:00
 */
public class AddTaskAction extends AbstractAction {
    private Planning planning;

    public AddTaskAction(Planning planning) {
        this.planning = planning;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Task task = new Task();
        TaskDialog dialog = new TaskDialog(task);
        dialog.pack();
        dialog.setVisible(true);
        System.out.println("TASK : " + task);
        planning.addTask(task);
    }
}