package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.gui.DefinePlanningDialog;
import info.augendre.perm_maker.gui.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by gaugendre on 18/04/2016 13:00
 */
public class DefinePlanningAction extends AbstractAction {
    private MainPanel mainPanel;

    public DefinePlanningAction(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefinePlanningDialog dialog = new DefinePlanningDialog(mainPanel.getPlanning());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }
}