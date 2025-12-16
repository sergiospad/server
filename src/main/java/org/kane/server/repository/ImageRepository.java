package org.kane.server.repository;

import org.kane.server.entity.ImageModel;
import org.kane.server.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageModel,Long> {
    public List<ImageModel> findAllByPost(Post post);
    public List<Long> findAllIdByPost(Post post);
}
