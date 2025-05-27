package com.neo.ssalud.controllers;

import com.neo.ssalud.services.TacService;
import com.neo.ssalud.services.TacService.DicomFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tac")
@RequiredArgsConstructor
public class TacController {

    private final TacService tacService;

    @GetMapping("/{folderName}/{filename:.+}")
    public ResponseEntity<Resource> getDicomFile(@PathVariable String folderName, @PathVariable String filename) throws IOException {
        Resource file = tacService.getDicomFile(folderName, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }



    @GetMapping("/estudio/{folderName}")
    public ResponseEntity<List<DicomFileInfo>> getStudyFiles(@PathVariable String folderName) throws IOException {
        List<TacService.DicomFileInfo> files = tacService.getDicomStudyFiles(folderName);
        return ResponseEntity.ok(files);
    }
}
