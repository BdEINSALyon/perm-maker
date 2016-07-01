package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.gui.DefineResourcesDialog;
import info.augendre.perm_maker.gui.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by gaugendre on 18/04/2016 13:00
 */
public class DefineResourcesAction extends AbstractAction {
    private MainPanel mainPanel;

    public DefineResourcesAction(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefineResourcesDialog dialog = new DefineResourcesDialog(mainPanel.getResources());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}