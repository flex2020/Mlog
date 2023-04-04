package com.web.Mlog.service;

import com.web.Mlog.domain.FileData;
import com.web.Mlog.dto.FileDataDto;
import com.web.Mlog.repository.FileDataRepository;
import com.web.Mlog.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileDataService {
    private FileDataRepository fileDataRepository;
    private FileUtil fileUtil;
    @Autowired
    public FileDataService(FileDataRepository fileDataRepository, FileUtil fileUtil) {
        this.fileDataRepository = fileDataRepository;
        this.fileUtil = fileUtil;
    }

    public FileDataDto.FileUploadDto uploadFile(MultipartFile multipartFile) {
        FileDataDto.FileUploadDto dto = new FileDataDto.FileUploadDto();
        //--- 1. DB에  저장할 파일 정보를 얻는다.
        FileData fileData = fileUtil.getFileData(multipartFile);

        //--- 2. DB에 저장하기전에 서버에 파일을 업로드 한다.
        if (fileUtil.uploadFile(multipartFile, fileData.getFileName())) {
            fileDataRepository.save(fileData);
            dto.setUuid(fileData.getUuid());
        }
        return dto;
    }
}
