package info.augendre.perm_maker.gui;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by Gabriel Augendre on 28/06/16
 */
public class Main implements Runnable {
    private JPanel mainPanel;
    private JFrame mainFrame;
    private final static String CLOSE = "close";
    private final static int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
    private final static KeyStroke escapeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

    public Main() {
        mainFrame = new JFrame("Perm Maker");
        this.mainPanel = new MainPanel(mainFrame).getMainPanel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace(System.err);
        }

        // Add key binding
        mainPanel.getInputMap(AFC).put(escapeStroke, CLOSE);
        mainPanel.getActionMap().put(CLOSE, new QuitAction(mainFrame));

        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}
