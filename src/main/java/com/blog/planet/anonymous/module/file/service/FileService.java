package com.blog.planet.anonymous.module.file.service;

import com.blog.planet.anonymous.exception.BusinessException;
import com.blog.planet.anonymous.item.RestResponse;
import com.blog.planet.anonymous.module.file.item.FileDto;
import com.blog.planet.anonymous.module.file.model.FileEntity;
import com.blog.planet.anonymous.module.file.repository.FileRepository;
import jdk.internal.loader.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @ProjectName: planet-blog-backend
 * @Package: com.blog.planet.anonymous.module.file.service
 * @File: FileService
 * @Author: Created by Jinhong Min on 2022-06-18 오전 11:24
 * @Description:
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {


    private final FileRepository fileRepository;


    @Value("${anonymous-planet.file.allow-extension}")
    private String ALLOW_FILE_EXTENSION;


    public RestResponse getFiles() {
        return RestResponse.RestSingleSuccessResponse("업로드 파일 목록", FileDto.FileItemRes.toDto(fileRepository.findAll()));
    }



    public FileDto.FileItemRes fileUploadProc(HttpServletRequest request, FileDto.FileUploadReq reqDto) {
        String fileExtension = fileExtension(reqDto.getOriginalFilename(), ALLOW_FILE_EXTENSION);
        String saveFilename = String.valueOf(UUID.randomUUID()).replaceAll("-", "");
        String originalFilename =FilenameUtils.removeExtension(reqDto.getOriginalFilename());
        String savePath = new File("src/main/resources/store").getAbsolutePath();
        try{
            // 파일 저장
//            File destinationFile = new File(savePath + saveFilename + "." + fileExtension);
            File destinationFile = new File(savePath + File.separator + saveFilename + "." + fileExtension);
            if(!destinationFile.exists()) {
                destinationFile.mkdirs();
            }
            reqDto.getFile().transferTo(destinationFile);
        }catch(IOException e) {
            e.printStackTrace();
            throw new BusinessException("파일 업로드 실패");
        }
        FileEntity fileEntity = fileRepository.save(FileEntity.builder().originalFilename(originalFilename).fileSize(reqDto.getFileSize()).savePath(savePath).saveFilename(saveFilename).fileExtension(fileExtension).build());
        return FileDto.FileItemRes.toDto(fileEntity);
    }

    public ResponseEntity<?> fileDownProc(HttpServletRequest request, FileDto.FileDownReq reqDto) {
        FileEntity fileEntity = fileRepository.findByIdx(reqDto.getIdx()).orElseThrow(() -> new BusinessException("파일 정보를 찾을 수 없습니다."));

        File downFile = new File(fileEntity.getSavePath() + File.separator + fileEntity.getSaveFilename() + "." + fileEntity.getFileExtension());
        if(!downFile.exists()) {
            throw new BusinessException("다운 받을 파일이 존재하지 않아요!");
        }

        try{
            String contentDisposition = getContentDisposition(request, fileEntity.getOriginalFilename(), fileEntity.getFileExtension());

            String contentType = getContentType(downFile);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileEntity.getFileSize()))
                    .header(HttpHeaders.TRANSFER_ENCODING, "binary")
                    .header(HttpHeaders.PRAGMA, "no-cache")
                    .header(HttpHeaders.EXPIRES, "0")
                    .body(FileUtils.readFileToByteArray(downFile))
                    ;
        }catch(Exception e) {
            throw new BusinessException("다운로드 처리 중 에러 발생!");
        }
    }


    private String fileExtension(String originalFileName, String allowExtension) {
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        if(allowExtension.indexOf(fileExtension) > -1) {
            return fileExtension;
        }
        throw new BusinessException("허용하지 않은 파일 확장자 입니다.[가능 확장자:" + ALLOW_FILE_EXTENSION + "]");
    }

    private void createDirectory(String uploadPath) throws IOException {
        Path path = Paths.get(uploadPath);
        if(!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public static String getContentDisposition(HttpServletRequest request, String fileName, String fileExtension) throws UnsupportedEncodingException {
        String header = request.getHeader("User-Agent");
        if (header.contains("Edge")){
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            return "attachment;filename=\"" + fileName + "\"." + fileExtension + ";";
        } else if (header.contains("MSIE") || header.contains("Trident")) { // IE 11버전부터 Trident로 변경되었기때문에 추가해준다.
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            return "attachment;filename=" + fileName + "." + fileExtension + ";";
        } else if (header.contains("Chrome")) {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            return "attachment; filename=" + fileName + "." + fileExtension;
        } else if (header.contains("Opera")) {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            return "attachment; filename=\"" + fileName + "\"." + fileExtension;
        } else if (header.contains("Firefox")) {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            return "attachment; filename=" + fileName + "." + fileExtension;
        }else{
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            return "attachment; filename=" + fileName + "." + fileExtension;
        }
    }

    public String getContentType(File file) {
        String contentType = "application/octet-stream";
        try{
            contentType = Files.probeContentType(Paths.get(file.toURI()));
        }catch(IOException e) {
            // 기본 콘텐트 타입 설정
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}
