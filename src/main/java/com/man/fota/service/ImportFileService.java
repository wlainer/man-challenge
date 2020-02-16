package com.man.fota.service;

import com.man.fota.enums.FileOperations;
import com.man.fota.model.Vehicle;
import com.man.fota.model.exceptions.FotaException;
import com.man.fota.repository.VehicleRepository;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ImportFileService {

    private final Logger log = LoggerFactory.getLogger(ImportFileService.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    public void importFile(String filename) throws IOException, FotaException {
        log.debug(String.format("Importing file: %s", filename));

        validateFilePattern(filename);

        checkIfInProcessingOrAlreadyProcessed(filename);

        createFile(filename, FileOperations.PROCESSING);

        Map<String, List<String>> entries = new HashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.forEach(entry -> storeEntry(entry, entries));
        }

        processData(entries);

        deleteFile(filename, FileOperations.PROCESSING);
        createFile(filename, FileOperations.PROCESSED);
    }

    protected void validateFilePattern(String filename) throws FotaException {
        log.debug(String.format("Validating pattern for filename: %s", filename));
        Pattern pattern = Pattern.compile("(hard_\\d{0,3}|soft_\\d{0,3})");
        Matcher m = pattern.matcher(filename);
        if (!m.find()) {
            throw new FotaException("Incorrect filename.");
        }

    }

    protected void checkIfInProcessingOrAlreadyProcessed(String filepath) throws FotaException {
        log.debug(String.format("Verifying if is being processed or processed: %s", filepath));

        if (checkIfFileExists(filepath + FileOperations.PROCESSING.getDetails()) ||
            checkIfFileExists(filepath + FileOperations.PROCESSED.getDetails()))
            throw new FotaException("File in process or already processed.");
    }

    protected void processData(Map<String, List<String>> entries) {
        log.debug("Processing data...");
        for (Map.Entry<String, List<String>> entry : entries.entrySet()) {

            Vehicle vehicle = vehicleRepository.findByIdentification(entry.getKey());
            if (vehicle == null)
                vehicle = new Vehicle();
            else
                log.info(String.format("Vehicle %s already registered.", vehicle.getIdentification()));

            List<String> mergedInstalled = mergeInstalled(vehicle.getInstalledSoftwareHardware(), entry.getValue());
            String softwareAndHardware = String.join(" ", mergedInstalled);

            vehicle.setIdentification(entry.getKey());
            vehicle.setInstalledSoftwareHardware(softwareAndHardware);

            vehicleRepository.saveAndFlush(vehicle);
        }
    }

    protected List<String> mergeInstalled(String installedSoftwareHardware, List<String> values) {
        log.debug("Merging data...");
        if (Strings.isEmpty(installedSoftwareHardware))
            return values;

        Map<String, Integer> installed = new HashMap<>();

        for (String piece : installedSoftwareHardware.split(" "))
            installed.putIfAbsent(piece, 0);

        for (String piece : values) {
            piece = StringUtils.trimAllWhitespace(piece);

            boolean isInstalled = installed.containsKey(piece);
            if (isInstalled)
                log.info(String.format("Hardware/Software %s already installed.", piece));
            else
                installed.putIfAbsent(piece, 0);

        }

        return installed.entrySet().stream()
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    protected void storeEntry(String entry, Map<String, List<String>> entries) {
        if (Strings.isEmpty(entry))
            return;

        String[] values = entry.split(",");

        entries.putIfAbsent(values[0], new ArrayList<>());
        entries.get(values[0]).add(values[1]);
    }

    protected boolean checkIfFileExists(String filepath) {
        File f = new File(filepath);
        return f.exists();
    }

    protected void createFile(String filepath, FileOperations operations) throws IOException {
        File f = new File(filepath + operations.getDetails());
        f.createNewFile();
    }

    protected void deleteFile(String filepath, FileOperations operations) throws IOException {
        File f = new File(filepath + operations.getDetails());
        f.delete();
    }

}
