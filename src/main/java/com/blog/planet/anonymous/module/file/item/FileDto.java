package com.blog.planet.anonymous.module.file.item;

import com.blog.planet.anonymous.module.file.model.FileEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: planet-blog-backend
 * @Package: com.blog.planet.anonymous.module.file.item
 * @File: FileDto
 * @Author: Created by Jinhong Min on 2022-06-17 오후 7:33
 * @Description:
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDto implements Serializable{

    private Long idx;
    private String originalFilename;    // 원본 파일명
    private Long fileSize;              // 파일 용량

    @Getter
    @NoArgsConstructor
    public static class FileItemRes extends FileDto implements Serializable {

        private String saveFilename;
        private String savePath;
        private String fileExtension;

        @Builder
        public FileItemRes(Long idx, String originalFilename, Long fileSize, String saveFilename, String fileExtension, String savePath) {
            super.idx = idx;
            super.originalFilename = originalFilename;
            super.fileSize = fileSize;
            this.saveFilename = saveFilename;
            this.fileExtension = fileExtension;
            this.savePath = savePath;
        }

        public static FileItemRes toDto(FileEntity entity) {
            return FileItemRes.builder()
                    .idx(entity.getIdx())
                    .originalFilename(entity.getOriginalFilename())
                    .fileSize(entity.getFileSize())
                    .saveFilename(entity.getSaveFilename())
                    .fileExtension(entity.getFileExtension())
                    .savePath(entity.getSavePath())
                    .build();
        }

        public static List<FileItemRes> toDto(List<FileEntity> entities) {
            return entities.stream().map(item -> toDto(item)).collect(Collectors.toList());
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FileUploadReq extends FileDto implements Serializable {
        private MultipartFile file;         // file
        private String saveFilename;

        @Builder
        public FileUploadReq(MultipartFile file) {
            this.file = file;
            super.originalFilename = file.getOriginalFilename();
            super.fileSize = file.getSize();
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FileUploadRes implements Serializable {
        private String originalFileName;
        private Long fileSize;

        @Builder
        public FileUploadRes(String originalFileName, Long fileSize) {
            this.originalFileName = originalFileName;
            this.fileSize = fileSize;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FileDownReq implements Serializable {
        private Long idx;

        @Builder
        public FileDownReq(Long idx) {
            this.idx = idx;
        }
    }
}
