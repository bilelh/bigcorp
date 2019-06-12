package com.training.springcore.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Measure {
    /**
     * Measure id
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Measure instant
     */
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant instant;

    /**
     * Measure valueInWatt
     */
    @Column(nullable = false)
    private Integer valueInWatt;

    /**
     * Measure captor
     */
    @ManyToOne(optional = false)
    private Captor captor;

    @Version
    private int version;

    public Measure() {    }

    public Measure(Long id, Instant instant, Integer valueInWatt, Captor captor) {
        this.id = id;
        this.instant = instant;
        this.valueInWatt = valueInWatt;
        this.captor = captor;
    }

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

    public Long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
