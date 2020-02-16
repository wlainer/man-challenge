package com.man.fota.service;

import com.man.fota.enums.RequirementType;
import com.man.fota.model.Feature;
import com.man.fota.model.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FotaServiceTest {

    private static FotaService service;

    @BeforeAll
    public static void setup() {
        service = new FotaService();
    }

    @Test
    void verifyRequirements_ShouldReturnTrueWhenHasAllRequirements() {
        String installed = "A B C";
        RequirementType type = RequirementType.MUST_HAVE;
        String[] requirement = new String[]{"A", "C"};

        boolean isCompatible = service.verifyRequirements(installed, type, requirement);

        Assertions.assertTrue(isCompatible);
    }

    @Test
    void verifyRequirements_ShouldReturnFalseWhenDoesNotHaveAllRequirements() {
        String installed = "A B";
        RequirementType type = RequirementType.MUST_HAVE;
        String[] requirement = new String[]{"A", "C"};

        boolean isCompatible = service.verifyRequirements(installed, type, requirement);

        Assertions.assertFalse(isCompatible);
    }

    @Test
    void verifyRequirements_ShouldReturnTrueWhenDoesNotHaveAProhibitedRequirement() {
        String installed = "A B C";
        RequirementType type = RequirementType.MUST_NOT_HAVE;
        String[] requirement = new String[]{"D"};

        boolean isCompatible = service.verifyRequirements(installed, type, requirement);

        Assertions.assertTrue(isCompatible);
    }

    @Test
    void verifyRequirements_ShouldReturnFalseWhenHaveAProhibitedRequirement() {
        String installed = "A B C";
        RequirementType type = RequirementType.MUST_NOT_HAVE;
        String[] requirement = new String[]{"C"};

        boolean isCompatible = service.verifyRequirements(installed, type, requirement);

        Assertions.assertFalse(isCompatible);
    }

    @Test
    void isCompatible_ShouldReturnTrueForACompatibleFeature() {
        Vehicle vehicle = new Vehicle();
        vehicle.setIdentification("vehicle 1");
        vehicle.setInstalledSoftwareHardware("A B C D");

        Feature feature = new Feature();
        feature.setIdentification("feature A");
        feature.setProhibitedHardware("E");
        feature.setProhibitedSoftware("F");
        feature.setRequiredHardware("A B");
        feature.setRequiredSoftware("C D");

        boolean isCompatible = service.isCompatible(vehicle, feature);

        Assertions.assertTrue(isCompatible);
    }

    @Test
    void isCompatible_ShouldReturnFalseForAnIncompatibleFeature() {
        Vehicle vehicle = new Vehicle();
        vehicle.setIdentification("vehicle 1");
        vehicle.setInstalledSoftwareHardware("A B C D");

        Feature feature = new Feature();
        feature.setIdentification("feature A");
        feature.setProhibitedHardware("A");
        feature.setProhibitedSoftware("F");
        feature.setRequiredHardware("B");
        feature.setRequiredSoftware("C D");

        boolean isCompatible = service.isCompatible(vehicle, feature);

        Assertions.assertFalse(isCompatible);
    }

    @Test
    void isCompatible_ShouldReturnFalseForAnIncompatibleProhibitedHardware() {
        Vehicle vehicle = new Vehicle();
        vehicle.setIdentification("vehicle 1");
        vehicle.setInstalledSoftwareHardware("A B C D");

        Feature feature = new Feature();
        feature.setIdentification("feature A");
        feature.setProhibitedHardware("A");
        feature.setProhibitedSoftware("F");
        feature.setRequiredHardware("A B");
        feature.setRequiredSoftware("C D");

        boolean isCompatible = service.isCompatible(vehicle, feature);

        Assertions.assertFalse(isCompatible);
    }

    @Test
    void isCompatible_ShouldReturnFalseForAnIncompatibleProhibitedSoftware() {
        Vehicle vehicle = new Vehicle();
        vehicle.setIdentification("vehicle 1");
        vehicle.setInstalledSoftwareHardware("A B C D");

        Feature feature = new Feature();
        feature.setIdentification("feature A");
        feature.setProhibitedHardware("E");
        feature.setProhibitedSoftware("A");
        feature.setRequiredHardware("A B");
        feature.setRequiredSoftware("C D");

        boolean isCompatible = service.isCompatible(vehicle, feature);

        Assertions.assertFalse(isCompatible);
    }

    @Test
    void isCompatible_ShouldReturnFalseForAnIncompatibleRequiredHardware() {
        Vehicle vehicle = new Vehicle();
        vehicle.setIdentification("vehicle 1");
        vehicle.setInstalledSoftwareHardware("A B C D");

        Feature feature = new Feature();
        feature.setIdentification("feature A");
        feature.setProhibitedHardware("E");
        feature.setProhibitedSoftware("F");
        feature.setRequiredHardware("G B");
        feature.setRequiredSoftware("C D");

        boolean isCompatible = service.isCompatible(vehicle, feature);

        Assertions.assertFalse(isCompatible);
    }

    @Test
    void isCompatible_ShouldReturnFalseForAnIncompatibleRequiredSoftware() {
        Vehicle vehicle = new Vehicle();
        vehicle.setIdentification("vehicle 1");
        vehicle.setInstalledSoftwareHardware("A B C D");

        Feature feature = new Feature();
        feature.setIdentification("feature A");
        feature.setProhibitedHardware("E");
        feature.setProhibitedSoftware("F");
        feature.setRequiredHardware("A B");
        feature.setRequiredSoftware("G D");

        boolean isCompatible = service.isCompatible(vehicle, feature);

        Assertions.assertFalse(isCompatible);
    }
}