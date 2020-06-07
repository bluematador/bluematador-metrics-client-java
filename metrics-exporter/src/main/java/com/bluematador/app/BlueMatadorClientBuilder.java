package com.bluematador.app;
import com.bluematador.app.BlueMatadorClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

public class BlueMatadorClientBuilder {
    private NonBlockingStatsDClient exporter;
    private String host;
    private int port;
    private String prefix;

    public BlueMatadorClientBuilder() {
        this.host = "Localhost";
        this.port = 8767;
        this.prefix = null;
    }

    public BlueMatadorClientBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public BlueMatadorClientBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public BlueMatadorClientBuilder withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public BlueMatadorClient build() {
       this.exporter = new NonBlockingStatsDClientBuilder()
        .hostname(this.host)
        .port(this.port)
        .prefix(this.prefix)
        .enableTelemetry(false)
        .build();

        BlueMatadorClient client = new BlueMatadorClient();
        client.exporter = this.exporter;

        return client;
    }
}