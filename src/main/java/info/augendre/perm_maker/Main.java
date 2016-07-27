package info.augendre.perm_maker;

import info.augendre.perm_maker.actions.QuitAction;
import info.augendre.perm_maker.data.Resource;
import info.augendre.perm_maker.gui.MainPanel;
import info.augendre.perm_maker.utils.Utils;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterator;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by Gabriel Augendre on 28/06/16
 */
public class Main implements Runnable {
    private JPanel mainPanel;
    private JFrame mainFrame;
    private static boolean newVersionAvailable = false;
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
            GitHub gitHub = GitHub.connectAnonymously();
            GHRepository repo = gitHub.getRepository(projectBundle.getString("repo"));
            PagedIterator<GHRelease> releases = repo.listReleases().iterator();

            if (releases.hasNext()) {
                GHRelease latest = releases.next();
                if (!latest.isDraft() && !latest.isPrerelease()) {
                    String releaseTagName = latest.getTagName();
                    String currentVersion = projectBundle.getString("version");
                    releaseTagName = Utils.normalizeVersionNumber(releaseTagName);
                    currentVersion = Utils.normalizeVersionNumber(currentVersion);

                    if (!releaseTagName.equals(currentVersion))
                        newVersionAvailable = true;
                }
            }
        }
        catch (IOException e) {
            System.out.println("Can't check for updates. Please check your internet connection.");
            e.printStackTrace();
        }

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

        if (newVersionAvailable) {
            ResourceBundle stringsBundle = ResourceBundle.getBundle("strings");
            JOptionPane.showMessageDialog(mainFrame, stringsBundle.getString("project-update_available") + "\n" + projectBundle.getString("releases_url"));
        }
    }

}
