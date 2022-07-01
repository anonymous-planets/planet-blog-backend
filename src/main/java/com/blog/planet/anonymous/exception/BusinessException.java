package com.blog.planet.anonymous.exception;

/**
 * @ProjectName: planet-blog-backend
 * @Package: com.blog.planet.anonymous.exception
 * @File: BusinessException
 * @Author: Created by Jinhong Min on 2022-06-18 오전 11:52
 * @Description:
 */
public class BusinessException extends RuntimeException{

    private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
}
