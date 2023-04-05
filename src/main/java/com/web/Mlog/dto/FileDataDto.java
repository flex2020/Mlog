package com.web.Mlog.dto;


import lombok.*;

public class FileDataDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileUploadDto {
        private String fileName;
    }
}
