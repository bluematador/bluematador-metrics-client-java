package com.bluematador.app;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sanitizer {
    private static final Logger logger = LogManager.getLogger(Sanitizer.class);

    public boolean sanitize(String name, String[] tags) throws Exception {
        var clean = this.checkName(name);
        if(clean) {
            clean = this.checkTags(tags);
        }
        return clean;
    }

    private boolean checkName(String name) throws Exception {
        if(name.contains(":")) {
           throw new Exception("Illegal character : found in metric name");
        }
        if(name.contains("|")) {
           throw new Exception("Illegal character | found in metric name");
        }
        return true;
    }

    private boolean checkTags(String[] tags) throws Exception {
        for(int i = 0; i < tags.length; i++) {
            if(tags[i].contains("#")) {
                throw new Exception("Illegal character # found in metric tags");
            }
            if(tags[i].contains("|")) {
                throw new Exception("Illegal character | found in metric tags");
            }
            if(tags[i].contains("@")) {
                throw new Exception("Illegal character @ found in metric tags");
            }
        }
        return true;
    }

    // private boolean checkSampleRate(double sampleRate) throws Exception {
    //     if(Math.max(0, Math.min(1.0, sampleRate))) {

    //     }
    // }
}