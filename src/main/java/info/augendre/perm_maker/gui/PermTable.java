package info.augendre.perm_maker.gui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by gaugendre on 04/07/16
 */
public class PermTable extends JTable {
    public PermTable(TableModel dm) {
        super(dm);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component comp = super.prepareRenderer(renderer, row, column);
        String value = (String) getModel().getValueAt(row, column);
        if (value.equals("MISSING")) {
            comp.setBackground(Color.RED);
        } else if (value.equals("--")) {
            comp.setBackground(Color.LIGHT_GRAY);
        } else if (column == 0) {
            comp.setBackground(Color.WHITE);
        } else {
            comp.setBackground(Color.GREEN);
        }
        return comp;
    }
}
