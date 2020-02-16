package com.man.fota.web;

import com.man.fota.dto.FeatureDTO;
import com.man.fota.dto.VehicleDTO;
import com.man.fota.dto.VehicleFeaturesDTO;
import com.man.fota.model.Feature;
import com.man.fota.service.FotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/fota")
public class FotaController {

//    @Autowired
//    private FotaService service;
//
//
//    @GetMapping("/features")
//    public ResponseEntity<List<FeatureDTO>> getAllFeatures() {
//        List<FeatureDTO> allFeatures = service.getAllFeatures();
//
//        return new ResponseEntity<>(allFeatures, HttpStatus.OK);
//    }
//
}
