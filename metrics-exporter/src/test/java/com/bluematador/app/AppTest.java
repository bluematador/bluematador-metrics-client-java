package com.bluematador.app;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private static final Logger logger = LogManager.getLogger(AppTest.class);
    private BlueMatadorClient bluematador = new BlueMatadorClient();

    public void testIllegalMetricName() {

        try {
            bluematador.count("countExample:main", 1, 1, new String[]{"Env:dev", "account:122321"});
        } catch (Exception e) {
            assertEquals("Result", "Illegal character : found in metric name", e.getMessage());
        }

        try {
            bluematador.gauge("gaugeExample:main", 23.2);
        } catch (Exception e) {
            assertEquals("Result", "Illegal character : found in metric name", e.getMessage());
        }

    }

    public void testIllegalMetricNameTwo() {
        try {
            bluematador.count("countExample|main");
        } catch (Exception e) {
            assertEquals("Result", "Illegal character | found in metric name", e.getMessage());
        }

        try {
            bluematador.gauge("gaugeExample|main", 22);
        } catch (Exception e) {
            assertEquals("Result", "Illegal character | found in metric name", e.getMessage());
        }
    }

    public void testIllegalMeticTag() {
        try {
            bluematador.count("countExample", new String[]{"env:#dev"});
        } catch (Exception e) {
            assertEquals("Result", "Illegal character # found in metric tags", e.getMessage());
        }
    }

    public void testIllegalMeticTagTwo() {
        try {
            bluematador.gauge("gaugeExample", 101.21, new String[]{"env:dev", "account|123"});
        } catch (Exception e) {
            assertEquals("Result", "Illegal character | found in metric tags", e.getMessage());
        }
    }

    public void testIllegalMeticTagThree() {
        try {
            bluematador.count("counterExample", 2, new String[]{"env:dev, prod"});
        } catch (Exception e) {
            assertEquals("Result", "Illegal character , found in metric tags", e.getMessage());
        }
    }

    public void testIllegalMeticTagFour() {
        try {
            bluematador.gauge("gaugeExample", 123, new String[]{"account@123"});
        } catch (Exception e) {
            assertEquals("Result", "Illegal character @ found in metric tags", e.getMessage());
        }
    }
}
