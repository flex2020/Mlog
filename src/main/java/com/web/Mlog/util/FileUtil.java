package com.web.Mlog.util;

import com.web.Mlog.domain.FileData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {
    private static String originalFileRootPath = "/home/ubuntu/mlog/resource/original/";
    private static String thumbnailFileRootPath = "/home/ubuntu/mlog/resource/thumbnail/";
    public static FileData getFileData(MultipartFile multipartFile) {
        // DB에 저장하기 위해 파일의 정보를 얻음
        FileData fileData = new FileData();
        try {
            String uuid = UUID.randomUUID().toString();
            String originalFilename = multipartFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            String fileName = uuid + "." + fileExtension;
            fileData.setUuid(uuid);
            fileData.setFileName(fileName);
            fileData.setFileSize(multipartFile.getSize());
            fileData.setMimeType(multipartFile.getContentType());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
        return fileData;
    }

    public static boolean uploadFile(MultipartFile multipartFile, String fileName) {
        // 파일을 서버에 저장함
        try {
            System.out.println("originalFileRootPath: " + originalFileRootPath);
            if (!new File(originalFileRootPath).exists()) {
                try {
                    new File(originalFileRootPath).mkdirs();
                } catch(Exception e) {
                    e.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "디렉토리 생성에 실패했습니다.");
                }
            }
            multipartFile.transferTo(Paths.get(originalFileRootPath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장을 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 못한 오류로 파일 저장을 실패했습니다.");
        }
        return true;
    }
}
