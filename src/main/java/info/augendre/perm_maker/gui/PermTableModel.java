package info.augendre.perm_maker.gui;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * Created by gaugendre on 01/07/16
 */
public class PermTableModel extends DefaultTableModel {

    public PermTableModel(Vector vector) {
        super(vector, 0);
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }
}
