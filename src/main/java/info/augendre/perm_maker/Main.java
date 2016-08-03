package info.augendre.perm_maker;

import info.augendre.perm_maker.actions.AboutAction;
import info.augendre.perm_maker.actions.AboutListener;
import info.augendre.perm_maker.actions.QuitAction;
import info.augendre.perm_maker.gui.MainPanel;
import info.augendre.perm_maker.utils.Utils;
import info.augendre.perm_maker.workers.CheckUpdateWorker;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

/**
 * Main class of the app.<br>
 * Shows the first {@link JFrame} containing a {@link MainPanel}.<br>
 * Starts a {@link CheckUpdateWorker}.<br>
 * Tries to register OS X handlers for About if on a Mac.
 *
 * @author gaugendre
 * @see MainPanel
 */
public class Main implements Runnable {
    private JPanel mainPanel;
    private JFrame mainFrame;
    private final static String CLOSE = "close";
    private final static int AFC = JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
    private final static KeyStroke escapeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    private static ResourceBundle projectBundle = ResourceBundle.getBundle("project");
    private static ResourceBundle stringsBundle = ResourceBundle.getBundle("strings");

    public Main() {
        mainFrame = new JFrame(projectBundle.getString("name"));
        this.mainPanel = new MainPanel().getMainPanel();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("Can't set Look and Feel.");
        }

        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        // Add key binding
        mainPanel.getInputMap(AFC).put(escapeStroke, CLOSE);
        mainPanel.getActionMap().put(CLOSE, new QuitAction(mainFrame));
        boolean couldSetMac = false;
        if (Utils.isMac()) {
            // Found at https://stackoverflow.com/a/11094687/2758732
            try {
                // Get an application object
                Object app = Class.forName("com.apple.eawt.Application")
                    .getMethod("getApplication")
                    .invoke(null);

                // Get a proxy for AboutHandler
                Object al = Proxy.newProxyInstance(
                    Class.forName("com.apple.eawt.AboutHandler").getClassLoader(), // 3) But construct it as a AboutHandler so we can use it below
                    new Class[]{Class.forName("com.apple.eawt.AboutHandler")}, // 1) Instead of calling some method from AboutHandler interface
                    new AboutListener(mainFrame) // 2) .invoke() our AboutListener
                );

                // Set the handler
                app.getClass()
                    .getMethod("setAboutHandler", Class.forName("com.apple.eawt.AboutHandler"))
                    .invoke(app, al);

                couldSetMac = true;
            }
            catch (Exception e) {
                //fail quietly
            }
        }
        if (!Utils.isMac() || !couldSetMac) {
            // If not on a mac or couldn't set the handlers for some reason
            JMenuBar menuBar = new JMenuBar();
            JMenu helpMenu = new JMenu(stringsBundle.getString("menu-help"));
            JMenuItem aboutThisApp = new JMenuItem(new AboutAction(mainFrame));
            helpMenu.add(aboutThisApp);
            menuBar.add(helpMenu);

            mainFrame.setJMenuBar(menuBar);
        }

        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // Launch thread for update check
        CheckUpdateWorker checkUpdate = new CheckUpdateWorker();
        checkUpdate.execute();
    }

}
