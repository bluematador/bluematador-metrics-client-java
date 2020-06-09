package com.bluematador.app;
import com.bluematador.app.BlueMatadorClient;
import com.timgroup.statsd.StatsExporter;

public class BlueMatadorClientBuilder {
    private StatsExporter exporter;
    private String host;
    private int port;
    private String prefix;

    public BlueMatadorClientBuilder() {
        this.host = System.getenv("BLUEMATADOR_AGENT_HOST") != null ? System.getenv("BLUEMATADOR_AGENT_HOST") : "127.0.0.1";
        this.port = System.getenv("BLUEMATADOR_AGENT_PORT") != null ? Integer.parseInt(System.getenv("BLUEMATADOR_AGENT_PORT")) : 8767;
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

    public BlueMatadorClient build() throws Exception {
        this.exporter = new StatsExporter(this.host, this.port, this.prefix);
        

        BlueMatadorClient client = new BlueMatadorClient();
        client.exporter = this.exporter;

        return client;
    }
}