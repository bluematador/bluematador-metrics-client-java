package com.bluematador.app;
import com.bluematador.app.BlueMatadorClient;
import com.timgroup.statsd.StatsExporter;

/**
 * The BlueMatadorClientBuilder is used to construct the BlueMatadorClient
 */
public class BlueMatadorClientBuilder {
    /**
     * exporter is an instance of the StatsExporter class which extends the DataDog StatsDClient
     * The custom metrics are formatted and then sent to the Blue Matador agent through 
     * this class.
     */
    private StatsExporter exporter;

    /**
     * specifies the host to send the custom metrics to
     */
    private String host;

    /**
     * specifies the port to send the custom metrics to
     */
    private int port;

    /**
     * specifies a prefix to add to each metric name
     */
    private String prefix;

    /**
     * The constructor for this class
     * 
     * @return an instance of the BlueMatadorClientBuilder
     */
    public BlueMatadorClientBuilder() {
        this.host = System.getenv("BLUEMATADOR_AGENT_HOST") != null ? System.getenv("BLUEMATADOR_AGENT_HOST") : "127.0.0.1";
        this.port = System.getenv("BLUEMATADOR_AGENT_PORT") != null ? Integer.parseInt(System.getenv("BLUEMATADOR_AGENT_PORT")) : 8767;
        this.prefix = null;
    }

    /**
     * withHost chains onto the class constructor. Allows host specification for the client
     * 
     * @param host specifies the host to send the custom metrics to. 
     * If this method is not called, the host defaults to the BLUEMATADOR_AGENT_HOST environmental
     * variable or 127.0.0.1
     * 
     * @return an instance of the BlueMatadorClientBuilder
     */
    public BlueMatadorClientBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * withPort chains onto the class constructor. Allows port specification for the client
     * 
     * @param port specifies the port to send the custom metrics to. 
     * If this method is not called, the port defaults to the BLUEMATADOR_AGENT_PORT environmental
     * variable or 8767
     * 
     * @return an instance of the BlueMatadorClientBuilder
     */
    public BlueMatadorClientBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * withPrefix chains onto the class constructor. Allows specification of a prefix to apply to each metric name the client sends.
     * 
     * @param prefix specifies the string to prefix each metric name with.
     * If this method is not called, no prefix will be applied to metric names.
     * 
     * @return an instance of the BlueMatadorClientBuilder
     */
    public BlueMatadorClientBuilder withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * build is to chained onto the class constructor after all other chained methods.
     * constructs an instance of the StatsExporter with the global 
     * class connection variables i.e host, port, prefix
     * 
     * @return an instance of the BlueMatadorClient
     */
    public BlueMatadorClient build() throws Exception {
        this.exporter = new StatsExporter(this.host, this.port, this.prefix);
        

        BlueMatadorClient client = new BlueMatadorClient();
        client.exporter = this.exporter;

        return client;
    }
}