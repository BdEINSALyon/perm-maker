package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.data.Planning;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Created by gaugendre on 31/07/2016 13:17
 */
public abstract class AbstractDeserializeAction<E extends Serializable> extends AbstractAction {
    private static final long serialVersionUID = 1100346215434394663L;
    private E deserialized;
    private JDialog dialog;

    public AbstractDeserializeAction(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FileDialog dialog;
            String mHomeDir = System.getProperty("user.home");

            dialog = new FileDialog((Dialog) null, "Open", FileDialog.LOAD);
            dialog.setDirectory(mHomeDir);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.setVisible(true);

            if (dialog.getFile() != null) {
                String dir = dialog.getDirectory();
                File file = new File(dir + dialog.getFile());
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                deserialized = (E) ois.readObject();
                update();
                ois.close();
                fis.close();
            }
        }
        catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public E getDeserialized() {
        return deserialized;
    }

    public abstract void update() throws ClassCastException;

    public JDialog getDialog() {
        return dialog;
    }
}
