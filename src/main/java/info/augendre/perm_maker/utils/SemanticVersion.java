package info.augendre.perm_maker.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This class aims to compare semantic version numbers.
 * Semantic version numbers are defined here : http://semver.org
 */
public class SemanticVersion implements Comparable<SemanticVersion> {
    /** The major version number. */
    private int major;

    /** The minor version number. */
    private int minor;

    /** The patch version number. */
    private int patch;

    /** The {@link PreRelease} tag. */
    private PreRelease preRelease;

    /** The pre-release version number. */
    private int preReleaseVersion;

    /**
     * Constructs a {@link SemanticVersion} with all information.
     * @param major The major version number.
     * @param minor The minor version number.
     * @param patch The patch version number.
     * @param preRelease The {@link PreRelease} tag.
     * @param preReleaseVersion The pre-release version number.
     */
    public SemanticVersion(int major, int minor, int patch, PreRelease preRelease, int preReleaseVersion) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = preRelease;
        this.preReleaseVersion = preReleaseVersion;
    }

    /**
     * Constructs a {@link SemanticVersion} with only major, minor and patch.
     * @param major The major version number.
     * @param minor The minor version number.
     * @param patch The patch version number.
     */
    public SemanticVersion(int major, int minor, int patch) {
        this(major, minor, patch, null, 0);
    }

    /**
     * Constructs a {@link SemanticVersion} from a {@link String}.
     * @param version The version number.<p>
     *                Must be in the following format : "X.Y.Z-PRE.W", where :
     *                <ul>
     *                    <li>X is the major version number</li>
     *                    <li>Y is the minor version number</li>
     *                    <li>Z is the patch version number</li>
     *                    <li>PRE is the pre-release tag (see {@link PreRelease})</li>
     *                    <li>W is the pre-release version number</li>
     *                </ul>
     */
    public SemanticVersion(String version) {
        version = normalize(version);
        String delimiters = "[.-]";
        String[] tokens = version.split(delimiters);
        int major = 0;
        int minor = 0;
        int patch = 0;
        PreRelease preRelease = null;
        int preReleaseVersion = 0;

        switch (tokens.length) {
            case 5:
                preReleaseVersion = Integer.parseInt(tokens[4]);
            case 4:
                preRelease = PreRelease.fromString(tokens[3]);
            case 3:
                patch = Integer.parseInt(tokens[2]);
            case 2:
                minor = Integer.parseInt(tokens[1]);
            case 1:
                major = Integer.parseInt(tokens[0]);
        }

        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = preRelease;
        this.preReleaseVersion = preReleaseVersion;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    public PreRelease getPreRelease() {
        return preRelease;
    }

    public void setPreRelease(PreRelease preRelease) {
        this.preRelease = preRelease;
    }

    public int getPreReleaseVersion() {
        return preReleaseVersion;
    }

    public void setPreReleaseVersion(int preReleaseVersion) {
        this.preReleaseVersion = preReleaseVersion;
    }

    @Override
    public int compareTo(SemanticVersion o) {
        // Compare major, minor and patch, classically.
        if (this.major > o.major) return 1;
        if (this.major < o.major) return -1;
        if (this.minor > o.minor) return 1;
        if (this.minor < o.minor) return -1;
        if (this.patch > o.patch) return 1;
        if (this.patch < o.patch) return -1;

        // Compare pre-release tags.
        // If one is null, it means it's a stable. Therefore it's greater than pre-release.
        if (this.preRelease == null && o.preRelease == null) return 0;
        if (this.preRelease == null) return 1;
        if (o.preRelease == null) return -1;
        if (this.preRelease.compareTo(o.preRelease) != 0) return this.preRelease.compareTo(o.preRelease);
        if (this.preReleaseVersion > o.preReleaseVersion) return 1;
        if (this.preReleaseVersion < o.preReleaseVersion) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SemanticVersion)) return false;

        SemanticVersion that = (SemanticVersion) o;

        return new EqualsBuilder()
            .append(major, that.major)
            .append(minor, that.minor)
            .append(patch, that.patch)
            .append(preReleaseVersion, that.preReleaseVersion)
            .append(preRelease, that.preRelease)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(major)
            .append(minor)
            .append(patch)
            .append(preRelease)
            .append(preReleaseVersion)
            .toHashCode();
    }

    /**
     * Formats a version number to the following format : "X.Y.Z-PRE.W", where :<p>
     *     <ul>
     *         <li>X is the major version number</li>
     *         <li>Y is the minor version number</li>
     *         <li>Z is the patch version number</li>
     *         <li>PRE is the pre-release tag (see {@link PreRelease})</li>
     *         <li>W is the pre-release version number</li>
     *     </ul>
     * @return The formatted version number.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.major).append(".").append(this.minor).append(".").append(this.patch);

        if (this.preRelease != null) {
            String pr = preReleaseToString(preRelease);
            sb.append("-").append(pr).append(".").append(this.preReleaseVersion);
        }

        return sb.toString();
    }

    /**
     * Removes the "v" that may be the first character of a version number. <p>
     * Examples :
     * <blockquote><pre>
     *     normalize("v1.0.4") returns "1.0.4"
     *     normalize("1.0.4") returns "1.0.4"
     * </pre></blockquote>
     * @param versionNumber The version number in {@link String} format.
     * @return The normalized version number in {@link String} format.
     */
    public static String normalize(String versionNumber) {
        if (versionNumber.startsWith("v")) versionNumber = versionNumber.substring(1);
        return versionNumber;
    }

    /**
     * Returns the {@link String} for the given {@link PreRelease}.
     * @param pr The {@link PreRelease} to convert to {@link String}.
     * @return The {@link String} representing the {@link PreRelease}.
     */
    public String preReleaseToString(PreRelease pr) {
        switch (pr) {
            case SNAPSHOT:
                return "SNAPSHOT";
            case NIGHTLY:
                return "nightly";
            case ALPHA:
                return "alpha";
            case BETA:
                return "beta";
            case RC:
                return "rc";
            default:
                return "";
        }
    }
}
