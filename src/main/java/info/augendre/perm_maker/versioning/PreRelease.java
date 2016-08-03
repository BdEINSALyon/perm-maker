package info.augendre.perm_maker.versioning;

/**
 * Enumerates the pre-release tags available to use in {@link SemanticVersion}.
 * @see SemanticVersion
 * @author gaugendre
 */
public enum PreRelease implements Comparable<PreRelease> {
    /**
     * Describes a snapshot version, usually a version built from a dev's computer.
     */
    SNAPSHOT,
    /**
     * Describes a version usually automatically built every night from a build server.
     */
    NIGHTLY,
    /**
     * Describes an alpha version, usually used for private testing inside the company.
     */
    ALPHA,
    /**
     * Describes a beta version, usually for public testing in the real world.
     */
    BETA,
    /**
     * Describes a release candidate version. A RC could be the real release but usually still needs few user feedback.
     */
    RC;

    /**
     * Builds a {@link PreRelease} given a {@link String}.
     * Doesn't care about case.<p>
     *
     * Requires string to fully spell the pre-release tag.<p>
     * Examples :
     * <blockquote><pre>
     *     PreRelease.fromString("alpha") returns PreRelease.ALPHA
     *     PreRelease.fromString("ALPHA") returns PreRelease.ALPHA
     *     PreRelease.fromString("AlPha") returns PreRelease.ALPHA
     *     PreRelease.fromString("a") returns null
     * </pre></blockquote>
     *
     * @param s The string to convert.
     * @return The built {@link PreRelease}. If no match is found, returns {@code null}.
     */
    public static PreRelease fromString(String s) {
        if (s.equalsIgnoreCase("alpha")) {
            return PreRelease.ALPHA;
        }
        else if (s.equalsIgnoreCase("beta")) {
            return PreRelease.BETA;
        }
        else if (s.toLowerCase().startsWith("snap")) {
            return PreRelease.SNAPSHOT;
        }
        else if (s.equalsIgnoreCase("rc")) {
            return PreRelease.RC;
        }
        else if (s.equalsIgnoreCase("nightly")) {
            return PreRelease.NIGHTLY;
        }
        return null;
    }
}
