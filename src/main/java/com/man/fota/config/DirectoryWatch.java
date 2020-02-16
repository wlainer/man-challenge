package com.man.fota.config;

import com.man.fota.enums.FileOperations;
import com.man.fota.model.exceptions.FotaException;
import com.man.fota.service.ImportFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

@Component
public class DirectoryWatch {

    private final Logger log = LoggerFactory.getLogger(DirectoryWatch.class);

    @Autowired
    private ImportFileService importFileService;

    @Value("${app.watchdir}")
    private String path;

    @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void watcher() {
        File f = new File(path);

        FilenameFilter filter = (f1, name) -> !name.contains(FileOperations.PROCESSING.getDetails()) &&
            !name.contains(FileOperations.PROCESSED.getDetails());

        File[] files = f.listFiles(filter);

        for (File file : files) {
            try {
                importFileService.importFile(file.getAbsolutePath());
            } catch (FotaException e) {
                log.debug("Incorrect filename pattern or File already processed.");
            } catch (IOException e) {
                log.error("Error while processing file.", e);
            }
        }

    }
}