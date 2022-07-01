package com.blog.planet.anonymous.item;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @ProjectName: planet-blog-backend
 * @Package: com.blog.planet.anonymous.item
 * @File: RestResponse
 * @Author: Created by Jinhong Min on 2022-06-18 오전 11:16
 * @Description:
 */

@Getter
public class RestResponse<T> implements Serializable {
    private Integer resultCode;
    private String message;
    private T data;
    private Long timestamp = System.currentTimeMillis();

    @Builder
    public RestResponse(Integer code, String message, T data) {
        this.resultCode = code;
        this.message = message;
        this.data = data;
    }

    public static RestResponse RestEmptySuccessResponse(String message) {
        return RestResponse.builder().code(0).message("성공").build();
    }

    public static RestResponse RestEmptySuccessResponse() {
        return RestEmptySuccessResponse("성공");
    }


    public static RestResponse RestEmptyFailResponse(String message) {
        return RestResponse.builder().code(-1).message(message).build();
    }

    public static <T>RestResponse RestSingleSuccessResponse(String message, T data) {
        return RestResponse.builder().code(0).message(message).data(data).build();
    }
    public static <T>RestResponse RestSingleSuccessResponse(T data) {
        return RestSingleSuccessResponse("성공", data);
    }
}
