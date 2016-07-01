package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.gui.DefineResourcesDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by gaugendre on 18/04/2016 13:00
 */
public class AddResourceAction extends AbstractAction {
    private DefineResourcesDialog defineResourcesDialog;

    public AddResourceAction(DefineResourcesDialog defineResourcesDialog) {
        this.defineResourcesDialog = defineResourcesDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Resource resource = new Resource();
        ResourceDialog dialog = new ResourceDialog(resource);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.out.println("RESOURCE : " + resource);
        defineResourcesDialog.addResource(resource);
    }
}