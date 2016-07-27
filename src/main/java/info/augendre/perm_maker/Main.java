package info.augendre.perm_maker;

import info.augendre.perm_maker.actions.QuitAction;
import info.augendre.perm_maker.gui.MainPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

/**
 * Created by Gabriel Augendre on 28/06/16
 */
public class Main implements Runnable {
    private JPanel mainPanel;
    private JFrame mainFrame;
    private final static String CLOSE = "close";
    private final static int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
    private final static KeyStroke escapeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    private static ResourceBundle projectBundle = ResourceBundle.getBundle("project");

    public Main() {
        mainFrame = new JFrame(projectBundle.getString("name"));
        this.mainPanel = new MainPanel().getMainPanel();
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace(System.err);
        }

        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        // Add key binding
        mainPanel.getInputMap(AFC).put(escapeStroke, CLOSE);
        mainPanel.getActionMap().put(CLOSE, new QuitAction(mainFrame));

        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // Launch thread for update check
        SwingUtilities.invokeLater(new CheckUpdateThread(mainFrame));
    }

}
