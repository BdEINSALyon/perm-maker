package info.augendre.perm_maker.versioning;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by gaugendre on 02/08/2016 11:13
 */
@RunWith(Parameterized.class)
public class SemanticVersionTest {
    @Parameterized.Parameters(name = "{index}: tag={0}")
    public static Iterable<?> data() {
        return Arrays.asList(new Object[][]{
            {PreRelease.SNAPSHOT, "1.2.4-SNAPSHOT.1", "SNAPSHOT"},
            {PreRelease.NIGHTLY, "1.2.4-nightly.1", "nightly"},
            {PreRelease.ALPHA, "1.2.4-alpha.1", "alpha"},
            {PreRelease.BETA, "1.2.4-beta.1", "beta"},
            {PreRelease.RC, "1.2.4-rc.1", "rc"}
        });
    }

    private SemanticVersion sv1;
    private SemanticVersion sv2;
    private SemanticVersion sv3;
    private SemanticVersion sv4;
    private SemanticVersion sv5;
    private SemanticVersion sv6;
    private SemanticVersion sv7;
    private String v123;
    private String v123Small;

    private PreRelease prTag;
    private String expectedVersion;
    private String expectedToString;

    public SemanticVersionTest(PreRelease tag, String expectedVersion, String expectedToString) {
        this.prTag = tag;
        this.expectedVersion = expectedVersion;
        this.expectedToString = expectedToString;
    }

    @Before
    public void setUp() throws Exception {
        sv1 = new SemanticVersion(1, 2, 3);
        sv2 = new SemanticVersion(1, 2, 3);
        sv3 = new SemanticVersion(1, 2, 4, this.prTag, 1);

        v123 = "v1.2.3";
        sv4 = new SemanticVersion(v123);
        sv5 = new SemanticVersion(expectedVersion);

        v123Small = "1.2.3";
        sv6 = new SemanticVersion(v123Small);
        sv7 = new SemanticVersion("1.2.4");
    }

    @Test
    public void compareToTest() throws Exception {
        assertEquals(0, sv1.compareTo(sv2));
        assertTrue(sv1.compareTo(sv3) < 0);
        assertEquals(0, sv1.compareTo(sv4));
        assertTrue(sv1.compareTo(sv5) < 0);
        assertEquals(0, sv1.compareTo(sv6));
        assertTrue(sv1.compareTo(sv7) < 0);
        assertEquals(0, sv3.compareTo(sv5));
        assertTrue(sv3.compareTo(sv1) > 0);
        assertTrue(sv5.compareTo(sv1) > 0);
        assertTrue(sv7.compareTo(sv1) > 0);
        assertTrue(sv7.compareTo(sv3) > 0);
        assertTrue(sv7.compareTo(sv5) > 0);
    }

    @Test
    public void equalsTest() throws Exception {
        assertTrue(sv1.equals(sv2));
        assertTrue(sv1.equals(sv4));
        assertTrue(sv1.equals(sv6));
        assertTrue(sv3.equals(sv5));
    }

    @Test
    public void preReleaseToStringTest() throws Exception {
        assertEquals(expectedToString, SemanticVersion.preReleaseToString(this.prTag));
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("1.2.3", sv1.toString());
        assertEquals("1.2.3", sv2.toString());
        assertEquals("1.2.3", sv4.toString());
        assertEquals("1.2.3", sv6.toString());

        assertEquals(expectedVersion, sv3.toString());
        assertEquals(expectedVersion, sv5.toString());

        assertEquals("1.2.4", sv7.toString());
    }

    @Test
    public void normalizeTest() throws Exception {
        assertEquals(v123Small, SemanticVersion.normalize(v123));
        assertEquals(v123Small, SemanticVersion.normalize(v123Small));
    }

}