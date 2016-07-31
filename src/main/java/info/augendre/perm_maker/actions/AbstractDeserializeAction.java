package info.augendre.perm_maker.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Created by gaugendre on 31/07/2016 13:17
 */
public abstract class AbstractDeserializeAction<E extends Serializable> extends AbstractAction {
    private static final long serialVersionUID = 1100346215434394663L;
    private E deserialized;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            FileInputStream fis = new FileInputStream("/tmp/address.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            deserialized = (E) ois.readObject();
            update();
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public E getDeserialized() {
        return deserialized;
    }

    public abstract void update() throws ClassCastException;
}
