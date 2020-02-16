package com.man.fota.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(
        name = "FEATURE",
        indexes = {
                @Index(name = "idx_feature_identification", columnList = "identification", unique = true)
        }
)
public class Feature extends BaseEntity {

    private String identification;
    private String requiredSoftware;
    private String prohibitedSoftware;
    private String requiredHardware;
    private String prohibitedHardware;

    @Column(name = "identification", nullable = false)
    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getRequiredSoftware() {
        return requiredSoftware;
    }

    public void setRequiredSoftware(String requiredSoftware) {
        this.requiredSoftware = requiredSoftware;
    }

    public String getProhibitedSoftware() {
        return prohibitedSoftware;
    }

    public void setProhibitedSoftware(String prohibitedSoftware) {
        this.prohibitedSoftware = prohibitedSoftware;
    }

    public String getRequiredHardware() {
        return requiredHardware;
    }

    public void setRequiredHardware(String requiredHardware) {
        this.requiredHardware = requiredHardware;
    }

    public String getProhibitedHardware() {
        return prohibitedHardware;
    }

    public void setProhibitedHardware(String prohibitedHardware) {
        this.prohibitedHardware = prohibitedHardware;
    }
}
