package com.web.Mlog.controller;

import com.web.Mlog.dto.FileDataDto;
import com.web.Mlog.service.FileDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class FileDataController {
    private final FileDataService fileDataService;

    @PostMapping("")
    public FileDataDto.FileUploadDto fileUpload(MultipartFile file) {
        return fileDataService.uploadFile(file);
    }
}
