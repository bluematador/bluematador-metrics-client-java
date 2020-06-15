package com.bluematador;

import com.bluematador.BlueMatadorClientBuilder;
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
    public BlueMatadorClient bluematador = this.initClient();
 
    public BlueMatadorClient initClient() {
        BlueMatadorClient client;
        try {
            client = new BlueMatadorClientBuilder().build();
         } catch (Exception e) {
             client = null;
             System.out.println(e);
         }
         return client;
    }
    

    public void testIllegalMetricName() {
        String sanitizedMetricName = bluematador.sanitize("my.app:new", ":");
        assertEquals("Result", "my.app_new", sanitizedMetricName);
    }

    public void testIllegalMetricNameTwo() {
        String sanitizedMetricName = bluematador.sanitize("my.app|new", ":");
        assertEquals("Result", "my.app_new", sanitizedMetricName);
    }

    public void testIllegalMeticTag() {
        String[] sanitizedMetricTags = bluematador.sanitizeLabels(new String[]{"env:#dev", "account|id:1234"});
        assertEquals("Result", "env:_dev", sanitizedMetricTags[0]);
        assertEquals("Result Two", "account_id:1234", sanitizedMetricTags[1]);
    }
}
