package com.bluematador.app;
import  com.bluematador.app.BlueMatadorClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);

    public static void main(String[] args) throws Exception {

        var bluematador = new BlueMatadorClient();

        try {
            bluematador.count("counter.1");
        } catch(Exception e) {
            logger.error(e);
        }
        try {
            bluematador.count("counter.2", 1);
        } catch(Exception e) {
            logger.error(e);
        }
        try {
            bluematador.count("counter.3", 2, 1);
        } catch(Exception e) {
            logger.error(e);
        }
        try {
            bluematador.count("counter.4", 1, new String[]{"Env:dev", "account:122321"});
        } catch(Exception e) {
            logger.error(e);
        }
        try {
            bluematador.count("counter.5", 1, 1, new String[]{"Env:dev", "account:122321"});
        } catch(Exception e) {
            logger.error(e);
        }
        try {
            bluematador.gauge("gauge.1", 23.5);
        } catch(Exception e) {
            logger.error(e);
        }
        try {
            bluematador.gauge("gauge.2", 32.23253, 1);
        } catch(Exception e) {
            logger.error(e);
        }
        try {
            bluematador.gauge("gauge.3", 23, 1, new String[]{"Env:dev", "account:122321"});
        } catch(Exception e) {
            logger.error(e);
        }
        try {
            bluematador.gauge("gauge.4", 23.2, new String[]{"Env:dev", "account:122321"});
        } catch(Exception e) {
            logger.error(e);
        }

        try {
            bluematador.close();
        } catch(Exception e) {
            logger.error(e);
        }
    }
}