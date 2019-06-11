package com.training.springcore.model;

import java.util.Objects;
import java.util.UUID;

public class Captor {
    /**
     * Captor id
     */
    private String id = UUID.randomUUID().toString();

    /**
     * Captor name
     */
    private String name;

    /**
     * Captor powerSource
     */
    private PowerSource powerSource;

    /**
     * Captor site
     */
    private Site site;

    @Deprecated
    public Captor() {
        // Use for serializer or deserializer
    }

    /**
     * Constructor to use with required property
     * @param name
     */
    public Captor(String name ) {
        this.name = name;
    }

    /**
     * Constructor to use with required property
     * @param name
     * @param site
     */
    public Captor(String name , Site site) {
        this.name = name;
        this.site = site;
    }

    /**
     * Constructor to use with required property
     * @param name
     * @param powerSource
     * @param site
     */
    public Captor(String name , PowerSource powerSource , Site site) {
        this.name = name;
        this.powerSource = powerSource;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PowerSource getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(PowerSource powerSource) {
        this.powerSource = powerSource;
    }

    public Site getSite() { return site; }

    public void setSite(Site site) { this.site = site; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Captor site = (Captor) o;
        return Objects.equals(name, site.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Captor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
