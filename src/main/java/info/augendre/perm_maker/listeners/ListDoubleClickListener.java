package info.augendre.perm_maker.listeners;

import info.augendre.perm_maker.actions.EditResourceAction;
import info.augendre.perm_maker.actions.EditTaskAction;
import info.augendre.perm_maker.data.Planning;
import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.data.Task;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by gaugendre on 31/07/2016 12:34
 */
public class ListDoubleClickListener<E> extends MouseAdapter {
    private JList<E> list;
    private Planning planning;

    public ListDoubleClickListener(JList<E> list, Planning planning) {
        this.list = list;
        this.planning = planning;
    }

    public ListDoubleClickListener(JList<E> list) {
        this.list = list;
        this.planning = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int index = list.locationToIndex(e.getPoint());
            E element = list.getModel().getElementAt(index);

            if (element instanceof Task) {
                Task task = (Task) element;
                EditTaskAction.editTask(task);
            }
            else if (element instanceof Resource && planning != null) {
                Resource resource = (Resource) element;
                EditResourceAction.editResource(resource, planning);
            }
        }
    }
}
