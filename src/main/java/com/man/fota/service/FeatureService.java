package com.man.fota.service;

import com.man.fota.dto.FeatureDTO;
import com.man.fota.dto.FeatureVehiclesDTO;
import com.man.fota.dto.VehicleDTO;
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
public class FeatureService extends FotaService {

    private final Logger log = LoggerFactory.getLogger(FeatureService.class);

    public Page getAllFeatures(Pageable pageable) {
        Page<Feature> page = featureRepository.findAll(pageable);
        Page<FeatureDTO> pageDTO = page.map(feature -> FeatureMapper.INSTANCE.featureToFeatureDTO(feature));
        return pageDTO;
    }

    public List<VehicleDTO> getAllFeatureVINCompatible(String featureID) {
        Feature feature = getFeature(featureID);

        List<Vehicle> compatibleVehicles = new ArrayList<>();
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        for (Vehicle vehicle : allVehicles)
            if (isCompatible(vehicle, feature))
                compatibleVehicles.add(vehicle);

        return compatibleVehicles.stream()
            .map(vehicle -> VehicleMapper.INSTANCE.vehicleToVehicleDTO(vehicle))
            .collect(Collectors.toList());
    }


    public List<VehicleDTO> getAllFeatureVINIncompatible(String featureID) {
        Feature feature = getFeature(featureID);

        List<Vehicle> incompatibleVehicles = new ArrayList<>();
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        for (Vehicle vehicle : allVehicles)
            if (!isCompatible(vehicle, feature))
                incompatibleVehicles.add(vehicle);

        return incompatibleVehicles.stream()
            .map(vehicle -> VehicleMapper.INSTANCE.vehicleToVehicleDTO(vehicle))
            .collect(Collectors.toList());
    }

    public FeatureVehiclesDTO getAllFeatureVINs(String featureID) {
        Feature feature = getFeature(featureID);

        FeatureVehiclesDTO featureVehiclesDTO = new FeatureVehiclesDTO();
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        for (Vehicle vehicle : allVehicles)
            if (isCompatible(vehicle, feature))
                featureVehiclesDTO.getCompatibleVehicles().add(VehicleMapper.INSTANCE.vehicleToVehicleDTO(vehicle));
            else
                featureVehiclesDTO.getIncompatibleVehicles().add(VehicleMapper.INSTANCE.vehicleToVehicleDTO(vehicle));

        return featureVehiclesDTO;
    }

    private Feature getFeature(String featureID) {
        Feature feature = featureRepository.findByIdentification(featureID);
        if (feature == null)
            throw new IllegalArgumentException("Invalid feature");

        return feature;
    }
}
