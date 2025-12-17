package org.kane.server.repository;

import org.kane.server.entity.Post;
import org.kane.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query(nativeQuery = true, value = "SELECT id FROM post WHERE user_id=:id")
    List<Long> findAllByUser(Long id);

    @Query(nativeQuery = true, value = "SELECT id FROM post")
    List<Long> findAllIdPosts();

}
