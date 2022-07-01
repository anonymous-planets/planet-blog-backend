package com.blog.planet.anonymous.module.file.controller;

import com.blog.planet.anonymous.exception.BusinessException;
import com.blog.planet.anonymous.item.RestResponse;
import com.blog.planet.anonymous.module.file.item.FileDto;
import com.blog.planet.anonymous.module.file.service.FileService;
import jdk.internal.loader.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: planet-blog-backend
 * @Package: com.blog.planet.anonymous.module.file.controller
 * @File: FileController
 * @Author: Created by Jinhong Min on 2022-06-17 오후 7:17
 * @Description:
 */

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 파일 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.getFiles());
    }


    /**
     * 파일 업로드
     * @return
     */
    @PostMapping
    public ResponseEntity<?> fileUpload(HttpServletRequest request, FileDto.FileUploadReq reqDto) {
        if(reqDto.getFile() == null) {
            throw new BusinessException("업로드할 파일이 없네요?");
        }
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.RestSingleSuccessResponse("서버가 파일을 잘받았어요!", fileService.fileUploadProc(request, reqDto)));
    }


    /**
     * 파일 다운 로드
     * @return
     */
    @GetMapping(value = "/download/{idx}")
    public ResponseEntity<?> fileDown(HttpServletRequest request, @PathVariable("idx") long idx) {
        return fileService.fileDownProc(request, FileDto.FileDownReq.builder().idx(idx).build());
    }

}
