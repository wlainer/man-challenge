package com.man.fota.service;

import com.man.fota.enums.RequirementType;
import com.man.fota.model.Feature;
import com.man.fota.model.Vehicle;
import com.man.fota.repository.FeatureRepository;
import com.man.fota.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class FotaService {

    @Autowired
    protected FeatureRepository featureRepository;

    @Autowired
    protected VehicleRepository vehicleRepository;

    protected boolean isCompatible(Vehicle vehicle, Feature feature) {
        String[] prohibited = Arrays.asList(feature.getProhibitedHardware().split(" "),
            feature.getProhibitedSoftware().split(" ")).stream()
            .flatMap(Arrays::stream)
            .toArray(String[]::new);

        boolean isCompatible = verifyRequirements(vehicle.getInstalledSoftwareHardware(),
            RequirementType.MUST_NOT_HAVE, prohibited);

        if (!isCompatible)
            return false;

        String[] required = Arrays.asList(feature.getRequiredHardware().split(" "),
            feature.getRequiredSoftware().split(" ")).stream()
            .flatMap(Arrays::stream)
            .toArray(String[]::new);

        isCompatible = verifyRequirements(vehicle.getInstalledSoftwareHardware(),
            RequirementType.MUST_HAVE, required);

        if (!isCompatible)
            return false;

        return true;
    }

    protected boolean verifyRequirements(String installed, RequirementType requirementType, String[] requirements) {
        String[] arrInstalled = installed.split(" ");
        Map<String, Integer> installedSoftwareAndHardware = new HashMap<>();
        for (String piece : arrInstalled)
            installedSoftwareAndHardware.put(piece, 0);

        for (String piece : requirements) {
            boolean contains = installedSoftwareAndHardware.containsKey(piece);
            if (requirementType.equals(RequirementType.MUST_HAVE) && !contains)
                return false;
            else if (requirementType.equals(RequirementType.MUST_NOT_HAVE) && contains)
                return false;
        }

        return true;
    }
}
