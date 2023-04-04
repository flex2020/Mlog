package com.web.Mlog.controller;

import com.web.Mlog.dto.FileDataDto;
import com.web.Mlog.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileDataController {
    private FileDataService fileDataService;

    @Autowired
    public FileDataController(FileDataService fileDataService) {
        this.fileDataService = fileDataService;
    }

    @PostMapping("")
    public FileDataDto.FileUploadDto fileUpload(MultipartFile file) {
        return fileDataService.uploadFile(file);
    }
}
