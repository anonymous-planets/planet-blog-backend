package com.blog.planet.anonymous.module.file.repository;

import com.blog.planet.anonymous.module.file.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @ProjectName: planet-blog-backend
 * @Package: com.blog.planet.anonymous.module.file.repository
 * @File: FileRepository
 * @Author: Created by Jinhong Min on 2022-06-20 오후 6:22
 * @Description:
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByIdx(Long idx);
}
