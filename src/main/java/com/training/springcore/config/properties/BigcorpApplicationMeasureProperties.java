package com.training.springcore.config.properties;

import org.springframework.stereotype.Component;


public class BigcorpApplicationMeasureProperties {

    private int defaultFixed;
    private int defaultSimulated;
    private int defaultReal;

        // GETTERS AND SETTERS
    public int getDefaultFixed() {
        return defaultFixed;
    }

    public void setDefaultFixed(int defaultFixed) {
        this.defaultFixed = defaultFixed;
    }

    public int getDefaultSimulated() {
        return defaultSimulated;
    }

    public void setDefaultSimulated(int defaultSimulated) {
        this.defaultSimulated = defaultSimulated;
    }

    public int getDefaultReal() {
        return defaultReal;
    }

    public void setDefaultReal(int defaultReal) {
        this.defaultReal = defaultReal;
    }
}
