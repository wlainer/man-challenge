package com.man.fota.web;

import com.man.fota.dto.FeatureDTO;
import com.man.fota.dto.FeatureVehiclesDTO;
import com.man.fota.dto.VehicleDTO;
import com.man.fota.service.FeatureService;
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
@RequestMapping("/fota/features")
public class FeatureController extends FotaController {

    @Autowired
    private FeatureService service;

    @GetMapping()
    public ResponseEntity<Page<FeatureDTO>> getAllFeatures(@PageableDefault(value=20, page=0) Pageable pageable) {
        Page page = service.getAllFeatures(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{feature}")
    public ResponseEntity<FeatureVehiclesDTO> getAllFeatureVINs(@PathVariable String feature) {
        FeatureVehiclesDTO allFeatureVINs = service.getAllFeatureVINs(feature);
        return new ResponseEntity<>(allFeatureVINs, HttpStatus.OK);
    }

    @GetMapping("/{feature}/installable")
    public ResponseEntity<List<VehicleDTO>> getFeatureCompatibleVINs(@PathVariable String feature) {
        List<VehicleDTO> allFeatureVINCompatible = service.getAllFeatureVINCompatible(feature);
        return new ResponseEntity<>(allFeatureVINCompatible, HttpStatus.OK);
    }

    @GetMapping("/{feature}/incompatible")
    public ResponseEntity<List<VehicleDTO>> getFeatureIncompatibleVINs(@PathVariable String feature) {
        List<VehicleDTO> allFeatureVINIncompatible = service.getAllFeatureVINIncompatible(feature);
        return new ResponseEntity<>(allFeatureVINIncompatible, HttpStatus.OK);
    }
}
