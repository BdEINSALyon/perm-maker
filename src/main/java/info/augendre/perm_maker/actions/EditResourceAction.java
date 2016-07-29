package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.gui.DefineResourcesDialog;
import info.augendre.perm_maker.gui.ResourceDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by gaugendre on 18/04/2016 13:00
 */
public class EditResourceAction extends AbstractAction {
    private DefineResourcesDialog defineResourcesDialog;

    public EditResourceAction(DefineResourcesDialog defineResourcesDialog) {
        this.defineResourcesDialog = defineResourcesDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!defineResourcesDialog.isSelectionEmpty()) {
            List<Resource> resources = defineResourcesDialog.getSelectedResources();
            resources.stream().filter(resource -> resource != null).forEachOrdered(resource -> {
                ResourceDialog dialog = new ResourceDialog(resource, defineResourcesDialog.getMainPanel().getPlanning());
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            });
        }
    }
}