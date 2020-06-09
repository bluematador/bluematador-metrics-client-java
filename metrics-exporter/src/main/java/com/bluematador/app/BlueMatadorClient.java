package com.bluematador.app;
import com.timgroup.statsd.StatsExporter;
import com.bluematador.app.Sanitizer;
import java.util.concurrent.ThreadLocalRandom;


public class BlueMatadorClient extends Sanitizer {
    public StatsExporter exporter;

    private void prepareCount(String name, long value, double sampleRate, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            if(!this.isInvalidSample(Math.max(0, Math.min(1.0, sampleRate)))) {
                exporter.count(name, value / Math.max(0, Math.min(1.0, sampleRate)), tags);
            }
        }
    }

    private void prepareGauge(String name, long value, double sampleRate, String[] tags) throws Exception {
        if(this.sanitize(name, tags)) {
            if(!this.isInvalidSample(Math.max(0, Math.min(1.0, sampleRate)))) {
                exporter.recordGaugeValue(name, value, tags);
            }
        }
    }

    private boolean isInvalidSample(double sampleRate) {
        return sampleRate != 1 && ThreadLocalRandom.current().nextDouble() > sampleRate;
    }

    public void count(String name, long value, double sampleRate, String[] tags) throws Exception {
        this.prepareCount(name, value, sampleRate, tags);
    }

    public void count(String name) throws Exception {
        this.prepareCount(name, 1, 1, new String[]{""});
    }

    public void count(String name, long value) throws Exception {
        this.prepareCount(name, value, 1, new String[]{""});
    }

    public void count(String name, long value, double sampleRate) throws Exception {
        this.prepareCount(name, value, sampleRate, new String[]{""});
    }

    public void count(String name, String[] tags) throws Exception {
        this.prepareCount(name, 1, 1, tags);
    }

    public void count(String name, long value, String[] tags) throws Exception {
        this.prepareCount(name, value, 1, tags);
    }

    public void gauge(String name, long value, double sampleRate, String[] tags) throws Exception {
        this.prepareGauge(name, value, sampleRate, tags);
    }

    public void gauge(String name, long value) throws Exception {
        this.prepareGauge(name, value, 1, new String[]{""});   
    }

    public void gauge(String name, long value, double sampleRate) throws Exception {
        this.prepareGauge(name, value, sampleRate, new String[]{""});
    }

    public void gauge(String name, long value, String[] tags) throws Exception {
        this.prepareGauge(name, value, 1, tags);
    }

    public void close() throws Exception {
        exporter.close();
    }

}