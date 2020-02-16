package com.man.fota.service;

import com.man.fota.model.exceptions.FotaException;
import javassist.expr.Expr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ImportFileServiceTest {

    private static ImportFileService importFileService;

    @BeforeAll
    public static void setup() {
        importFileService = new ImportFileService();
    }

    @Test
    void validateFilePattern_ShouldReturnTrueForAValidValue_Hard_XXX() throws FotaException {
        String filename = "c:\\fota\\hard_039.csv";
        importFileService.validateFilePattern(filename);
    }

    @Test
    void validateFilePattern_ShouldReturnTrueForAValidValue_Soft_XXX() throws FotaException {
        String filename = "c:\\fota\\soft_000.csv";
        importFileService.validateFilePattern(filename);
    }

    @Test
    void validateFilePattern_ShouldReturnFalseForAInvalidValue() {
        String filename = "c:\\fota\\hardware_039.csv";

        Assertions.assertThrows(FotaException.class, () -> {
            importFileService.validateFilePattern(filename);
        });
    }

    @Test
    void storeEntry_EmptyEntry() {
        Map<String, List<String>> entries = new HashMap<>();
        String entry = "";
        importFileService.storeEntry(entry, entries);

        Assertions.assertEquals(entries.size(), 0);
    }

    @Test
    void storeEntry_NewValue() {
        Map<String, List<String>> entries = new HashMap<>();
        String entry = "A,1";
        importFileService.storeEntry(entry, entries);

        Assertions.assertEquals(entries.size(), 1);
        Assertions.assertEquals(entries.get("A").get(0), "1");
    }

    @Test
    void storeEntry_UpdateEntry() {
        Map<String, List<String>> entries = new HashMap<>();
        String entry = "A,1";
        String entry2 = "B,2";
        importFileService.storeEntry(entry, entries);
        importFileService.storeEntry(entry2, entries);

        Assertions.assertEquals(entries.size(), 2);
        Assertions.assertEquals(entries.get("A").get(0), "1");
        Assertions.assertEquals(entries.get("B").get(0), "2");
    }

    @Test
    void mergeInstalled_EmptyInstalledRequirements() {
        String installed = "";
        List<String> requirements = Arrays.asList("A", "B", "C");

        List<String> merged = importFileService.mergeInstalled(installed, requirements);
        Assertions.assertEquals(requirements, merged);
    }

    @Test
    void mergeInstalled_UpdateExistingValues() {
        String installed = "D";
        List<String> requirements = Arrays.asList("A", "B", "C");

        List<String> merged = importFileService.mergeInstalled(installed, requirements);

        List<String> expected = Arrays.asList("A", "B", "C", "D");
        Assertions.assertEquals(expected, merged);
    }

    @Test
    void mergeInstalled_NotChangeExistingValues() {
        String installed = "A";
        List<String> requirements = Arrays.asList("A");

        List<String> merged = importFileService.mergeInstalled(installed, requirements);

        Assertions.assertEquals(requirements, merged);
    }

    @Test
    void mergeInstalled_CheckTrimWhitespaces() {
        String installed = "A";
        List<String> requirements = Arrays.asList(" B ");

        List<String> merged = importFileService.mergeInstalled(installed, requirements);
        List<String> expected = Arrays.asList("A", "B");

        Assertions.assertEquals(expected, merged);
    }

}