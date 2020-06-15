package com.test;

import com.bluematador.BlueMatadorClientBuilder;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



// This file is soley used to test sending custom metrics to the Agent.
// It will not be included when we are ready to publish to Maven

public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);

    public static void main(String[] args) throws Exception {

        var bluematador = new BlueMatadorClientBuilder()
            .withHost("localhost")
            .withPort(8767)
            .withPrefix("app")
            .build();


            bluematador.count("counter.1");
            bluematador.count("counter.2", 1);
            bluematador.count("counter.3", 2, 1);
            bluematador.count("counter.4", 1, new String[]{"Env:dev", "account:122321", "tag3:hello", "tag4:yoyo", "tag5:jojo"});
            bluematador.count("counter.5", 1, 2, new String[]{"Env:dev", "account:122321"});
            bluematador.gauge("gauge.1", 23);
            bluematador.gauge("gauge.2", 32, 1);
            bluematador.gauge(":gauge|3", 23, 1, new String[]{"#Env:dev", "|account:#122321"});
            bluematador.gauge("gauge|4", 23, new String[]{"Env:dev", "account:122321"});
        try {
            bluematador.close();
        } catch(Exception e) {
            logger.error(e);
        }
    }
}