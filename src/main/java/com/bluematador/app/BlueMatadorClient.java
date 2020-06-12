package com.bluematador;

import com.timgroup.statsd.BlueMatadorNonBlockingStatsDClient;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The BlueMatadorClient class allows you to send custom gauge and count metrics
 * to your Blue Matador dashboard
 */
public class BlueMatadorClient {
    /**
     * client is an instance of the BlueMatadorNonBlockingStatsDClient which extends the DataDog StatsDClient
     * The custom metrics are formatted and then sent to the Blue Matador agent through 
     * this class.
     */
    private BlueMatadorNonBlockingStatsDClient client;

    public BlueMatadorClient(BlueMatadorNonBlockingStatsDClient client) {
        this.client = client;
    }


    /**
     * sanitizes a string by replacing illegal characters : and | with _
     * 
     * @param string the string to sanitize
     * 
     * @return the sanitized string
     */
    private String sanitize(String string) {
        var String sanitizedString = string;
        if(sanitizedString.contains(":")) {
            sanitizedString = sanitizedString.replace(":", "_");
        } 
        if(sanitizedString.contains("|")) {
            sanitizedString = sanitizedString.replace("|", "_");
        }
        return sanitizedString;
    }

    /**
     * sends each tag to the sanitize function to replace illegal characters : and | with _
     * 
     * @param tags the array of tags to sanitize
     * 
     * @return the array of sanitized tags
     */
    private String[] sanitizeTags(String[] tags) {
        String[] sanitizedTags = new String[tags.length];
        for(int i = 0; i < tags.length; i++) {
            sanitizedTags[i] = this.sanitize(tags[i]);
        }
        return sanitizedTags;
    }
    
    /**
     * _count sanitizes your metrics checking for illegal characters within the string parameters
     * it then determines whether to send your metric based on the given sample rate.
     * 
     * @param name The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value  The amount to increment the metric by, the default is 1.
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * @param tags adds metadata to a metric. Can be specified as an array of strings with key-value pairs 
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *  
     * @return void 
     */
    private void _count(String name, double value, double sampleRate, String[] tags) throws Exception {
        var metricName = this.sanitize(name);
        var metricTags = this.sanitizeTags(tags);
            if(!this.isInvalidSample(Math.max(0, Math.min(1.0, sampleRate)))) {
                client.count(metricName, value / Math.max(0, Math.min(1.0, sampleRate)), metricTags);
            }
    }

     /**
     * _gauge sanitizes your metrics checking for illegal characters within the string parameters
     * it then determines whether to send your metric based on the given sample rate.
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * @param tags       adds metadata to a metric. Can be specified as an array of strings with key-value pairs 
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *  
     * @return void 
     */
    private void _gauge(String name, double value, double sampleRate, String[] tags) throws Exception {
        var metricName = this.sanitize(name);
        var metricTags = this.sanitizeTags(tags);
            if(!this.isInvalidSample(Math.max(0, Math.min(1.0, sampleRate)))) {
                client.recordGaugeValue(metricName, value, metricTags);
            }
    }
    
    /**
     * checks whether the given sampleRate is not equal to one and is less than the randomly generated number between
     * 0 and 1. If the sample rate is equal to 1 or greater than the random number the metric is sent
     * 
     * @param sampleRate the double to sample your data by. Must be a number between 0 and 1
     * 
     * @return           returns a boolean. False indicates to send the metric to the calling function
     */
    private boolean isInvalidSample(double sampleRate) {
        return sampleRate != 1 && ThreadLocalRandom.current().nextDouble() > sampleRate;
    }

    // private double toDouble(long num) {

    // }

    /**
     * sends a custom counter metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The amount to increment the metric by, the default is 1.
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * @param tags       adds metadata to a metric. Can be specified as an array of strings with key-value pairs 
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     * 
     * @return void 
     */
    public void count(String name, long value, double sampleRate, String[] tags) throws Exception {
        this._count(name, (double)value, sampleRate, tags);
    }

    public void count(String name, double value, double sampleRate, String[] tags) throws Exception {
        this._count(name, value, sampleRate, tags);
    }

    /**
     * sends a custom counter metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * 
     * value is set to 1
     * sampleRate is set to 1
     * tags are set to null 
     * 
     * @return void 
     */
    public void count(String name) throws Exception {
        this._count(name, 1, 1, null);
    }

    /**
     * sends a custom counter metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The amount to increment the metric by, the default is 1.
     * 
     * sampleRate is set to 1
     * tags are set to an empty array
     * 
     * @return void 
     */
    public void count(String name, long value) throws Exception {
        this._count(name, value, 1, null);
    }

    public void count(String name, double value) throws Exception {
        this._count(name, value, 1, null);
    }

    /**
     * sends a custom counter metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The amount to increment the metric by, the default is 1.
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * 
     * tags are set to an empty array
     * 
     * @return void 
     */
    public void count(String name, long value, double sampleRate) throws Exception {
        this._count(name, value, sampleRate, null);
    }

    public void count(String name, double value, double sampleRate) throws Exception {
        this._count(name, value, sampleRate, null);
    }

       /**
     * sends a custom counter metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param tags       adds metadata to a metric. Can be specified as an array of strings with key-value pairs 
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     * 
     * value is set to 1
     * sampleRate is set to 1
     * 
     * @return void 
     */
    public void count(String name, String[] tags) throws Exception {
        this._count(name, 1, 1, tags);
    }

       /**
     * sends a custom counter metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The amount to increment the metric by, the default is 1.
     * @param tags       adds metadata to a metric. Can be specified as an array of strings with key-value pairs 
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     * 
     * sampleRate is set to 1
     * 
     * @return void 
     */
    public void count(String name, long value, String[] tags) throws Exception {
        this._count(name, value, 1, tags);
    }

    public void count(String name, double value, String[] tags) throws Exception {
        this._count(name, value, 1, tags);
    }

    /**
     * sends a custom gauge metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * @param tags       adds metadata to a metric. Can be specified as an array of strings with key-value pairs 
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *  
     * @return void 
     */
    public void gauge(String name, long value, double sampleRate, String[] tags) throws Exception {
        this._gauge(name, value, sampleRate, tags);
    }

    public void gauge(String name, double value, double sampleRate, String[] tags) throws Exception {
        this._gauge(name, value, sampleRate, tags);
    }

     /**
     * sends a custom gauge metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * 
     * sampleRate is set to 1
     * tags are set to an empty array
     *  
     * @return void 
     */
    public void gauge(String name, long value) throws Exception {
        this._gauge(name, value, 1, null);   
    }

    public void gauge(String name, double value) throws Exception {
        this._gauge(name, value, 1, null);   
    }
     /**
     * sends a custom gauge metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * 
     * tags are set to an empty array
     *  
     * @return void 
     */
    public void gauge(String name, long value, double sampleRate) throws Exception {
        this._gauge(name, value, sampleRate, null);
    }

    public void gauge(String name, double value, double sampleRate) throws Exception {
        this._gauge(name, value, sampleRate, null);
    }

     /**
     * sends a custom gauge metric
     * 
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * @param tags       adds metadata to a metric. Can be specified as an array of strings with key-value pairs 
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *  
     * sampleRate is set to 1
     * 
     * @return void 
     */
    public void gauge(String name, long value, String[] tags) throws Exception {
        this._gauge(name, value, 1, tags);
    }

    public void gauge(String name, double value, String[] tags) throws Exception {
        this._gauge(name, value, 1, tags);
    }

    /**
     * closes the UDP connection of the client
     * 
     * @return void
     */
    public void close() throws Exception {
        client.close();
    }

}