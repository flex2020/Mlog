package com.web.Mlog.service;

import com.web.Mlog.domain.FileData;
import com.web.Mlog.dto.FileDataDto;
import com.web.Mlog.repository.FileDataRepository;
import com.web.Mlog.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileDataService {
    private final FileDataRepository fileDataRepository;
    private final FileUtil fileUtil;
    public FileDataDto.FileUploadDto uploadFile(MultipartFile multipartFile) {
        FileDataDto.FileUploadDto dto = new FileDataDto.FileUploadDto();
        //--- 1. DB에  저장할 파일 정보를 얻는다.
        FileData fileData = fileUtil.getFileData(multipartFile);

        //--- 2. DB에 저장하기전에 서버에 파일을 업로드 한다.
        if (fileUtil.uploadFile(multipartFile, fileData.getFileName())) {
            fileDataRepository.save(fileData);
            dto.setFileName(fileData.getFileName());
        }
        return dto;
    }
}
