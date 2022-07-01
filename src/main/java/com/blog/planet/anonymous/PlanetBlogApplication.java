package com.blog.planet.anonymous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @ProjectName: planet-blog-backend
 * @Package: PACKAGE_NAME
 * @File: com.blog.planet.anonymous.PlanetBlogApplication
 * @Author: Created by Jinhong Min on 2022-06-17 오후 7:11
 * @Description:
 */

@SpringBootApplication
public class PlanetBlogApplication {

    public static void main(String args[]) {
        SpringApplication.run(PlanetBlogApplication.class, args);
    }
}
