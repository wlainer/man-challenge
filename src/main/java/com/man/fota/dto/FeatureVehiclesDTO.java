package com.man.fota.dto;

import java.util.ArrayList;
import java.util.List;

public class FeatureVehiclesDTO {

    private List<VehicleDTO> compatibleVehicles = new ArrayList<>();
    private List<VehicleDTO> incompatibleVehicles = new ArrayList<>();

    public List<VehicleDTO> getCompatibleVehicles() {
        return compatibleVehicles;
    }

    public void setCompatibleVehicles(List<VehicleDTO> compatibleVehicles) {
        this.compatibleVehicles = compatibleVehicles;
    }

    public List<VehicleDTO> getIncompatibleVehicles() {
        return incompatibleVehicles;
    }

    public void setIncompatibleVehicles(List<VehicleDTO> incompatibleVehicles) {
        this.incompatibleVehicles = incompatibleVehicles;
    }
}
