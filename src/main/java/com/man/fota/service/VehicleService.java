package com.man.fota.service;

import com.man.fota.dto.FeatureDTO;
import com.man.fota.dto.VehicleDTO;
import com.man.fota.dto.VehicleFeaturesDTO;
import com.man.fota.mapper.FeatureMapper;
import com.man.fota.mapper.VehicleMapper;
import com.man.fota.model.Feature;
import com.man.fota.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class VehicleService extends FotaService {

    private final Logger log = LoggerFactory.getLogger(VehicleService.class);

    public Page getAllVehicles(Pageable pageable) {
        Page<Vehicle> page = vehicleRepository.findAll(pageable);
        Page<VehicleDTO> pageDTO = page.map(vehicle -> VehicleMapper.INSTANCE.vehicleToVehicleDTO(vehicle));
        return pageDTO;
    }

    public List<FeatureDTO> getAllVINInstallableFeatures(String vin) {
        Vehicle vehicle = getVehicle(vin);

        List<Feature> compatibleFeatures = new ArrayList<>();
        List<Feature> allFeatures = featureRepository.findAll();
        for (Feature feature : allFeatures)
            if (isCompatible(vehicle, feature))
                compatibleFeatures.add(feature);


        return compatibleFeatures.stream()
            .map(feature -> FeatureMapper.INSTANCE.featureToFeatureDTO(feature))
            .collect(Collectors.toList());
    }


    public List<FeatureDTO> getAllVINIncompatibleFeatures(String vin) {
        Vehicle vehicle = getVehicle(vin);

        List<Feature> incompatibleFeatures = new ArrayList<>();
        List<Feature> allFeatures = featureRepository.findAll();
        for (Feature feature : allFeatures)
            if (!isCompatible(vehicle, feature))
                incompatibleFeatures.add(feature);


        return incompatibleFeatures.stream()
            .map(feature -> FeatureMapper.INSTANCE.featureToFeatureDTO(feature))
            .collect(Collectors.toList());
    }

    public VehicleFeaturesDTO getAllVINFeatures(String vin) {
        Vehicle vehicle = getVehicle(vin);

        VehicleFeaturesDTO vehicleFeaturesDTO = new VehicleFeaturesDTO();
        List<Feature> allFeatures = featureRepository.findAll();
        for (Feature feature : allFeatures)
            if (isCompatible(vehicle, feature))
                vehicleFeaturesDTO.getCompatible().add(FeatureMapper.INSTANCE.featureToFeatureDTO(feature));
            else
                vehicleFeaturesDTO.getIncompatible().add(FeatureMapper.INSTANCE.featureToFeatureDTO(feature));

        return vehicleFeaturesDTO;
    }

    private Vehicle getVehicle(String vin) {
        Vehicle vehicle = vehicleRepository.findByIdentification(vin);
        if (vehicle == null)
            throw new IllegalArgumentException("Invalid VIN");

        return vehicle;
    }
}
