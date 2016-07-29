package info.augendre.perm_maker;

import info.augendre.perm_maker.utils.Utils;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterator;

import javax.swing.*;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/**
 * Created by gaugendre on 29/07/2016 02:10
 */
public class CheckUpdateWorker extends SwingWorker<Boolean, Void> {
    private ResourceBundle projectBundle = ResourceBundle.getBundle("project");
    private ResourceBundle stringsBundle = ResourceBundle.getBundle("strings");

    @Override
    protected Boolean doInBackground() throws Exception {
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

                    return !releaseTagName.equals(currentVersion);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Can't check for updates. Please check your internet connection.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void done() {
        boolean updateAvailable;
        try {
            updateAvailable = get();
        }
        catch (InterruptedException | ExecutionException e) {
            updateAvailable = false;
            System.err.println("Error getting value from update check. Thread may have been interrupted.");
            e.printStackTrace();
        }

        if (updateAvailable) {
            JOptionPane.showMessageDialog(
                null,
                stringsBundle.getString("project-update_available") +
                    "\n"+
                    projectBundle.getString("releases_url")
            );
        }
    }
}