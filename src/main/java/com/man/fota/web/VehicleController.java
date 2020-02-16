package com.man.fota.web;

import com.man.fota.dto.FeatureDTO;
import com.man.fota.dto.VehicleDTO;
import com.man.fota.dto.VehicleFeaturesDTO;
import com.man.fota.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fota/vehicles")
public class VehicleController extends FotaController {

    @Autowired
    private VehicleService service;

    @GetMapping()
    public ResponseEntity<Page<VehicleDTO>> getAllVehicles(@PageableDefault(value = 20, page = 0) Pageable pageable) {
        Page page = service.getAllVehicles(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{vin}")
    public ResponseEntity<VehicleFeaturesDTO> getAllVINFeatures(@PathVariable String vin) {
        VehicleFeaturesDTO allVINFeatures = service.getAllVINFeatures(vin);
        return new ResponseEntity<>(allVINFeatures, HttpStatus.OK);
    }

    @GetMapping("/{vin}/installable")
    public ResponseEntity<List<FeatureDTO>> getVINInstallableFeatures(@PathVariable String vin) {
        List<FeatureDTO> installableFeatures = service.getAllVINInstallableFeatures(vin);

        return new ResponseEntity<>(installableFeatures, HttpStatus.OK);
    }

    @GetMapping("/{vin}/incompatible")
    public ResponseEntity<List<FeatureDTO>> getVINIncompatibleFeatures(@PathVariable String vin) {
        List<FeatureDTO> installableFeatures = service.getAllVINIncompatibleFeatures(vin);

        return new ResponseEntity<>(installableFeatures, HttpStatus.OK);
    }
}
