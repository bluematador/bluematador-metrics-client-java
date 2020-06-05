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

    public void count(String name, double value, double sampleRate, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            this.client.count(name, value, sampleRate, tags);
        }
    }

    public void count(String name) throws Exception {
        if(this.sanitize(name, new String[]{""})) {
            this.client.increment(name);
        }  
    }

    public void count(String name, double value) throws Exception {
        if(this.sanitize(name, new String[]{""})) {
            this.client.count(name, value, 1, new String[]{""});
        }
    }

    public void count(String name, double value, double sampleRate) throws Exception {
        if(this.sanitize(name, new String[]{""})) {
            this.client.count(name, value, sampleRate, new String[]{""});
        }
    }

    public void count(String name, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            this.client.count(name, 1, 1, tags);
        }
    }

    public void count(String name, double value, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            this.client.count(name, value, 1, tags);
        }
    }

    public void gauge(String name, double value, double sampleRate, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            this.client.recordGaugeValue(name, value, sampleRate, tags);
        }
    }

    public void gauge(String name, double value) throws Exception {
        if(this.sanitize(name, new String[]{""})) {
            this.client.recordGaugeValue(name, value, 1, new String[]{""});   
        }
    }

    public void gauge(String name, double value, double sampleRate) throws Exception {
        if(this.sanitize(name, new String[]{""})) {
            this.client.recordGaugeValue(name, value, sampleRate, new String[]{""});
        }
    }

    public void gauge(String name, double value, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            this.client.recordGaugeValue(name, value, 1, tags);
        }
    }

    public void close() throws Exception {
        this.client.close();
    }

}