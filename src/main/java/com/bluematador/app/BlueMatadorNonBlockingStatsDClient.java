package com.timgroup.statsd;


/**
 * BlueMatadorNonBlockingStatsDClient extends Datadogs NonBlockingStatsdClient
 * to allow custom functionality
 */
public class BlueMatadorNonBlockingStatsDClient extends NonBlockingStatsDClient {

    // need to find the right return type here
    private NonBlockingStatsDClient.staticAddressResolution clientAddress() {
        return NonBlockingStatsDClientBuilder.staticAddressResolution(host, port);
    }

    /**
     * constructs the BlueMatadorNonBlockingStatsDClient and super class
     * 
     * @param host 
     * @param port
     * @param prefix
     * 
     */
    public BlueMatadorNonBlockingStatsDClient(String host, int port, String prefix) throws Exception {
        super(prefix, 4096, null, null, this.clientAddress, this.clientAddress, 100, 1096, 1400, null, 512, 1, 1, false, false, 0);
    }
    
    /**
     * tagString overrides the tagString method on the super class to
     * properly format the tag string for the Blue Matador agent.
     * 
     * @return  the formatted tag string to send with the metric
     */
    @Override
    StringBuilder tagString(final String[] tags, StringBuilder builder) {
        if ((tags == null) || (tags.length == 0)) {
            return builder;
        }
        builder.append("|#" + tags[0]);

        for (int n = 1; n < tags.length; n++) {
            builder.append('#' + tags[n]);
        }
        return builder;
    }
}