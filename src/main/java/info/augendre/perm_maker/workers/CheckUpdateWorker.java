package info.augendre.perm_maker.workers;

import info.augendre.perm_maker.versioning.SemanticVersion;
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
 * This worker checks for available updates when the app starts.
 * If an update is found, a dialog is shown to the user.
 * @author gaugendre
 */
public class CheckUpdateWorker extends SwingWorker<Boolean, Void> {
    private ResourceBundle projectBundle = ResourceBundle.getBundle("project");
    private ResourceBundle stringsBundle = ResourceBundle.getBundle("strings");

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            // Connect to GitHub and fetch repo
            GitHub gitHub = GitHub.connectAnonymously();
            GHRepository repo = gitHub.getRepository(projectBundle.getString("repo"));
            PagedIterator<GHRelease> releases = repo.listReleases().iterator();

            if (releases.hasNext()) {
                // Get the latest release
                GHRelease latest = releases.next();
                if (!latest.isDraft() && !latest.isPrerelease()) {
                    // Get the release version and running version
                    String releaseTagName = latest.getTagName();
                    String currentVersion = projectBundle.getString("version");

                    SemanticVersion localVersion = new SemanticVersion(currentVersion);
                    SemanticVersion gitHubVersion = new SemanticVersion(releaseTagName);

                    // We have an update if the release version is greater than the local version
                    return gitHubVersion.compareTo(localVersion) > 0;
                }
            }
        }
        catch (IOException e) {
            System.err.println("Can't check for updates. Please check your internet connection.");
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
        }

        if (updateAvailable) {
            // Display a dialog if an update is available.
            JEditorPane editorPane = Utils.htmlEditorPaneFactory(
                stringsBundle.getString("project-update_available") +
                " <a href=\""+
                projectBundle.getString("releases_url") +
                "\">GitHub</a>"
            );
            JOptionPane.showMessageDialog(
                null,
                editorPane,
                stringsBundle.getString("project-update_available_title"),
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
