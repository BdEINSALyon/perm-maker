package info.augendre.perm_maker.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This class aims to compare semantic version numbers.
 * Semantic version numbers are defined here : http://semver.org
 */
public class SemanticVersion implements Comparable<SemanticVersion> {
    private int major;
    private int minor;
    private int revision;
    private PreRelease preRelease;
    private int preReleaseVersion;

    public SemanticVersion(int major, int minor, int revision, PreRelease preRelease, int preReleaseVersion) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
        this.preRelease = preRelease;
        this.preReleaseVersion = preReleaseVersion;
    }

    public SemanticVersion(int major, int minor, int revision) {
        this(major, minor, revision, null, 0);
    }

    public SemanticVersion(String version) {
        version = normalize(version);
        String delimiters = "[.-]";
        String[] tokens = version.split(delimiters);
        int major = 0;
        int minor = 0;
        int revision = 0;
        PreRelease preRelease = null;
        int preReleaseVersion = 0;

        switch (tokens.length) {
            case 5:
                preReleaseVersion = Integer.parseInt(tokens[4]);
            case 4:
                preRelease = PreRelease.fromString(tokens[3]);
            case 3:
                revision = Integer.parseInt(tokens[2]);
            case 2:
                minor = Integer.parseInt(tokens[1]);
            case 1:
                major = Integer.parseInt(tokens[0]);
        }

        this.major = major;
        this.minor = minor;
        this.revision = revision;
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

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
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
        // Compare major, minor and revision, classically.
        if (this.major > o.major) return 1;
        if (this.major < o.major) return -1;
        if (this.minor > o.minor) return 1;
        if (this.minor < o.minor) return -1;
        if (this.revision > o.revision) return 1;
        if (this.revision < o.revision) return -1;

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
            .append(revision, that.revision)
            .append(preReleaseVersion, that.preReleaseVersion)
            .append(preRelease, that.preRelease)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(major)
            .append(minor)
            .append(revision)
            .append(preRelease)
            .append(preReleaseVersion)
            .toHashCode();
    }

    public static String normalize(String versionNumber) {
        if (versionNumber.startsWith("v")) versionNumber = versionNumber.substring(1);
        return versionNumber;
    }
}
