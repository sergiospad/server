package org.kane.server.repository;

import org.kane.server.entity.Post;
import org.kane.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post,Long> {
    List<Long> findAllIdByUserOrderByCreatedDateDesc(User user);
    List<Long> findAllIdOrderByCreatedDateDesc();

}
