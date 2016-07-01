package info.augendre.perm_maker.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResourceDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public ResourceDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
    }

    private void onOK() {
// add your code here
        dispose();
    }
}
