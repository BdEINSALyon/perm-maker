package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.gui.DefineResourcesDialog;
import info.augendre.perm_maker.workers.LoadResourcesFromDoodleWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Determines the action to run when loading resources from Doodle (.xls export).
 * Loaded in {@link DefineResourcesDialog}.
 *
 * @author gaugendre
 */
public class LoadResourcesFromDoodleAction extends AbstractAction {
    private DefineResourcesDialog dialog;

    public LoadResourcesFromDoodleAction(DefineResourcesDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog dialog;
        String mHomeDir = System.getProperty("user.home");

        dialog = new FileDialog(this.dialog, "Open", FileDialog.LOAD);
        dialog.setDirectory(mHomeDir);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);

        if (dialog.getFile() != null) {
            String dir = dialog.getDirectory();
            File file = new File(dir + dialog.getFile());
            LoadResourcesFromDoodleWorker worker = new LoadResourcesFromDoodleWorker(this.dialog, file);
            worker.execute();
        }
    }
}
