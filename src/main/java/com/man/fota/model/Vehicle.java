package com.man.fota.model;

import javax.persistence.*;

@Entity
@Table(
    name = "VEHICLE",
    indexes = {
        @Index(name = "idx_vehicle_identification", columnList = "identification", unique = true)
    }
)
public class Vehicle extends BaseEntity {

    private String identification;
    private String installedSoftwareHardware;


    @Column(name = "identification", nullable = false)
    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Lob
    public String getInstalledSoftwareHardware() {
        return installedSoftwareHardware;
    }

    public void setInstalledSoftwareHardware(String installedSoftwareHardware) {
        this.installedSoftwareHardware = installedSoftwareHardware;
    }

}
