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
     * sanitizes a string by replacing illegal characters with _
     *
     * @param name the string to sanitize
     * @param character the optional illegal character to sanitize. : for metric name, # for metric labels
     *
     * @return the sanitized string
     */
    protected String sanitize(String name, String character) {
        String sanitizedString = name;
        if(sanitizedString.contains(character)) {
            sanitizedString = sanitizedString.replace(character, "_");
        }
        if(sanitizedString.contains("|")) {
            sanitizedString = sanitizedString.replace("|", "_");
        }
        return sanitizedString;
    }

    /**
     * sends each tag to the sanitize function to replace illegal characters # and | with _
     *
     * @param labels the array of labels to sanitize
     *
     * @return the array of sanitized labels
     */
    protected String[] sanitizeLabels(String[] labels) {
        String[] sanitizedLabels = new String[labels.length];
        for(int i = 0; i < labels.length; i++) {
            sanitizedLabels[i] = this.sanitize(labels[i], "#");
        }
        return sanitizedLabels;
    }

    /**
     * _count sanitizes your metrics checking for illegal characters within the string parameters
     * it then determines whether to send your metric based on the given sample rate.
     *
     * @param name The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value  The amount to increment the metric by, the default is 1.
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * @param labels adds metadata to a metric. Can be specified as an array of strings with key-value pairs
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *
     * @return void
     */
    private void _count(String name, double value, double sampleRate, String[] labels) {
        String metricName = this.sanitize(name, ":");
        String[] metricLabels = labels != null ? this.sanitizeLabels(labels) : labels;
        client.count(metricName, value, sampleRate, metricLabels);
    }

     /**
     * _gauge sanitizes your metrics checking for illegal characters within the string parameters
     * it then determines whether to send your metric based on the given sample rate.
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * @param labels       adds metadata to a metric. Can be specified as an array of strings with key-value pairs
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *
     * @return void
     */
    private void _gauge(String name, double value, double sampleRate, String[] labels) {
        String metricName = this.sanitize(name, ":");
        String[] metricLabels = labels != null ? this.sanitizeLabels(labels) : labels;
        client.recordGaugeValue(metricName, value, sampleRate, metricLabels);
    }

    /**
     * sends a custom counter metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The amount to increment the metric by, the default is 1.
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * @param labels       adds metadata to a metric. Can be specified as an array of strings with key-value pairs
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *
     * @return void
     */
    public void count(String name, long value, double sampleRate, String[] labels) {
        this._count(name, (double)value, sampleRate, labels);
    }

    public void count(String name, double value, double sampleRate, String[] labels) {
        this._count(name, value, sampleRate, labels);
    }

    /**
     * sends a custom counter metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     *
     * value is set to 1
     * sampleRate is set to 1
     * labels are set to null
     *
     * @return void
     */
    public void count(String name) {
        this._count(name, 1, 1, null);
    }

    /**
     * sends a custom counter metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The amount to increment the metric by, the default is 1.
     *
     * sampleRate is set to 1
     * labels are set to an empty array
     *
     * @return void
     */
    public void count(String name, long value) {
        this._count(name, value, 1, null);
    }

    public void count(String name, double value) {
        this._count(name, value, 1, null);
    }

    /**
     * sends a custom counter metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The amount to increment the metric by, the default is 1.
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     *
     * labels are set to an empty array
     *
     * @return void
     */
    public void count(String name, long value, double sampleRate) {
        this._count(name, value, sampleRate, null);
    }

    public void count(String name, double value, double sampleRate) {
        this._count(name, value, sampleRate, null);
    }

       /**
     * sends a custom counter metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param labels       adds metadata to a metric. Can be specified as an array of strings with key-value pairs
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *
     * value is set to 1
     * sampleRate is set to 1
     *
     * @return void
     */
    public void count(String name, String[] labels) {
        this._count(name, 1, 1, labels);
    }

       /**
     * sends a custom counter metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The amount to increment the metric by, the default is 1.
     * @param labels       adds metadata to a metric. Can be specified as an array of strings with key-value pairs
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *
     * sampleRate is set to 1
     *
     * @return void
     */
    public void count(String name, long value, String[] labels) {
        this._count(name, value, 1, labels);
    }

    public void count(String name, double value, String[] labels) {
        this._count(name, value, 1, labels);
    }

    /**
     * sends a custom gauge metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     * @param labels       adds metadata to a metric. Can be specified as an array of strings with key-value pairs
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *
     * @return void
     */
    public void gauge(String name, long value, double sampleRate, String[] labels) {
        this._gauge(name, value, sampleRate, labels);
    }

    public void gauge(String name, double value, double sampleRate, String[] labels) {
        this._gauge(name, value, sampleRate, labels);
    }

     /**
     * sends a custom gauge metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     *
     * sampleRate is set to 1
     * labels are set to an empty array
     *
     * @return void
     */
    public void gauge(String name, long value) {
        this._gauge(name, value, 1, null);
    }

    public void gauge(String name, double value) {
        this._gauge(name, value, 1, null);
    }
     /**
     * sends a custom gauge metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * @param sampleRate sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
     *
     * labels are set to an empty array
     *
     * @return void
     */
    public void gauge(String name, long value, double sampleRate) {
        this._gauge(name, value, sampleRate, null);
    }

    public void gauge(String name, double value, double sampleRate) {
        this._gauge(name, value, sampleRate, null);
    }

     /**
     * sends a custom gauge metric
     *
     * @param name       The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
     * @param value      The latest value to set for the metric
     * @param labels       adds metadata to a metric. Can be specified as an array of strings with key-value pairs
     * formatted with a colon separator e.g. ['account:12345', 'env:development']. Cannot contain '#' or '|'
     *
     * sampleRate is set to 1
     *
     * @return void
     */
    public void gauge(String name, long value, String[] labels) {
        this._gauge(name, value, 1, labels);
    }

    public void gauge(String name, double value, String[] labels) {
        this._gauge(name, value, 1, labels);
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
