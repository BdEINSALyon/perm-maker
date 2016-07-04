package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.gui.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by gaugendre on 04/07/16
 */
public class DispatchTasksAction extends AbstractAction {
    private MainPanel mainPanel;

    public DispatchTasksAction(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Yop");
    }
}
