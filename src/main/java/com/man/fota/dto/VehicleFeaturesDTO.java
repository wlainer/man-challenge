package com.man.fota.dto;

import java.util.ArrayList;
import java.util.List;

public class VehicleFeaturesDTO {

    private List<FeatureDTO> compatible = new ArrayList<>();
    private List<FeatureDTO> incompatible = new ArrayList<>();

    public List<FeatureDTO> getCompatible() {
        return compatible;
    }

    public void setCompatible(List<FeatureDTO> compatible) {
        this.compatible = compatible;
    }

    public List<FeatureDTO> getIncompatible() {
        return incompatible;
    }

    public void setIncompatible(List<FeatureDTO> incompatible) {
        this.incompatible = incompatible;
    }

}
