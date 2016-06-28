package info.augendre.perm_maker;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by gaugendre on 18/04/2016 13:00
 */
public class QuitAction extends AbstractAction {
    private JFrame mainFrame;

    public QuitAction(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainFrame.dispose();
        System.exit(0);
    }
}