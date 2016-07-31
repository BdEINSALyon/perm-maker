package info.augendre.perm_maker.utils;

/**
 * Created by gaugendre on 01/08/2016 00:25
 */
public enum PreRelease implements Comparable<PreRelease> {
    SNAPSHOT, NIGHTLY, ALPHA, BETA, RC;

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
