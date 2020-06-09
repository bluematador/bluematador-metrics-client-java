package com.bluematador.app;
import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.NonBlockingStatsDClient;

public class StatsExporter extends NonBlockingStatsDClient {

    public StatsExporter(String host, int port, String prefix) throws Exception {
        super(prefix, 4096, null, null, NonBlockingStatsDClientBuilder.staticAddressResolution(host, port), null, 100, 1096, 1400, null, 512, 1, 1, true, false, 0);
    }

}