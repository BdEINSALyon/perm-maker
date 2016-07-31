package info.augendre.perm_maker.actions;

import javax.swing.*;
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
            FileOutputStream fos = new FileOutputStream("/tmp/address.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(element);
            oos.close();
            fos.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
