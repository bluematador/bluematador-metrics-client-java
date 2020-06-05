package com.bluematador.app;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import com.bluematador.app.Sanitizer;


public class BlueMatadorClient extends Sanitizer {
    private static final Logger logger = LogManager.getLogger(BlueMatadorClient.class);
    private StatsDClient client; 

    public BlueMatadorClient(String host, int port) {
        this.client = new NonBlockingStatsDClientBuilder()
            .hostname(host)
            .port(port)
            .build();
    }

    public BlueMatadorClient() {
        this.client = new NonBlockingStatsDClientBuilder()
        .hostname("localhost")
        .port(8767)
        .build();
    }

    public BlueMatadorClient(String host) {
        this.client = new NonBlockingStatsDClientBuilder()
        .hostname(host)
        .port(8767)
        .build();
    }

    public BlueMatadorClient(int port) {
        this.client = new NonBlockingStatsDClientBuilder()
        .hostname("localhost")
        .port(port)
        .build();
    }

    private void bluematadorCount(String name, long value, double sampleRate, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            this.client.count(name, value, sampleRate, tags);
        }
    }

    private void bluematadorGauge(String name, long value, double sampleRate, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            this.client.recordGaugeValue(name, value, sampleRate, tags);
        }
    }

    public void count(String name, long value, double sampleRate, String[] tags) throws Exception {
        this.bluematadorCount(name, value, sampleRate, tags);
    }

    public void count(String name) throws Exception {
        this.bluematadorCount(name, 1, 1, new String[]{""});
    }

    public void count(String name, long value) throws Exception {
        this.bluematadorCount(name, value, 1, new String[]{""});
    }

    public void count(String name, long value, double sampleRate) throws Exception {
        this.bluematadorCount(name, value, sampleRate, new String[]{""});
    }

    public void count(String name, String[] tags) throws Exception {
        this.bluematadorCount(name, 1, 1, tags);
    }

    public void count(String name, long value, String[] tags) throws Exception {
        this.bluematadorCount(name, value, 1, tags);
    }

    public void gauge(String name, long value, double sampleRate, String[] tags) throws Exception {
        this.bluematadorGauge(name, value, sampleRate, tags);
    }

    public void gauge(String name, long value) throws Exception {
        this.bluematadorGauge(name, value, 1, new String[]{""});   
    }

    public void gauge(String name, long value, double sampleRate) throws Exception {
        this.bluematadorGauge(name, value, sampleRate, new String[]{""});
    }

    public void gauge(String name, long value, String[] tags) throws Exception {
        this.bluematadorGauge(name, value, 1, tags);
    }

    public void close() throws Exception {
        this.client.close();
    }

}