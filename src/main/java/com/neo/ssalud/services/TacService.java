package com.neo.ssalud.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Validated
public class TacService {

    private final Path dicomStoragePath = Paths.get("/data/dicom/");

    @Value("${dicom.base-path}")
    private String dicomBasePath;

    public Resource getDicomFile(String folderName, String filename) throws IOException {
        Path filePath = Paths.get(dicomBasePath, folderName, filename);
        if (!Files.exists(filePath)) {
            System.out.println("Not found: " + filePath);
            throw new IOException("DICOM file not found: " + filename);
        }
        return new UrlResource(filePath.toUri());
    }


    public List<DicomFileInfo> getDicomStudyFiles(String folderName) throws IOException {
        File folder = new File(dicomBasePath, folderName);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IOException("Folder not found: " + folderName);
        }

        List<DicomFileInfo> dicomFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder.toPath())) {
            for (Path filePath : stream) {
                if (Files.isRegularFile(filePath)) {
                    try (DicomInputStream dis = new DicomInputStream(filePath.toFile())) {
                        Attributes attr = dis.readDataset(-1, -1);
                        int instanceNumber = attr.getInt(Tag.InstanceNumber, 0);
                        dicomFiles.add(new DicomFileInfo(filePath.getFileName().toString(), instanceNumber));
                    } catch (Exception e) {
                        // Loggear o ignorar archivos no DICOM
                    }
                }
            }
        }

        dicomFiles.sort(Comparator.comparingInt(DicomFileInfo::getInstanceNumber));
        return dicomFiles;
    }

    @Data
    @AllArgsConstructor
    public static class DicomFileInfo {
        private String fileName;
        private int instanceNumber;
    }
}
