package org.kane.server.repository;

import org.kane.server.entity.Post;
import org.kane.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Long> findAllIdByUserOrderByCreatedDateDesc(User user);
    List<Long> findAllByOrderByCreatedDateDesc();

}
