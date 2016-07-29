package info.augendre.perm_maker.listeners;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by gaugendre on 29/07/2016 12:59
 */
public class AllowEditListener implements ListSelectionListener {
    private JButton button;

    public AllowEditListener(JButton button) {
        this.button = button;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (e.getFirstIndex() >= 0) {
                button.setEnabled(true);
            }
            else {
                button.setEnabled(false);
            }
        }
    }
}
