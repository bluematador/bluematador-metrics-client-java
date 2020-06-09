package com.bluematador.app;
import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.NonBlockingStatsDClient;

public class StatsExporter extends NonBlockingStatsDClient {

    public StatsExporter(String host, int port, String prefix) throws Exception {
        super(prefix, 4096, new String[]{""}, null, NonBlockingStatsDClientBuilder.staticAddressResolution(host, port), NonBlockingStatsDClientBuilder.staticAddressResolution(host, port), 100, 1096, 1400, null, 512, 1, 1, false, false, 0);
    }

    @Override
    StringBuilder tagString(final String[] tags, StringBuilder builder) {
        if ((tags == null) || (tags.length == 0)) {
            return builder;
        }
        builder.append("|#");

        for (int n = tags.length - 1; n >= 0; n--) {
            builder.append(tags[n]);
            if (n > 0) {
                builder.append('#');
            }
        }
        return builder;
    }
}