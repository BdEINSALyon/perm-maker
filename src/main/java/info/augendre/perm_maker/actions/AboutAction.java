package info.augendre.perm_maker.actions;

import info.augendre.perm_maker.utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * Created by gaugendre on 29/07/2016 01:20
 */
public class AboutAction extends AbstractAction {
    private JFrame frame;
    private static ResourceBundle stringsBundle = ResourceBundle.getBundle("strings");

    public AboutAction(JFrame frame) {
        super(stringsBundle.getString("menu-about"));
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.showDialog();
    }

    public void showDialog() {
        JEditorPane editorPane = Utils.htmlEditorPaneFactory(
            stringsBundle.getString("menu-aboutdialog-message")
        );

        JOptionPane.showMessageDialog(
            frame,
            editorPane,
            stringsBundle.getString("menu-aboutdialog-title"),
            JOptionPane.PLAIN_MESSAGE
        );
    }
}
