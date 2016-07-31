package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Created by gaugendre on 31/07/2016 13:17
 */
public class SerializeAction<E extends Serializable> extends AbstractAction {
    private static final long serialVersionUID = -9100062737723003733L;
    private E element;

    public SerializeAction(E element) {
        this.element = element;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FileDialog dialog;
            String mHomeDir = System.getProperty("user.home");
            String fileName = "untitled.perm";
            if (element instanceof Planning) {
                fileName = "planning.perm";
            }

            dialog = new FileDialog((Dialog) null, "Save", FileDialog.SAVE);
            dialog.setDirectory(mHomeDir);
            dialog.setFile(fileName);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);

            if (dialog.getFile() != null) {
                String dir = dialog.getDirectory();
                File file = new File(dir + dialog.getFile());
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(element);
                oos.close();
                fos.close();
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
