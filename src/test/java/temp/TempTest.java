package temp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TempTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TempTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TempTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }
}
