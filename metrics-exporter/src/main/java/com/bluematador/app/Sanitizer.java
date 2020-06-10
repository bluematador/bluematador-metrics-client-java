package com.bluematador.app;

/**
 * The Sanitizer class takes in metric string parameters and checks against illegal characters
 */
public class Sanitizer {

    /**
     * sanitize sends the metric name and tags to functions that check for illegal characters
     * 
     * @return the validity of the metric is returned. True for valid, false for invalid
     */
    public boolean sanitize(String name, String[] tags) throws Exception {
        var clean = this.checkName(name);
        if(clean) {
            clean = this.checkTags(tags);
        }
        return clean;
    }

    /**
     * checkName checks the metric name for illegal characters
     * 
     * @return the validity of the metric name is returned. True for valid, false for invalid 
     */
    private boolean checkName(String name) throws Exception {
        if(name.contains(":")) {
           throw new Exception("Illegal character : found in metric name");
        }
        if(name.contains("|")) {
           throw new Exception("Illegal character | found in metric name");
        }
        return true;
    }

    /**
     * checkTags checks the metric tags for illegal characters
     * 
     * @return the validity of the metric tags is returned. True for valid, false for invalid 
     */
    private boolean checkTags(String[] tags) throws Exception {
        for(int i = 0; i < tags.length; i++) {
            if(tags[i].contains("#")) {
                throw new Exception("Illegal character # found in metric tags");
            }
            if(tags[i].contains("|")) {
                throw new Exception("Illegal character | found in metric tags");
            }
        }
        return true;
    }

}