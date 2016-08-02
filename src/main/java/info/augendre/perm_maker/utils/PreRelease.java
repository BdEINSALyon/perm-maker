package info.augendre.perm_maker.utils;

/**
 * Enumerates the pre-release tags available to use in {@link SemanticVersion}.
 * @see SemanticVersion
 */
public enum PreRelease implements Comparable<PreRelease> {
    SNAPSHOT, NIGHTLY, ALPHA, BETA, RC;

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
