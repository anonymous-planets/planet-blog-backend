package com.blog.planet.anonymous.exception;

import com.blog.planet.anonymous.item.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @ProjectName: planet-blog-backend
 * @Package: com.blog.planet.anonymous.exception
 * @File: GlobalExceptionHandler
 * @Author: Created by Jinhong Min on 2022-06-18 오전 11:51
 * @Description:
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<RestResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RestResponse.RestEmptyFailResponse(e.getMessage()));
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RestResponse.RestEmptyFailResponse("서버를 잘못만들었네..."));
    }
}
