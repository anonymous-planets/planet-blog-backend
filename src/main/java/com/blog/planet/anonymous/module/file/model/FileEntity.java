package com.blog.planet.anonymous.module.file.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ProjectName: planet-blog-backend
 * @Package: com.blog.planet.anonymous.module.file.model
 * @File: FileEntity
 * @Author: Created by Jinhong Min on 2022-06-20 오후 6:13
 * @Description:
 */
@Slf4j
@Getter
@Entity
@Table(name = "tb_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Column(name = "original_file_name")
    private String originalFilename;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "save_file_name")
    private String saveFilename;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "save_path")
    private String savePath;

    @Builder
    public FileEntity(Long idx, String originalFilename, Long fileSize, String saveFilename, String fileExtension, String savePath) {
        this.idx = idx;
        this.originalFilename = originalFilename;
        this.fileSize = fileSize;
        this.saveFilename = saveFilename;
        this.fileExtension = fileExtension;
        this.savePath = savePath;
    }

}
