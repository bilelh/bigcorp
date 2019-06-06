package com.training.springcore.model;

import java.time.Instant;
import java.util.Objects;

public class Measure {
    /**
     * Measure instant
     */
    private Instant instant;

    /**
     * Measure valueInWatt
     */
    private Integer valueInWatt;

    /**
     * Measure captor
     */
    private Captor captor;

    public Measure(Instant instant, Integer valueInWatt, Captor captor) {
        this.instant = instant;
        this.valueInWatt = valueInWatt;
        this.captor = captor;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Integer getValueInWatt() {
        return valueInWatt;
    }

    public void setValueInWatt(Integer valueInWatt) {
        this.valueInWatt = valueInWatt;
    }

    public Captor getCaptor() {
        return captor;
    }

    public void setCaptor(Captor captor) {
        this.captor = captor;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "instant=" + instant +
                ", valueInWatt=" + valueInWatt +
                ", captor=" + captor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measure)) return false;
        Measure measure = (Measure) o;
        return Objects.equals(getInstant(), measure.getInstant()) &&
                Objects.equals(getValueInWatt(), measure.getValueInWatt()) &&
                Objects.equals(getCaptor(), measure.getCaptor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstant(), getValueInWatt(), getCaptor());
    }
}
