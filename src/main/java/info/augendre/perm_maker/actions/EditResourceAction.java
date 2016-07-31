package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;
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
            resources
                .stream()
                .filter(resource -> resource != null)
                .forEachOrdered(r -> editResource(r, defineResourcesDialog.getMainPanel().getPlanning()));
        }
    }

    public static void editResource(Resource resource, Planning planning) {
        ResourceDialog dialog = new ResourceDialog(resource, planning);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}