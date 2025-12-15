package org.kane.server.repository;

import org.kane.server.entity.Comment;
import org.kane.server.entity.Post;
import org.kane.server.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    Comment findCommentByIdAndCommentator(Long id, User commentator);
}
