package info.augendre.perm_maker;

import info.augendre.perm_maker.utils.Utils;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterator;

import javax.swing.*;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by gaugendre on 28/07/2016 00:46
 */
public class CheckUpdateThread implements Runnable {
    private JFrame mainFrame;
    private ResourceBundle projectBundle = ResourceBundle.getBundle("project");
    private ResourceBundle stringsBundle = ResourceBundle.getBundle("strings");

    public CheckUpdateThread(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
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
                    System.out.println("Fetched all information");

                    if (!releaseTagName.equals(currentVersion)) {
                        JOptionPane.showMessageDialog(
                            mainFrame,
                            stringsBundle.getString("project-update_available") +
                                "\n"+
                                projectBundle.getString("releases_url")
                        );
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Can't check for updates. Please check your internet connection.");
            e.printStackTrace();
        }
    }
}
